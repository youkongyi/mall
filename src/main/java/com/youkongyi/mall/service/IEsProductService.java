package com.youkongyi.mall.service;

import com.youkongyi.mall.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;

import java.util.List;

/**
  * @description： 商品搜索管理Service
  *     com.youkongyi.mall.service.IEsProductService.
  * @author： Aimer
  * @crateDate： 2022/7/8 15:13
  */
public interface IEsProductService {
    /**
     * 从数据库中导入所有商品到ES
     */
    int importAll() throws Exception;

    /**
     * 根据id删除商品
     */
    void delete(Long id);

    /**
     * 根据id创建商品
     */
    EsProduct create(Long id);

    /**
     * 批量删除商品
     */
    void delete(List<Long> ids);

    /**
     * 根据关键字搜索名称或者副标题
     */
    List<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) throws Exception;
}
