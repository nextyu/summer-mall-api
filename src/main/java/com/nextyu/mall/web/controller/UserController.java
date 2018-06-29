package com.nextyu.mall.web.controller;

import com.nextyu.mall.common.ServiceResponse;
import com.nextyu.mall.constant.SwaggerParamTypeConstants;
import com.nextyu.mall.query.ProductQuery;
import com.nextyu.mall.query.UserQuery;
import com.nextyu.mall.service.UserService;
import com.nextyu.mall.vo.ProductListVO;
import com.nextyu.mall.vo.UserVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "注册", notes = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true,
                    dataType = "string", defaultValue = "15500000000", paramType = SwaggerParamTypeConstants.FORM),
            @ApiImplicitParam(name = "password", value = "密码", required = true,
                    dataType = "string", defaultValue = "123456", paramType = SwaggerParamTypeConstants.FORM)
    })
    @PostMapping()
    public Object listPage(String phone, String password) {
        UserVO user = userService.signUp(phone, password);
        return ServiceResponse.buildOk(user);
    }

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