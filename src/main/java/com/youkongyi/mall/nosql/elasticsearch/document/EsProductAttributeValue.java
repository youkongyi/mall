package com.youkongyi.mall.nosql.elasticsearch.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
  * @description： ES_商品属性信息
  *     com.youkongyi.mall.nosql.elasticsearch.document.EsProductAttributeValue.
  * @author： Aimer
  * @crateDate： 2022/7/8 15:01
  */
@Setter
@Getter
public class EsProductAttributeValue implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Field(type = FieldType.Keyword)
    private Long productAttributeId;

    private String value;

    @Field(type = FieldType.Keyword)
    private Integer type;

    private String name;

}
