package com.youkongyi.mall.service.impl;

import cn.hutool.core.date.DateUtil;
import com.youkongyi.mall.nosql.mongodb.document.MemberReadHistory;
import com.youkongyi.mall.nosql.mongodb.repository.MemberReadHistoryRepository;
import com.youkongyi.mall.service.IMemberReadHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberReadHistoryServiceImpl implements IMemberReadHistoryService {

    private final MemberReadHistoryRepository repository;

    @Autowired
    public MemberReadHistoryServiceImpl(MemberReadHistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public int create(MemberReadHistory memberReadHistory) {
        memberReadHistory.setId(null);
        memberReadHistory.setCreateTime(DateUtil.now());
        repository.save(memberReadHistory);
        return 1;
    }

    @Override
    public int delete(List<String> ids) {
        List<MemberReadHistory> deleteList = new ArrayList<>();
        for(String id:ids){
            MemberReadHistory memberReadHistory = new MemberReadHistory();
            memberReadHistory.setId(id);
            deleteList.add(memberReadHistory);
        }
        repository.deleteAll(deleteList);
        return ids.size();
    }

    @Override
    public List<MemberReadHistory> list(Long memberId) {
        return repository.findByMemberIdOrderByCreateTimeDesc(memberId);
    }
}
