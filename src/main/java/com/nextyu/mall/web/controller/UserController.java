package com.nextyu.mall.web.controller;

import com.nextyu.mall.query.UserQuery;
import com.nextyu.mall.service.UserService;
import com.nextyu.mall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /*@RequestMapping(value = "", method = RequestMethod.POST)
    public Object save(UserVO userVO) {
        Boolean isSuccess = userService.save(userVO);
        return new Object();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Object update(@PathVariable Long id, UserVO userVO) {
        Boolean isSuccess = userService.update(userVO);
        return new Object();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getById(@PathVariable Long id) {
        UserVO userVO = userService.getById(id);
        return new Object();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object listPage(UserQuery query) {
        List<UserVO> userVOS = userService.listPage(query);
        return new Object();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object listAll() {
        List<UserVO> userVOS = userService.listAll();
        return new Object();
    }*/
}