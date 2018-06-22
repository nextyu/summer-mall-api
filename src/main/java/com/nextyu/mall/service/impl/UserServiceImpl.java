package com.nextyu.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nextyu.mall.dao.UserMapper;
import com.nextyu.mall.entity.User;
import com.nextyu.mall.query.UserQuery;
import com.nextyu.mall.service.UserService;
import com.nextyu.mall.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Boolean save(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        int rows = userMapper.insertSelective(user);
        return rows > 0;
    }

    @Override
    public Boolean update(UserVO userVO) {
        User user = new User();
        BeanUtils.copyProperties(userVO, user);
        int rows = userMapper.updateByPrimaryKeySelective(user);
        return rows > 0;
    }

    @Override
    public UserVO getById(Long id) {
        return null;
    }

    @Override
    public List<UserVO> listPage(UserQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<UserVO> userVOS = null;
        return userVOS;
    }

    @Override
    public List<UserVO> listAll() {
        List<UserVO> userVOS = null;
        return userVOS;
    }

    @Override
    public PageInfo<UserVO> getPageInfo(UserQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<UserVO> userVOS = null;
        return new PageInfo<>(userVOS);
    }
}