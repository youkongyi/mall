package com.youkongyi.mall.model;

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
public class PmsBrand {
    /** 主键 */
    private String id;
    /** 品牌名称 */
    private String name;
    /** 首字母 */
    private String firstLetter;
    /** 排序 */
    private String sort;
    /** 是否为品牌制造商 */
    private String factoryStatus;
    /** 是否显示 */
    private String showStatus;
    /** 产品数量 */
    private String productCount;
    /** 产品评论数量 */
    private String productCommentCount;
    /** 品牌logo */
    private String logo;
    /** 专区大图 */
    private String bigPic;
    /** 品牌故事 */
    private String brandStory;

}
