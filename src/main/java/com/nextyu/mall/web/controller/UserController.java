package com.nextyu.mall.web.controller;

import com.nextyu.mall.common.ServiceResponse;
import com.nextyu.mall.constant.SwaggerParamTypeConstants;
import com.nextyu.mall.service.UserService;
import com.nextyu.mall.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "用户api", tags = "用户api")
@RestController
@RequestMapping("/users")
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
    @PostMapping("/signUp")
    public Object signUp(String phone, String password) {
        UserVO user = userService.signUp(phone, password);
        if (user == null) {
            return ServiceResponse.buildError("注册失败");
        }
        return ServiceResponse.buildOk(user);
    }

    @ApiOperation(value = "登录", notes = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true,
                    dataType = "string", defaultValue = "15500000000", paramType = SwaggerParamTypeConstants.FORM),
            @ApiImplicitParam(name = "password", value = "密码", required = true,
                    dataType = "string", defaultValue = "123456", paramType = SwaggerParamTypeConstants.FORM)
    })
    @PostMapping("/signIn")
    public Object signIn(String phone, String password) {
        UserVO user = userService.signIn(phone, password);
        if (user == null) {
            return ServiceResponse.buildError("账号或者密码错误");
        }
        return ServiceResponse.buildOk(user);
    }


}