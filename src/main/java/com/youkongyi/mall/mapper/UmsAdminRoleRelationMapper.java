package com.youkongyi.mall.mapper;

import com.youkongyi.mall.model.UmsPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UmsAdminRoleRelationMapper {

    List<UmsPermission> getPermissionList(Long adminId);
}
