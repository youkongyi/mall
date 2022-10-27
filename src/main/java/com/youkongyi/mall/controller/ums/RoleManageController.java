package com.youkongyi.mall.controller.ums;

import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.model.UmsRole;
import com.youkongyi.mall.service.ums.IRoleManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
  * @description： 角色管理Controller
  *     com.youkongyi.mall.controller.ums.RoleManageController.
  * @author： Aimer
  * @crateDate： 2022/10/27 14:48
  */
@Slf4j
@RestController
@RequestMapping("/role")
@Tag(name = "角色列表", description = "角色列表")
public class RoleManageController {

    private final IRoleManageService roleManageService;

    @Autowired
    public RoleManageController(IRoleManageService roleManageService) {
        this.roleManageService = roleManageService;
    }

    @Operation(summary = "根据条件查询角色集合", description = "根据条件查询角色集合")
    @Parameters({
            @Parameter(name = "reqMap", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = UmsRole.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
    )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
    )
    })
    @GetMapping("/list")
    public ReturnDTO<List<UmsRole>> list(@RequestParam Map<String,Object> reqMap){
        ReturnDTO<List<UmsRole>> returnDTO = new ReturnDTO<>();
        try {
            returnDTO = roleManageService.queryRoleListForCond(reqMap);
        } catch (Exception e){
            log.error("查询角色信息错误: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
        }
        return returnDTO;
    }

    @Operation(summary = "根据操作员主键精确查询角色信息", description = "根据操作员主键精确查询角色信息")
    @Parameters({
            @Parameter(name = "adminId", description = "操作员主键", in = ParameterIn.PATH)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = UmsRole.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
    )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
    )
    })
    @GetMapping("/find/{adminId}")
    public ReturnDTO<List<UmsRole>> find(@PathVariable Integer adminId){
        ReturnDTO<List<UmsRole>> returnDTO = new ReturnDTO<>();
        try {
            returnDTO = roleManageService.findRoleByAdminId(adminId);
        } catch (Exception e){
            log.error("查询角色信息错误: ", e);
            returnDTO.setCode(ResultCode.FAILED.getCode());
            returnDTO.setMsg(ResultCode.FAILED.getMessage());
        }
        return returnDTO;
    }
}
