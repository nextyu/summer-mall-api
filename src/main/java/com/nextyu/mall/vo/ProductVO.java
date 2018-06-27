package com.nextyu.mall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "产品", description = "产品")
@Data
public class ProductVO {
    /**
     * id
     */
    @ApiModelProperty(value = "产品id")
    private Long id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;

    /**
     * 营业时间
     */
    @ApiModelProperty(value = "营业时间")
    private String hours;


    /**
     * 原价
     */
    @ApiModelProperty(value = "原价，分")
    private Long originalPrice;

    /**
     * 现价
     */
    @ApiModelProperty(value = "现价，分")
    private Long currentPrice;


    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private Long stockQuantity;

    /**
     * 销量
     */
    @ApiModelProperty(value = "销量")
    private Long saleQuantity;

    /**
     * 查看量
     */
    @ApiModelProperty(value = "查看量")
    private Long viewQuantity;


}