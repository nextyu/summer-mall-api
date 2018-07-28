package com.nextyu.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nextyu.mall.dao.UserMapper;
import com.nextyu.mall.entity.User;
import com.nextyu.mall.query.UserQuery;
import com.nextyu.mall.service.UserService;
import com.nextyu.mall.util.DateTimeUtil;
import com.nextyu.mall.util.JWTUtil;
import com.nextyu.mall.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Override
    public UserVO signUp(String phone, String password) {
        User user = new User();
        user.setPhone(phone);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreateTime(DateTimeUtil.currentTimeMillis());

        int rows = userMapper.insertSelective(user);
        if (rows <= 0) {
            return null;
        }

        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        return userVO;
    }

    @Override
    public UserVO signIn(String phone, String password) {
        User user = userMapper.getByPhone(phone);
        if (user == null) {
            return null;
        }
        if (!user.getPassword().equals(passwordEncoder.encode(password))) {
            return null;
        }
        String token = JWTUtil.getToken(user.getId());
        UserVO userVO = new UserVO();
        userVO.setToken(token);
        return userVO;
    }
}