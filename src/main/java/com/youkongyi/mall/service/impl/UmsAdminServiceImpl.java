package com.youkongyi.mall.service.impl;

import java.util.List;

import cn.hutool.core.date.DateUtil;
import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.youkongyi.mall.dto.AdminUserDetails;
import com.youkongyi.mall.mapper.UmsAdminMapper;
import com.youkongyi.mall.mapper.UmsAdminRoleRelationMapper;
import com.youkongyi.mall.model.UmsAdmin;
import com.youkongyi.mall.model.UmsPermission;
import com.youkongyi.mall.service.IUmsAdminService;

import io.mybatis.mapper.example.Example;

@Slf4j
@Service
public class UmsAdminServiceImpl implements IUmsAdminService {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private JwtUtil jwtUtil;

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private UmsAdminMapper adminMapper;

    @Autowired
    public void setAdminMapper(UmsAdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    private UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;

    @Autowired
    public void setUmsAdminRoleRelationMapper(UmsAdminRoleRelationMapper umsAdminRoleRelationMapper) {
        this.umsAdminRoleRelationMapper = umsAdminRoleRelationMapper;
    }

    @Override
    public AdminUserDetails getAdminByUsername(String username) {
        Example<UmsAdmin> example = new Example<>();
        example.createCriteria().andEqualTo(UmsAdmin::getUsername, username);
        List<UmsAdmin> adminList = adminMapper.selectByExample(example);
        if (adminList != null && adminList.size() > 0) {
            UmsAdmin admin = adminList.get(0);
            List<UmsPermission> permissionList = this.getPermissionList(Long.parseLong(admin.getId()));
            return new AdminUserDetails(admin, permissionList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

    @Override
    public List<UmsPermission> getPermissionList(Long adminId) {
        return umsAdminRoleRelationMapper.getPermissionList(adminId);
    }

    @Override
    public String login(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    @Override
    public ReturnDTO<UmsAdmin> register(UmsAdmin umsAdminParam) {
        ReturnDTO<UmsAdmin> ret = new ReturnDTO<>();
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        umsAdmin.setCreateTime(DateUtil.now());
        umsAdmin.setStatus("1");
        //查询是否有相同用户名的用户
        Example<UmsAdmin> example = new Example<>();
        example.createCriteria().andEqualTo(UmsAdmin::getUsername, umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = adminMapper.selectByExample(example);
        if (umsAdminList.size() > 0) {
            return null;
        }
        //将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        adminMapper.insert(umsAdmin);
        ret.setCode(ResultCode.SUCCESS.getCode());
        ret.setMsg(ResultCode.SUCCESS.getMessage());
        ret.setData(umsAdmin);
        return ret;
    }
}
