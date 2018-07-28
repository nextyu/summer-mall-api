package com.nextyu.mall.dao;

import com.nextyu.mall.entity.Order;
import com.nextyu.mall.query.OrderQuery;
import com.nextyu.mall.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    List<OrderVO> list(OrderQuery query);
}