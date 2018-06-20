package com.nextyu.mall.query;

import lombok.Getter;
import lombok.Setter;

/**
 * created on 2017-06-05 14:48
 *
 * @author nextyu
 */
@Getter
@Setter
public class BaseQuery {
    private Integer pageNum = 1; // 页码
    private Integer pageSize = 10;// 每页显示数量
}
