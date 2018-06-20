package com.nextyu.mall.enums;

import org.springframework.data.domain.Sort;

/**
 * created on 2017-07-12 10:50
 *
 * @author nextyu
 */
public enum ProductSortEnum {
    ORDER_BY_COMPREHENSIVE(1, "综合排序", "createTime", Sort.Direction.DESC),
    ORDER_BY_PRICE_ASC(2, "价格从小到大排序", "currentPrice", Sort.Direction.ASC),
    ORDER_BY_PRICE_DESC(3, "价格从大到小排序", "currentPrice", Sort.Direction.DESC),
    ORDER_BY_SALES_ASC(4, "销量从小到大排序", "saleQuantity", Sort.Direction.ASC),
    ORDER_BY_SALES_DESC(5, "销量从大到小排序", "saleQuantity", Sort.Direction.DESC);

    private Integer sort;
    private String name;
    private String fieldName;
    private Sort.Direction direction;

    ProductSortEnum(Integer sort, String name, String fieldName, Sort.Direction direction) {
        this.sort = sort;
        this.name = name;
        this.fieldName = fieldName;
        this.direction = direction;
    }

    public static Sort getSort(Integer sort) {
        ProductSortEnum[] values = ProductSortEnum.values();
        for (ProductSortEnum value : values) {
            if (sort.equals(value.sort)) {
                return new Sort(value.direction, value.fieldName);
            }
        }
        return new Sort(ProductSortEnum.ORDER_BY_COMPREHENSIVE.direction, ProductSortEnum.ORDER_BY_COMPREHENSIVE.fieldName);
    }
}
