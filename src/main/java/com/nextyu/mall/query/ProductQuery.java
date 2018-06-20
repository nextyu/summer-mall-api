package com.nextyu.mall.query;

import lombok.Data;

@Data
public class ProductQuery extends BaseQuery {
    private String keywords;
    private Integer sort = 1;
    private Long categoryId;
}