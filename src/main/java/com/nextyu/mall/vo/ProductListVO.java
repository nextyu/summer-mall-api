package com.nextyu.mall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "产品列表", description = "产品列表")
@Data
public class ProductListVO extends ProductVO {
    /**
     * 主图
     */
    @ApiModelProperty(value = "主图")
    private String mainImage;

}