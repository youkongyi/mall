package com.youkongyi.mall.service.ums.impl;

import cn.hutool.core.convert.Convert;
import com.github.pagehelper.PageHelper;
import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.Pager;
import com.youkongyi.mall.common.util.PagerResult;
import com.youkongyi.mall.mapper.UmsAdminMapper;
import com.youkongyi.mall.model.UmsAdmin;
import com.youkongyi.mall.service.ums.IOperatorManageService;
import io.mybatis.mapper.example.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OperatorManageServiceImpl implements IOperatorManageService {

    private final UmsAdminMapper adminMapper;

    @Autowired
    public OperatorManageServiceImpl(UmsAdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    /**
      * @description： 操作员列表分页查询
      *     com.youkongyi.mall.service.ums.impl.OperatorManageServiceImpl.queryOperatorForPage
      * @param： reqMap (java.util.Map<java.lang.String,java.lang.Object>)
      * @return： com.youkongyi.mall.common.util.PagerResult<com.youkongyi.mall.model.UmsAdmin>
      * @author： Aimer
      * @crateDate： 2022/10/27 16:20
      */
    @Override
    public PagerResult<UmsAdmin> queryOperatorForPage(Map<String, Object> reqMap) {
        PagerResult<UmsAdmin> result = new PagerResult<>();
        String keyword = String.valueOf(reqMap.get("keyword"));
        Pager pager = Convert.convert(Pager.class, reqMap);
        Example<UmsAdmin> example = adminMapper.example();
        if(!"null".equals(keyword)){
            example.createCriteria().andLike(UmsAdmin::getNickName, keyword);
            example.or(example.createCriteria().andLike(UmsAdmin::getUsername, keyword));
        }
        PageHelper.startPage(pager);
        List<UmsAdmin> list = adminMapper.selectByExample(example);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMessage());
        result.setRows(list);
        return result;
    }
}
