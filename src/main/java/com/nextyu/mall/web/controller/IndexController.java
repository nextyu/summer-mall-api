package com.nextyu.mall.web.controller;


import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.resource.ClassPathResource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nextyu.mall.common.ServiceResponse;
import com.nextyu.mall.vo.MenuVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 2017-03-08 上午11:49
 *
 * @author nextyu
 */
@ApiIgnore
@RestController
public class IndexController extends BaseController {

    /**
     * 菜单列表
     *
     * @return
     */
    @GetMapping(value = "/menuList")
    public Object menuList() {
        List<MenuVO> menuVOS = new ArrayList<>();
        MenuVO member = new MenuVO(1L, "产品管理", "&#xe631;", "");
        MenuVO member11 = new MenuVO(11L, "产品列表", "&#xe631;", "products/list");
        List<MenuVO> memberSubs = new ArrayList<>();
        memberSubs.add(member11);
        member.setSub(memberSubs);

        menuVOS.add(member);


        return ServiceResponse.buildOk(null, menuVOS);
    }
}
