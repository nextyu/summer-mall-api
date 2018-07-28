package com.nextyu.mall.web.controller;

import com.nextyu.mall.common.ServiceResponse;
import com.nextyu.mall.constant.SwaggerParamTypeConstants;
import com.nextyu.mall.query.OrderQuery;
import com.nextyu.mall.service.OrderService;
import com.nextyu.mall.vo.OrderVO;
import com.nextyu.mall.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "订单api", tags = "订单api")
@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "购买", notes = "购买")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "商品id", required = true,
                    dataType = "long", defaultValue = "1", paramType = SwaggerParamTypeConstants.FORM)
    })
    @PostMapping("/buy")
    public Object buy(Long productId) {
        OrderVO order = orderService.order(productId);
        if (order == null) {
            return ServiceResponse.buildError("购买失败");
        }
        return ServiceResponse.buildOk(order);
    }

    @ApiOperation(value = "订单列表", notes = "订单列表", response = OrderVO.class, responseContainer = "List")
    @ApiImplicitParams({

    })
    @GetMapping("/list")
    public Object listPage(@ApiIgnore OrderQuery query) {
        return ServiceResponse.buildOk(orderService.listPage(query));
    }

}