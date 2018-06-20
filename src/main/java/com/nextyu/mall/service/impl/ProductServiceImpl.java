package com.nextyu.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nextyu.mall.dao.ProductDetailMapper;
import com.nextyu.mall.dao.ProductMapper;
import com.nextyu.mall.entity.Product;
import com.nextyu.mall.entity.ProductDetail;
import com.nextyu.mall.enums.ProductSortEnum;
import com.nextyu.mall.query.ProductQuery;
import com.nextyu.mall.repository.ProductRepository;
import com.nextyu.mall.service.ProductService;
import com.nextyu.mall.service.UploadService;
import com.nextyu.mall.vo.ProductDetailVO;
import com.nextyu.mall.vo.ProductListVO;
import com.nextyu.mall.vo.ProductVO;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private UploadService uploadService;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public ProductDetailVO getById(Long id) {

        Optional<Product> optionalProduct = productRepository.findById(id);
        Product one = optionalProduct.get();

        LOGGER.debug("from es {}", one);


        Product product = productMapper.selectByPrimaryKey(id);
        if (null == product) {
            return null;
        }
        ProductDetailVO productDetailVO = new ProductDetailVO();
        BeanUtils.copyProperties(product, productDetailVO);

        ProductDetail productDetail = productDetailMapper.getByProductId(id);
        if (null != productDetail) {
            productDetailVO.setDetail(productDetail.getDetail());
        }

        String subImages = product.getSubImages();
        if (StringUtils.isNotEmpty(subImages)) {
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
        SearchQuery searchQuery = getProductSearchQuery(query);

        /*Pageable pageable = new PageRequest(query.getPageNum(), query.getPageSize());

        List<Product> byTitle = productRepository.findByTitle(query.getKeywords());
        System.out.println("*******" + byTitle);

        Page<Product> page = productRepository.search(QueryBuilders.matchPhraseQuery("title", query.getKeywords()), pageable);
        System.out.println("++++" + page.getContent());
        Page<Product> page2 = productRepository.search(QueryBuilders.queryStringQuery(query.getKeywords()), pageable);
        System.out.println("&&&&&&" + page2.getContent());
        Iterable<Product> title1 = productRepository.search(QueryBuilders.matchPhraseQuery("title", query.getKeywords()));*/


        List<Product> products = productRepository.search(searchQuery).getContent();
        LOGGER.debug("from es {}", products);
        List<ProductListVO> productVOS = new ArrayList<>(products.size());
        for (Product product : products) {
            ProductListVO productListVO = new ProductListVO();
            BeanUtils.copyProperties(product, productListVO);
            productListVO.setMainImage(uploadService.getImgDomain() + productListVO.getMainImage());
            productVOS.add(productListVO);
        }

        return productVOS;
    }

    private SearchQuery getProductSearchQuery(ProductQuery query) {
        QueryBuilder queryBuilder = null;
        if (StringUtils.isNotEmpty(query.getKeywords())) {
//            queryBuilder = QueryBuilders.queryStringQuery(query.getKeywords());
            queryBuilder = QueryBuilders.matchPhraseQuery("title", query.getKeywords());
//            queryBuilder = QueryBuilders.matchQuery("categoryId", 1026);
//            queryBuilder = QueryBuilders.multiMatchQuery(query.getKeywords(), "title");
        }

        QueryBuilder categoryQueryBuilder = null;
        if (null != query.getCategoryId()) {
            categoryQueryBuilder = QueryBuilders.matchQuery("categoryId", query.getCategoryId());
        }


        if (query.getPageNum() >= 1) {
            query.setPageNum(query.getPageNum() - 1);
        }

        // 分页参数
        Pageable pageable = new PageRequest(query.getPageNum(), query.getPageSize(), ProductSortEnum.getSort(query.getSort()));
        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(queryBuilder)
                .withFilter(categoryQueryBuilder)
                .build();
    }


}