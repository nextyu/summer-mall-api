package com.nextyu.mall.web.controller;

import com.nextyu.mall.common.ServiceResponse;
import com.nextyu.mall.constant.SwaggerParamTypeConstants;
import com.nextyu.mall.service.ActivityService;
import com.nextyu.mall.vo.ActivityDetailVO;
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

@ApiIgnore
@Api(value = "活动api", tags = "活动api")
@RestController
@RequestMapping("/activities")
public class ActivityController extends BaseController {


    @Autowired
    private ActivityService activityService;


    /*@ApiOperation(value = "活动列表", notes = "活动列表", response = ActivityListVO.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true,
                    dataType = "int", defaultValue = "0", paramType = SwaggerParamTypeConstants.QUERY),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量", required = true,
                    dataType = "int", defaultValue = "10", paramType = SwaggerParamTypeConstants.QUERY)
    })
    @GetMapping()
    public Object listPage(@ApiIgnore ActivityQuery query) {
        PageInfo<ActivityListVO> pageInfo = activityService.getPageInfo(query);
        return ServiceResponse.buildOk(pageInfo.getList());
    }

    @ApiOperation(value = "搜索活动列表", notes = "搜索活动列表", response = ActivityListVO.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true,
                    dataType = "int", defaultValue = "0", paramType = SwaggerParamTypeConstants.QUERY),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数量", required = true,
                    dataType = "int", defaultValue = "10", paramType = SwaggerParamTypeConstants.QUERY),
            @ApiImplicitParam(name = "keywords", value = "关键词", required = false,
                    dataType = "string", defaultValue = "糕点", paramType = SwaggerParamTypeConstants.QUERY),
            @ApiImplicitParam(name = "sort", value = "排序", required = true,
                    dataType = "int", defaultValue = "1", paramType = SwaggerParamTypeConstants.QUERY)
    })
    @GetMapping("/search")
    public Object search(@ApiIgnore ActivityQuery query) {
        List<ActivityListVO> activityListVOS = activityService.search(query);
        return ServiceResponse.buildOk(activityListVOS);
    }*/

    @ApiOperation(value = "根据id查找活动", notes = "根据id查找活动", response = ActivityDetailVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "活动id", required = true,
                    dataType = "long", defaultValue = "1", paramType = SwaggerParamTypeConstants.PATH)
    })
    @GetMapping("/{id}")
    public Object getById(@PathVariable Long id) {
        ActivityDetailVO activityDetailVO = activityService.getById(id);
        return ServiceResponse.buildOk(activityDetailVO);
    }

    @GetMapping("/kill/{id}")
    public Object mockSeckill(@PathVariable Long id) {

        activityService.mockSeckill(id);
        return ServiceResponse.buildOk();
    }

}