package com.youkongyi.mall.mapper;

import com.youkongyi.mall.nosql.elasticsearch.document.EsProduct;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EsProductMapper {

    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
