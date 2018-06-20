package com.nextyu.mall.service.impl;

import com.nextyu.mall.dao.CarouselMapper;
import com.nextyu.mall.query.CarouselQuery;
import com.nextyu.mall.service.CarouselService;
import com.nextyu.mall.service.UploadService;
import com.nextyu.mall.vo.CarouselVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    @Autowired
    private UploadService uploadService;


    @Override
    public List<CarouselVO> listAll() {
        List<CarouselVO> carouselVOS = carouselMapper.list(new CarouselQuery());
        for (CarouselVO carouselVO : carouselVOS) {
            carouselVO.setImage(uploadService.getImgDomain() + carouselVO.getImage());
        }
        return carouselVOS;
    }


}