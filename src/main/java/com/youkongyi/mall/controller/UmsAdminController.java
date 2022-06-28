package com.youkongyi.mall.controller;

import com.google.gson.JsonObject;
import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.dto.UmsAdminLoginParam;
import com.youkongyi.mall.model.PmsBrand;
import com.youkongyi.mall.model.UmsAdmin;
import com.youkongyi.mall.service.IUmsAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin")
@Tag(name = "用户管理", description = "后台用户管理")
public class UmsAdminController {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

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
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = Boolean.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping("/login")
    public ReturnDTO<String> login(@RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        ReturnDTO<String> ret = new ReturnDTO<>();
        try {
             String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("token", token);
            jsonObject.addProperty("tokenHead", tokenHead);
            ret.setCode(ResultCode.SUCCESS.getCode());
            ret.setMsg(ResultCode.SUCCESS.getMessage());
            ret.setData(jsonObject.toString());
        } catch (Exception e){
            log.error("登录失败: ", e);
            ret.setCode(ResultCode.FAILED.getCode());
            ret.setMsg(ResultCode.FAILED.getMessage());
        }
        return ret;
    }
}
