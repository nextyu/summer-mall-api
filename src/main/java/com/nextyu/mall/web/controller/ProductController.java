package com.nextyu.mall.web.controller;

import com.github.pagehelper.PageInfo;
import com.nextyu.mall.common.ServiceResponse;
import com.nextyu.mall.constant.SwaggerParamTypeConstants;
import com.nextyu.mall.query.ProductQuery;
import com.nextyu.mall.service.ProductService;
import com.nextyu.mall.vo.ProductDetailVO;
import com.nextyu.mall.vo.ProductListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "产品api", tags = "产品api")
@RestController
@RequestMapping("/products")
public class ProductController extends BaseController {


    @Autowired
    private ProductService productService;


    @ApiOperation(value = "产品列表", notes = "产品列表", response = ProductListVO.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true,
                    dataType = "int", defaultValue = "1", paramType = SwaggerParamTypeConstants.QUERY),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量", required = true,
                    dataType = "int", defaultValue = "10", paramType = SwaggerParamTypeConstants.QUERY),
            @ApiImplicitParam(name = "categoryId", value = "类目id", required = false,
                    dataType = "long", defaultValue = "1026", paramType = SwaggerParamTypeConstants.QUERY)
    })
    @GetMapping()
    public Object listPage(@ApiIgnore ProductQuery query) {
        return ServiceResponse.buildOk(productService.listPage(query));
    }

    @ApiOperation(value = "搜索产品列表", notes = "搜索产品列表", response = ProductListVO.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true,
                    dataType = "int", defaultValue = "1", paramType = SwaggerParamTypeConstants.QUERY),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量", required = true,
                    dataType = "int", defaultValue = "10", paramType = SwaggerParamTypeConstants.QUERY),
            @ApiImplicitParam(name = "keywords", value = "关键词", required = false,
                    dataType = "string", defaultValue = "糕点", paramType = SwaggerParamTypeConstants.QUERY),
            @ApiImplicitParam(name = "sort", value = "排序,1:综合排序,2:价格从小到大排序,3:价格从大到小排序,4:销量从小到大排序,5:销量从大到小排序", required = true,
                    dataType = "int", defaultValue = "1", paramType = SwaggerParamTypeConstants.QUERY)
    })
    @GetMapping("/search")
    public Object search(@ApiIgnore ProductQuery query) {
        List<ProductListVO> productListVOS = productService.search(query);
        return ServiceResponse.buildOk(productListVOS);
    }

    @ApiOperation(value = "根据id查找产品", notes = "根据id查找产品", response = ProductDetailVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品id", required = true,
                    dataType = "long", defaultValue = "1", paramType = SwaggerParamTypeConstants.PATH)
    })
    @GetMapping("/{id}")
    public Object getById(@PathVariable Long id) {
        ProductDetailVO productDetailVO = productService.getById(id);
        return ServiceResponse.buildOk(productDetailVO);
    }


}