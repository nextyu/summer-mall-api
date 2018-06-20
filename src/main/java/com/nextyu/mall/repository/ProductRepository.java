package com.nextyu.mall.repository;

import com.nextyu.mall.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * created on 2017-07-11 15:08
 *
 * @author nextyu
 */
public interface ProductRepository extends ElasticsearchRepository<Product, Long> {
    /**
     * LIKE 语句查询
     *
     * @param title
     * @return
     */
    List<Product> findByTitleLike(String title);

    List<Product> findByTitle(String title);
}
