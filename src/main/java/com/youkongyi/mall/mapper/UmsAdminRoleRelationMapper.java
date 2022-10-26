package com.youkongyi.mall.mapper;

import com.youkongyi.mall.model.UmsPermission;
import com.youkongyi.mall.model.UmsRole;

import java.util.List;

public interface UmsAdminRoleRelationMapper {

    List<UmsPermission> getPermissionList(Long adminId);

    List<UmsRole> getRoleList(Long id);
}
