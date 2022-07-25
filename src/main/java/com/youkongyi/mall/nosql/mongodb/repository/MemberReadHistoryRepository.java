package com.youkongyi.mall.nosql.mongodb.repository;

import com.youkongyi.mall.nosql.mongodb.document.MemberReadHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
  * @description： 会员商品浏览历史Repository
  *     com.youkongyi.mall.nosql.mongodb.repository.MemberReadHistoryRepository
  * @author： Aimer
  * @crateDate： 2022/7/22 9:56
  */
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory, String> {

    /**
      * @description： 根据会员ID按时间倒序获取浏览记录
      *     com.youkongyi.mall.nosql.mongodb.repository.MemberReadHistoryRepository.findByMemberIdOrderByCreateTimeDesc
      * @param： memberId (java.lang.Long)
      * @return： java.util.List<com.youkongyi.mall.nosql.mongodb.document.MemberReadHistory>
      * @author： Aimer
      * @crateDate： 2022/7/22 9:56
      */
    List<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId);
}
