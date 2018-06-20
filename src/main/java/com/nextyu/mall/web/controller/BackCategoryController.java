package com.nextyu.mall.web.controller;

import com.nextyu.mall.common.ServiceResponse;
import com.nextyu.mall.service.BackCategoryService;
import com.nextyu.mall.vo.BackCategoryVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created on 2017-07-28 9:44
 *
 * @author nextyu
 */
@Api(value = "类目api", tags = "类目api")
@RestController
@RequestMapping("/categories")
public class BackCategoryController {

    @Autowired
    private BackCategoryService backCategoryService;

    @ApiOperation(value = "类目列表", notes = "类目列表", response = BackCategoryVO.class, responseContainer = "List")
    @GetMapping()
    public Object list() {
        return ServiceResponse.buildOk(backCategoryService.listAll());
    }

}
