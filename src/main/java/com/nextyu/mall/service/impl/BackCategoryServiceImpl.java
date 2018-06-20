package com.nextyu.mall.service.impl;

import com.nextyu.mall.dao.BackCategoryMapper;
import com.nextyu.mall.query.BackCategoryQuery;
import com.nextyu.mall.service.BackCategoryService;
import com.nextyu.mall.service.UploadService;
import com.nextyu.mall.vo.BackCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BackCategoryServiceImpl implements BackCategoryService {
    @Autowired
    private BackCategoryMapper backCategoryMapper;

    @Autowired
    private UploadService uploadService;


    @Override
    public List<BackCategoryVO> listAll() {
        List<BackCategoryVO> backCategoryVOS = backCategoryMapper.list(new BackCategoryQuery());
        for (BackCategoryVO backCategoryVO : backCategoryVOS) {
            backCategoryVO.setImage(uploadService.getImgDomain() + backCategoryVO.getImage());
        }
        return backCategoryVOS;
    }

}