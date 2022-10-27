package com.youkongyi.mall.service.ums;

import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.model.UmsRole;

import java.util.List;
import java.util.Map;

/**
  * @description： 角色管理Controller
  *     com.youkongyi.mall.controller.ums.IRoleManageService.
  * @author： Aimer
  * @crateDate： 2022/10/27 14:48
  */
public interface IRoleManageService {

    /**
      * @description： 根据条件查询角色集合
      *     com.youkongyi.mall.service.ums.IRoleManageService.queryRoleListForCond
      * @param： reqMap (java.util.Map<java.lang.String,java.lang.Object>)
      * @return： com.youkongyi.mall.common.util.ReturnDTO<java.util.List<com.youkongyi.mall.model.UmsRole>>
      * @author： Aimer
      * @crateDate： 2022/10/27 17:12
      */
    ReturnDTO<List<UmsRole>> queryRoleListForCond(Map<String, Object> reqMap);

    /**
      * @description： 根据操作员主键精确查询角色信息
      *     com.youkongyi.mall.service.ums.IRoleManageService.findRoleByAdminId
      * @param： roleId (java.lang.Integer)
      * @return： com.youkongyi.mall.common.util.ReturnDTO<com.youkongyi.mall.model.UmsRole>
      * @author： Aimer
      * @crateDate： 2022/10/27 17:24
      */
    ReturnDTO<List<UmsRole>> findRoleByAdminId(Integer adminId);
}
