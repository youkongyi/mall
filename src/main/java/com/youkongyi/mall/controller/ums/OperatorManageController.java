package com.youkongyi.mall.controller.ums;

import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.PagerResult;
import com.youkongyi.mall.model.UmsAdmin;
import com.youkongyi.mall.service.ums.IOperatorManageService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
  * @description： 操作员管理Controller
  *     com.youkongyi.mall.controller.ums.OperatorManageController.
  * @author： Aimer
  * @crateDate： 2022/10/27 14:49
  */
@Slf4j
@RestController
@RequestMapping("/admin")
@Tag(name = "权限", description = "用户列表")
public class OperatorManageController {

    private final IOperatorManageService operatorManageService;

    @Autowired
    public OperatorManageController(IOperatorManageService operatorManageService) {
        this.operatorManageService = operatorManageService;
    }

    /**
      * @description： 操作员列表分页查询
      *     com.youkongyi.mall.controller.ums.OperatorManageController.page
      * @param： reqMap (java.util.Map<java.lang.String,java.lang.Object>) 
      * @return： com.youkongyi.mall.common.util.PagerResult          
      * @author： Aimer
      * @crateDate： 2022/10/27 14:57
      */
    @Operation(summary = "操作员列表分页查询", description = "操作员列表分页查询")
    @Parameters({
            @Parameter(name = "keyword", description = "关键字", in = ParameterIn.QUERY)
            ,@Parameter(name = "pageNum", description = "页码", in = ParameterIn.QUERY)
            ,@Parameter(name = "pageSize", description = "页面大小", in = ParameterIn.QUERY)
            ,@Parameter(name = "reqMap", hidden = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = UmsAdmin.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
    )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
            content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
    )
    })
    @GetMapping("/page")
    public PagerResult<UmsAdmin> page(@RequestParam Map<String, Object> reqMap){
        PagerResult<UmsAdmin> result = new PagerResult<>();
        try {
            result = operatorManageService.queryOperatorForPage(reqMap);
        } catch (Exception e){
            log.error("用户列表查询失败", e);
            result.setCode(ResultCode.FAILED.getCode());
            result.setMsg(ResultCode.FAILED.getMessage());
        }
        return result;
    }

}
