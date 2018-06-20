package com.nextyu.mall.dao;

import com.nextyu.mall.entity.Activity;
import com.nextyu.mall.query.ActivityQuery;
import com.nextyu.mall.vo.ActivityDetailVO;
import com.nextyu.mall.vo.ActivityVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);

    List<ActivityVO> list(ActivityQuery query);

    ActivityDetailVO getById(Long id);

    int increaseSalesAndDecreaseStock(@Param("id") Long id, @Param("quantity") int quantity
            , @Param("currentTimeMillis") long currentTimeMillis);
}