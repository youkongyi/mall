package com.youkongyi.mall.model;

import io.mybatis.provider.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * @description： 品牌
 *      com.youkongyi.mall.model.PmsBrand
 * @author： Aimer
 * @crateDate： 2022/05/30 10:14
 */
@Setter
@Getter
@Entity.Table("pms_brand")
public class PmsBrand {
    /** 主键 */
    @Entity.Column(id = true, remark = "主键")
    private String id;
    /** 品牌名称 */
    @Entity.Column
    private String name;
    /** 首字母 */
    @Entity.Column("first_letter")
    private String firstLetter;
    /** 排序 */
    @Entity.Column
    private String sort;
    /** 是否为品牌制造商 */
    @Entity.Column("factory_status")
    private String factoryStatus;
    /** 是否显示 */
    @Entity.Column("show_status")
    private String showStatus;
    /** 产品数量 */
    @Entity.Column("product_count")
    private String productCount;
    /** 产品评论数量 */
    @Entity.Column("product_comment_count")
    private String productCommentCount;
    /** 品牌logo */
    @Entity.Column
    private String logo;
    /** 专区大图 */
    @Entity.Column("big_pic")
    private String bigPic;
    /** 品牌故事 */
    @Entity.Column("brand_story")
    private String brandStory;

}
