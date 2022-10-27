package com.youkongyi.mall.mapper;

import com.youkongyi.mall.model.UmsRole;
import io.mybatis.mapper.Mapper;

import java.util.List;

/**
  * @description： 角色管理Mapper
  *     com.youkongyi.mall.mapper.UmsRoleMapper.
  * @author： Aimer
  * @crateDate： 2022/10/27 17:43
  */
public interface UmsRoleMapper extends Mapper<UmsRole,Integer> {

    /**
      * @description： 根据操作员主键精确查询角色信息
      *     com.youkongyi.mall.mapper.UmsRoleMapper.findRoleByAdminId
      * @param： adminId (java.lang.Integer)
      * @return： java.util.List<com.youkongyi.mall.model.UmsRole>
      * @author： Aimer
      * @crateDate： 2022/10/27 17:43
      */
    List<UmsRole> findRoleByAdminId(Integer adminId);
}
