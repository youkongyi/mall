package com.youkongyi.mall.service;

import com.youkongyi.mall.nosql.mongodb.document.MemberReadHistory;

import java.util.List;

/**
  * @description： 会员浏览记录管理Service
  *     com.youkongyi.mall.service.IMemberReadHistoryService
  * @author： Aimer
  * @crateDate： 2022/7/22 9:57
  */
public interface IMemberReadHistoryService {

    /**
      * @description： 生成浏览记录
      *     com.youkongyi.mall.service.IMemberReadHistoryService.create
      * @param： memberReadHistory (com.youkongyi.mall.nosql.mongodb.document.MemberReadHistory)
      * @return： int
      * @author： Aimer
      * @crateDate： 2022/7/22 9:58
      */
    int create(MemberReadHistory memberReadHistory);

    /**
      * @description： 删除浏览记录
      *     com.youkongyi.mall.service.IMemberReadHistoryService.delete
      * @param： ids (java.util.List<java.lang.String>)
      * @return： int
      * @author： Aimer
      * @crateDate： 2022/7/22 9:58
      */
    int delete(List<String> ids);

    /**
      * @description： 获取用户浏览历史记录
      *     com.youkongyi.mall.service.IMemberReadHistoryService.list
      * @param： memberId (java.lang.Long)
      * @return： java.util.List<com.youkongyi.mall.nosql.mongodb.document.MemberReadHistory>
      * @author： Aimer
      * @crateDate： 2022/7/22 9:59
      */
    List<MemberReadHistory> list(Long memberId);

}
