package com.nextyu.mall.web.controller;

import com.nextyu.mall.common.ServiceResponse;
import com.nextyu.mall.service.CarouselService;
import com.nextyu.mall.vo.CarouselVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * created on 2017-07-28 9:44
 *
 * @author nextyu
 */
@ApiIgnore

@Api(value = "轮播图api", tags = "轮播图api")
@RestController
@RequestMapping("/carousels")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    @ApiOperation(value = "轮播图列表", notes = "轮播图列表", response = CarouselVO.class, responseContainer = "List")
    @GetMapping()
    public Object list() {
        return ServiceResponse.buildOk(carouselService.listAll());
    }

}
