package com.youkongyi.mall.dto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.youkongyi.mall.common.util.ConstantPool;
import com.youkongyi.mall.model.UmsAdmin;
import com.youkongyi.mall.model.UmsPermission;

/**
  * @description： SpringSecurity定义用于封装用户信息的类
  *     com.youkongyi.mall.dto.AdminUserDetails
  * @author： Aimer
  * @crateDate： 2022/06/28 11:40
  */
@Setter
@Getter
public class AdminUserDetails implements UserDetails {

    private final UmsAdmin umsAdmin;

    private final List<UmsPermission> permissionList;

    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsPermission> permissionList) {
        this.umsAdmin = umsAdmin;
        this.permissionList = permissionList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissionList.stream()
                .filter(umsPermission -> umsPermission.getValue()!=null)
                .map(permission -> new SimpleGrantedAuthority(permission.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(ConstantPool.ON);
    }
}
