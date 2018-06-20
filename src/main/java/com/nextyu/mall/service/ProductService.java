package com.nextyu.mall.service;

import com.github.pagehelper.PageInfo;
import com.nextyu.mall.entity.ProductDetail;
import com.nextyu.mall.query.ProductQuery;
import com.nextyu.mall.vo.ProductDetailVO;
import com.nextyu.mall.vo.ProductListVO;
import com.nextyu.mall.vo.ProductVO;

import java.util.List;

public interface ProductService {

    ProductDetailVO getById(Long id);

    List<ProductVO> listAll();

    List<ProductListVO> listPage(ProductQuery query);

    PageInfo<ProductListVO> getPageInfo(ProductQuery query);

    List<ProductListVO> search(ProductQuery query);
}