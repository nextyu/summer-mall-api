package com.nextyu.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nextyu.mall.context.UserContext;
import com.nextyu.mall.dao.OrderMapper;
import com.nextyu.mall.dao.ProductMapper;
import com.nextyu.mall.entity.Order;
import com.nextyu.mall.entity.Product;
import com.nextyu.mall.enums.OrderStatusEnum;
import com.nextyu.mall.query.OrderQuery;
import com.nextyu.mall.service.OrderService;
import com.nextyu.mall.service.UploadService;
import com.nextyu.mall.util.DateTimeUtil;
import com.nextyu.mall.util.MoneyUtil;
import com.nextyu.mall.vo.OrderVO;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UploadService uploadService;

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
        query.setUserId(UserContext.get().getUserId());
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<OrderVO> orderVOS = orderMapper.list(query);
        if (CollUtil.isEmpty(orderVOS)) {
            return orderVOS;
        }

        return orderVOS.stream().peek(order -> {
            order.setOrderTime(DateTimeUtil.formatMillis(order.getCreateTime(), DateTimeUtil.yyyy_MM_dd));
            order.setTotalPriceYuan(MoneyUtil.fen2Yuan(order.getTotalPrice()).toString());
            order.setOrderStatusStr(OrderStatusEnum.getDesc(order.getOrderStatus()));
            order.setProductImage(uploadService.getImgDomain() + order.getProductImage());
        }).collect(Collectors.toList());

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

    @Override
    public OrderVO order(Long productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        Order order = new Order();
        order.setUserId(UserContext.get().getUserId());
        order.setProductId(productId);
        order.setProductName(product.getTitle());
        order.setProductQuantity(1);
        order.setProductPrice(product.getCurrentPrice());
        order.setTotalPrice(product.getCurrentPrice());
        order.setOrderStatus(OrderStatusEnum.UN_USED.code());
        order.setCreateTime(DateTimeUtil.currentTimeMillis());
        order.setVersion(1L);
        int rows = orderMapper.insertSelective(order);
        if (rows <= 0) {
            return null;
        }

        OrderVO orderVO = new OrderVO();
        orderVO.setId(order.getId());
        orderVO.setProductId(productId);
        return orderVO;
    }
}