package com.nextyu.mall.web.controller;

import com.nextyu.mall.query.OrderQuery;
import com.nextyu.mall.service.OrderService;
import com.nextyu.mall.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /*@RequestMapping(value = "", method = RequestMethod.POST)
    public Object save(OrderVO orderVO) {
        Boolean isSuccess = orderService.save(orderVO);
        return new Object();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Object update(@PathVariable Long id, OrderVO orderVO) {
        Boolean isSuccess = orderService.update(orderVO);
        return new Object();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getById(@PathVariable Long id) {
        OrderVO orderVO = orderService.getById(id);
        return new Object();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object listPage(OrderQuery query) {
        List<OrderVO> orderVOS = orderService.listPage(query);
        return new Object();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Object listAll() {
        List<OrderVO> orderVOS = orderService.listAll();
        return new Object();
    }*/
}