package com.youkongyi.mall.nosql.elasticsearch.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

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
@Document(indexName = "pms")
@Setting(replicas = 0)
public class EsProduct implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String productSn;

    private Long brandId;

    @Field(type = FieldType.Keyword)
    private String brandName;

    private Long productCategoryId;

    @Field(type = FieldType.Keyword)
    private String productCategoryName;

    private String pic;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String subTitle;

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String keywords;

    private BigDecimal price;

    private Integer sale;

    private Integer newStatus;

    private Integer recommandStatus;

    private Integer stock;

    private Integer promotionType;

    private Integer sort;

    @Field(type = FieldType.Nested)
    private List<EsProductAttributeValue> attrValueList;

}
