package com.youkongyi.mall.nosql.elasticsearch.document;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
  * @description： ES_商品信息
  *     com.youkongyi.mall.model.EsProduct.
  * @author： Aimer
  * @crateDate： 2022/7/8 14:54
  */
@Getter
@Setter
public class EsProduct implements Serializable {

    private final static long serialVersionUID = 1L;

    private Long id;

    private String productSn;

    private Long brandId;

    private String brandName;

    private Long productCategoryId;

    private String productCategoryName;

    private String pic;

    private String name;

    private String subTitle;

    private String keywords;

    private BigDecimal price;

    private Integer sale;

    private Integer newStatus;

    private Integer recommandStatus;

    private Integer stock;

    private Integer promotionType;

    private Integer sort;

    private List<EsProductAttributeValue> attrValueList;

}
