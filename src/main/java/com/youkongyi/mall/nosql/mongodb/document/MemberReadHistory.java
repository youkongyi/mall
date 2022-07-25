package com.youkongyi.mall.nosql.mongodb.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

/**
  * @description： 用户商品浏览历史记录
  *     com.youkongyi.mall.nosql.mongodb.document.MemberReadHistory
  * @author： Aimer
  * @crateDate： 2022/7/22 9:55
  */
@Setter
@Getter
@Document
public class MemberReadHistory {

    @MongoId
    private String id;

    @Indexed
    private Long memberId;

    private String memberNickname;

    private String memberIcon;

    @Indexed
    private String productId;

    private String productName;

    private String productPic;

    private String productSubtitle;

    private String productPrice;

    private String createTime;

}
