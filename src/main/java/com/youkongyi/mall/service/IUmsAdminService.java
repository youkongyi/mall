package com.youkongyi.mall.service;

import java.util.List;

import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.dto.AccessToken;
import com.youkongyi.mall.dto.AdminUserDetails;
import com.youkongyi.mall.model.UmsAdmin;
import com.youkongyi.mall.model.UmsPermission;

public interface IUmsAdminService {

    AdminUserDetails getAdminByUsername(String username);

    List<UmsPermission> getPermissionList(Long adminId);

    ReturnDTO<AccessToken> login(String username, String password);

    ReturnDTO<UmsAdmin> register(UmsAdmin umsAdmin);
}
