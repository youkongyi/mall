package com.youkongyi.mall.service.ums.impl;

import cn.hutool.core.convert.Convert;
import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.mapper.UmsRoleMapper;
import com.youkongyi.mall.model.UmsRole;
import com.youkongyi.mall.service.ums.IRoleManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleManageServiceImpl implements IRoleManageService {

    private final UmsRoleMapper roleMapper;

    @Autowired
    public RoleManageServiceImpl(UmsRoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    /**
      * @description： 根据条件查询角色集合
      *     com.youkongyi.mall.service.ums.impl.RoleManageServiceImpl.queryRoleListForCond
      * @param： reqMap (java.util.Map<java.lang.String,java.lang.Object>)
      * @return： com.youkongyi.mall.common.util.ReturnDTO<java.util.List<com.youkongyi.mall.model.UmsRole>>
      * @author： Aimer
      * @crateDate： 2022/10/27 17:12
      */
    @Override
    public ReturnDTO<List<UmsRole>> queryRoleListForCond(Map<String, Object> reqMap) {
        ReturnDTO<List<UmsRole>> returnDTO = new ReturnDTO<>();
        UmsRole role = Convert.convert(UmsRole.class, reqMap);
        List<UmsRole> list = roleMapper.selectList(role);
        returnDTO.setCode(ResultCode.SUCCESS.getCode());
        returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
        returnDTO.setData(list);
        return returnDTO;
    }

    /**
      * @description： 根据操作员主键精确查询角色信息
      *     com.youkongyi.mall.service.ums.impl.RoleManageServiceImpl.findRoleByAdminId
      * @param： roleId (java.lang.Integer)
      * @return： com.youkongyi.mall.common.util.ReturnDTO<com.youkongyi.mall.model.UmsRole>
      * @author： Aimer
      * @crateDate： 2022/10/27 17:25
      */
    @Override
    public ReturnDTO<List<UmsRole>> findRoleByAdminId(Integer adminId) {
        ReturnDTO<List<UmsRole>> returnDTO = new ReturnDTO<>();
        List<UmsRole> list = roleMapper.findRoleByAdminId(adminId);
        returnDTO.setCode(ResultCode.SUCCESS.getCode());
        returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
        returnDTO.setData(list);
        return returnDTO;
    }
}
