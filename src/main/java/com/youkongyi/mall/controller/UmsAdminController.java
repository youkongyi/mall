package com.youkongyi.mall.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.dto.AccessToken;
import com.youkongyi.mall.dto.AdminUserDetails;
import com.youkongyi.mall.dto.UmsAdminLoginParam;
import com.youkongyi.mall.model.UmsAdmin;
import com.youkongyi.mall.model.UmsRole;
import com.youkongyi.mall.service.IUmsAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/admin")
@Tag(name = "用户管理", description = "后台用户管理")
public class UmsAdminController {

    private IUmsAdminService adminService;

    @Autowired
    public void setAdminService(IUmsAdminService adminService) {
        this.adminService = adminService;
    }

    /**
      * @description： 用户注册
      *     com.youkongyi.mall.controller.UmsAdminController.register
      * @param： umsAdmin (com.youkongyi.mall.model.UmsAdmin) 
      * @return： com.youkongyi.mall.common.util.ReturnDTO<com.youkongyi.mall.model.UmsAdmin>          
      * @author： Aimer
      * @crateDate： 2022/06/28 15:00
      */
    @Operation(summary = "5001_用户注册",description = "用户注册")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "账户信息", required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UmsAdmin.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = Boolean.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping("/register")
    public ReturnDTO<UmsAdmin> register(@RequestBody UmsAdmin umsAdmin) {
        ReturnDTO<UmsAdmin> ret = new ReturnDTO<>();
        try {
            return adminService.register(umsAdmin);
        } catch (Exception e){
            log.error("注册失败: ", e);
            ret.setCode(ResultCode.FAILED.getCode());
            ret.setMsg(ResultCode.FAILED.getMessage());
        }
        return ret;
    }

    /**
      * @description： 登录返回token
      *     com.youkongyi.mall.controller.UmsAdminController.login
      * @param： umsAdminLoginParam (com.youkongyi.mall.dto.UmsAdminLoginParam)
      * @return： com.youkongyi.mall.common.util.ReturnDTO<java.lang.String>
      * @author： Aimer
      * @crateDate： 2022/06/28 15:07
      */
    @Operation(summary = "5002_登录返回token", description = "登录返回token")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "账户信息", required = true,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UmsAdminLoginParam.class)))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = AccessToken.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping("/login")
    public ReturnDTO<AccessToken> login(@RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        ReturnDTO<AccessToken> ret = new ReturnDTO<>();
        try {
             return adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        } catch (Exception e){
            log.error("登录失败: ", e);
            ret.setCode(ResultCode.FAILED.getCode());
            ret.setMsg(ResultCode.FAILED.getMessage());
        }
        return ret;
    }


    @GetMapping("/info")
    public ReturnDTO<JSONObject> info(Principal principal){
        ReturnDTO<JSONObject> ret = new ReturnDTO<>();
        if (principal == null){
            log.error("暂未登录或token已经过期");
            ret.setCode(ResultCode.UNAUTHORIZED.getCode());
            ret.setMsg(ResultCode.UNAUTHORIZED.getMessage());
            return ret;
        }
        try {
            String username = principal.getName();
            AdminUserDetails umsAdmin = adminService.getAdminByUsername(username);
            UmsAdmin admin = umsAdmin.getUmsAdmin();
            JSONObject json = JSONUtil.createObj();
            json.set("username", umsAdmin.getUsername());
            json.set("menus", umsAdmin.getPermissionList()
                    .stream()
                    .filter(umsPermission -> umsPermission.getType()!=2) // 过滤2 按钮
                    .collect(Collectors.toList()));
            json.set("icon", admin.getIcon());

            List<UmsRole> roleList = adminService.getRoleList(admin.getId());
            if(CollUtil.isNotEmpty(roleList)){
                List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
                json.set("roles",roles);
            }
            ret.setCode(ResultCode.SUCCESS.getCode());
            ret.setMsg(ResultCode.SUCCESS.getMessage());
            ret.setData(json);
        }catch (Exception e){
            log.error("登录失败: ", e);
            ret.setCode(ResultCode.FAILED.getCode());
            ret.setMsg(ResultCode.FAILED.getMessage());
        }
        return ret;
    }
}
