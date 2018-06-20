package com.nextyu.mall.dao;

import com.nextyu.mall.entity.ProductDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductDetail record);

    int insertSelective(ProductDetail record);

    ProductDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductDetail record);

    int updateByPrimaryKeyWithBLOBs(ProductDetail record);

    int updateByPrimaryKey(ProductDetail record);

    ProductDetail getByProductId(Long productId);
}