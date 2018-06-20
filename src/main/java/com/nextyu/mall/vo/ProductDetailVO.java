package com.nextyu.mall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "产品详情", description = "产品详情")
@Data
public class ProductDetailVO extends ProductVO{

    @ApiModelProperty(value = "图片")
    private List<String> images;

    @ApiModelProperty(value = "详情")
    private String detail;

}