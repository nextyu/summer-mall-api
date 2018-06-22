package com.nextyu.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nextyu.mall.dao.OrderMapper;
import com.nextyu.mall.entity.Order;
import com.nextyu.mall.query.OrderQuery;
import com.nextyu.mall.service.OrderService;
import com.nextyu.mall.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Boolean save(OrderVO orderVO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderVO, order);
        int rows = orderMapper.insertSelective(order);
        return rows > 0;
    }

    @Override
    public Boolean update(OrderVO orderVO) {
        Order order = new Order();
        BeanUtils.copyProperties(orderVO, order);
        int rows = orderMapper.updateByPrimaryKeySelective(order);
        return rows > 0;
    }

    @Override
    public OrderVO getById(Long id) {
        return null;
    }

    @Override
    public List<OrderVO> listPage(OrderQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<OrderVO> orderVOS = null;
        return orderVOS;
    }

    @Override
    public List<OrderVO> listAll() {
        List<OrderVO> orderVOS = null;
        return orderVOS;
    }

    @Override
    public PageInfo<OrderVO> getPageInfo(OrderQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<OrderVO> orderVOS = null;
        return new PageInfo<>(orderVOS);
    }
}