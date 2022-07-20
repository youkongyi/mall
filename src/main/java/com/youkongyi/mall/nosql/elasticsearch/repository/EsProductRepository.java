package com.youkongyi.mall.nosql.elasticsearch.repository;

import com.youkongyi.mall.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @description： ES_商品操作类
 *     com.youkongyi.mall.nosql.elasticsearch.repository.EsProductRepository.
 * @author： Aimer
 * @crateDate： 2022/7/8 15:11
 */
public interface EsProductRepository extends ElasticsearchRepository<EsProduct, Long> {

    /**
     * @description： 搜索查询
     *     com.youkongyi.mall.nosql.elasticsearch.repository.EsProductRepository.findByNameOrSubTitleOrKeywords
     * @param： name (java.lang.String) 商品名称
     * @param： subTitle (java.lang.String) 商品标题
     * @param： keywords (java.lang.String) 商品关键字
     * @param： page (org.springframework.data.domain.Pageable) 分页信息
     * @return： org.springframework.data.domain.Page<com.youkongyi.mall.nosql.elasticsearch.document.EsProduct>
     * @author： Aimer
     * @crateDate： 2022/7/8 15:11
     */
    Page<EsProduct> findByNameOrSubTitleOrKeywords(String name, String subTitle, String keywords, Pageable page);
}
