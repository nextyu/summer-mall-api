package com.nextyu.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nextyu.mall.context.UserContext;
import com.nextyu.mall.context.UserInfo;
import com.nextyu.mall.dao.ActivityMapper;
import com.nextyu.mall.entity.Activity;
import com.nextyu.mall.query.ActivityQuery;
import com.nextyu.mall.service.ActivityService;
import com.nextyu.mall.util.CastUtil;
import com.nextyu.mall.util.DateTimeUtil;
import com.nextyu.mall.vo.ActivityDetailVO;
import com.nextyu.mall.vo.ActivityVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ActivityServiceImpl implements ActivityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityServiceImpl.class);

    @Autowired
    private ActivityMapper activityMapper;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean save(ActivityVO activityVO) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityVO, activity);
        activity.setStartTime(DateTimeUtil.date2Millis(activityVO.getStartTimeDate()));
        activity.setEndTime(DateTimeUtil.date2Millis(activityVO.getEndTimeDate()));

        if (null != activity.getId()) {
            activity.setUpdateTime(DateTimeUtil.currentTimeMillis());
            return activityMapper.updateByPrimaryKeySelective(activity) > 0;
        }
        activity.setCreateTime(DateTimeUtil.currentTimeMillis());
        int rows = activityMapper.insertSelective(activity);
        return rows > 0;
    }

    @Override
    public Boolean update(ActivityVO activityVO) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityVO, activity);
        int result = activityMapper.updateByPrimaryKeySelective(activity);
        return result > 0;
    }

    @Override
    public ActivityDetailVO getById(Long id) {


        return activityMapper.getById(id);
    }

    @Override
    public List<ActivityVO> listPage(ActivityQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<ActivityVO> activityVOS = null;
        return activityVOS;
    }

    @Override
    public List<ActivityVO> listAll() {
        List<ActivityVO> activityVOS = null;
        return activityVOS;
    }

    @Override
    public PageInfo<ActivityVO> getPageInfo(ActivityQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<ActivityVO> activityVOS = activityMapper.list(query);
        for (ActivityVO activityVO : activityVOS) {
            activityVO.setStartTimeStr(DateTimeUtil.formatDate(DateTimeUtil.millis2Date(activityVO.getStartTime()), "yyyy-MM-dd"));
            activityVO.setEndTimeStr(DateTimeUtil.formatDate(DateTimeUtil.millis2Date(activityVO.getEndTime()), "yyyy-MM-dd"));
        }


        return new PageInfo<>(activityVOS);
    }

    @Override
    public Boolean updateStatus(Long id, Integer status) {
        Activity activity = activityMapper.selectByPrimaryKey(id);
        if (null == activity) {
            return false;
        }

        Activity updateActivity = new Activity();
        updateActivity.setId(id);
        updateActivity.setStatus(status);
        updateActivity.setUpdateTime(DateTimeUtil.currentTimeMillis());
        updateActivity.setVersion(activity.getVersion());
        int rows = activityMapper.updateByPrimaryKeySelective(updateActivity);

        if (rows <= 0) {
            return false;
        }

        if (1 == status) {
            stringRedisTemplate.opsForHash().put("activity_" + id, "stockQuantity", activity.getStockQuantity() + "");
            stringRedisTemplate.opsForHash().put("activity_" + id, "startTime", activity.getStartTime() + "");
            stringRedisTemplate.opsForHash().put("activity_" + id, "endTime", activity.getEndTime() + "");
        } else if (0 == status) {
            stringRedisTemplate.opsForHash().delete("activity_" + id, "stockQuantity", "startTime", "endTime");
        }


        return true;
    }

    @Override
    public void mockSeckill(Long id) {

        UserInfo userInfo = UserContext.get();
        String token = userInfo.getToken();
        LOGGER.debug("token {}", token);

        // 判断库存
       /* Object stockQuantity = stringRedisTemplate.opsForHash().get("activity_" + id, "stockQuantity");
        if (null == stockQuantity) {
            throw new RuntimeException("库存为null");
        }

        if (CastUtil.castLong(stockQuantity) < 0) {
            throw new RuntimeException("java 卖光了");
        }*/

        // 判断重复秒杀
        String key = "activity_history_" + id;
       /* SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
        if (setOperations.isMember(key, token)) {

        }

        setOperations.add(key, token);*/

        long increment = CastUtil.castLong(stringRedisTemplate.opsForHash().get(key, token));

//        Long increment = stringRedisTemplate.opsForHash().increment(key, token, 1);
        if (increment > 1) {
            LOGGER.debug("重复秒杀");
            throw new RuntimeException("java 重复秒杀");
        }


        /*Long quantity = stringRedisTemplate.opsForHash().increment("activity_" + id, "stockQuantity", -1);
        if (quantity < 0) {
            throw new RuntimeException("java 卖光了");
        }*/

        //Activity activity = activityMapper.selectByPrimaryKey(id);
        /*Activity updateActivity = new Activity();
        updateActivity.setId(id);
        updateActivity.setSaleQuantity(activity.getSaleQuantity() + 1);
        updateActivity.setStockQuantity(activity.getStockQuantity() - 1);
        updateActivity.setUpdateTime(DateTimeUtil.currentTimeMillis());*/

        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int rows = activityMapper.increaseSalesAndDecreaseStock(id, 1, DateTimeUtil.currentTimeMillis());
        if (rows <= 0) {
            throw new RuntimeException("秒杀失败");
        }

        stringRedisTemplate.opsForHash().increment("activity_order_" + id, token, 1);

        //activityMapper.updateByPrimaryKeySelective(updateActivity);
    }
}