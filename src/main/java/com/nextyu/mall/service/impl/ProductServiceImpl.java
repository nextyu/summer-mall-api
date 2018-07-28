package com.nextyu.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nextyu.mall.constant.RedisConstants;
import com.nextyu.mall.dao.ProductDetailMapper;
import com.nextyu.mall.dao.ProductMapper;
import com.nextyu.mall.entity.Product;
import com.nextyu.mall.enums.ProductSortEnum;
import com.nextyu.mall.query.ProductQuery;
import com.nextyu.mall.repository.ProductRepository;
import com.nextyu.mall.service.ProductService;
import com.nextyu.mall.service.UploadService;
import com.nextyu.mall.vo.ProductDetailVO;
import com.nextyu.mall.vo.ProductListVO;
import com.nextyu.mall.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public ProductDetailVO getById(Long id) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            return null;
        }
        Product product = optionalProduct.get();

        log.debug("from es {}", product);


        ProductDetailVO productDetailVO = new ProductDetailVO();
        BeanUtils.copyProperties(product, productDetailVO);


        Object detail = redisTemplate.opsForValue().get(RedisConstants.PRODUCT_PREFIX + id);
        if (null != detail) {
            productDetailVO.setDetail(detail.toString());
        }

        String subImages = product.getSubImages();
        if (StrUtil.isNotEmpty(subImages)) {
            List<String> images = new ArrayList<>();
            String[] strings = subImages.split(",");
            for (String string : strings) {
                images.add(uploadService.getImgDomain() + string);
            }
            productDetailVO.setImages(images);
        }

        return productDetailVO;
    }

    @Override
    public List<ProductListVO> listPage(ProductQuery query) {
        return search(query);
    }

    @Override
    public List<ProductVO> listAll() {
        List<ProductVO> productVOS = null;
        return productVOS;
    }

    @Override
    public PageInfo<ProductListVO> getPageInfo(ProductQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<ProductListVO> productVOS = productMapper.list(query);
        for (ProductListVO productListVO : productVOS) {
            productListVO.setMainImage(uploadService.getImgDomain() + productListVO.getMainImage());
        }
        return new PageInfo<>(productVOS);
    }

    @Override
    public List<ProductListVO> search(ProductQuery query) {
        // 构建搜索查询
        SearchQuery searchQuery = initProductSearchQuery(query);

        List<Product> products = productRepository.search(searchQuery).getContent();
        log.debug("from es {}", products);
        List<ProductListVO> productVOS = new ArrayList<>(products.size());
        for (Product product : products) {
            ProductListVO productListVO = new ProductListVO();
            BeanUtils.copyProperties(product, productListVO);
            productListVO.setMainImage(uploadService.getImgDomain() + productListVO.getMainImage());
            productVOS.add(productListVO);
        }

        return productVOS;
    }

    private SearchQuery initProductSearchQuery(ProductQuery query) {
        QueryBuilder queryBuilder = null;
        if (StrUtil.isNotEmpty(query.getKeywords())) {
            queryBuilder = QueryBuilders.matchPhraseQuery("title", query.getKeywords());
        }

        QueryBuilder categoryQueryBuilder = null;
        if (null != query.getCategoryId()) {
            categoryQueryBuilder = QueryBuilders.matchQuery("categoryId", query.getCategoryId());
        }

        if (query.getPageNum() >= 1) {
            query.setPageNum(query.getPageNum() - 1);
        }

        // 随机排序
        ScriptSortBuilder scriptSortBuilder = SortBuilders.scriptSort(new Script("Math.random()"), ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.ASC);


        // 分页参数
//        Pageable pageable = PageRequest.of(query.getPageNum(), query.getPageSize(), ProductSortEnum.getSort(query.getSort()));
        Pageable pageable = PageRequest.of(0,2);
        return new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withFilter(categoryQueryBuilder)
                .withSort(scriptSortBuilder)
                .withPageable(pageable)
                .build();
    }


}