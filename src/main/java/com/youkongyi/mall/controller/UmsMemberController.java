package com.youkongyi.mall.controller;

import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.service.IUmsMemberService;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
  * @description： 会员登录注册管理Controller
  *     com.youkongyi.mall.controller.UmsMemberController
  * @author： Aimer
  * @crateDate： 2022/06/27 13:42
  */
@Slf4j
@RestController
@RequestMapping("/sso")
@Tag(name = "会员登录注册",description = "会员登录注册管理")
public class UmsMemberController {

    private final IUmsMemberService umsMemberService;

    @Autowired
    public UmsMemberController(IUmsMemberService umsMemberService) {
        this.umsMemberService = umsMemberService;
    }

    /**
      * @description： 生成验证码信息
      *     com.youkongyi.mall.controller.UmsMemberController.generateAuthCode
      * @param： telephone (java.lang.String)
      * @return： com.youkongyi.mall.common.util.ReturnDTO<java.lang.String>
      * @author： Aimer
      * @crateDate： 2022/06/27 13:52
      */
    @Operation(summary = "3001_生成验证码信息", description = "生成验证码信息")
    @Parameter(name = "telephone", description = "电话号码", in = ParameterIn.QUERY)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/generate")
    public ReturnDTO<String> generateAuthCode(String telephone){
        ReturnDTO<String> ret = new ReturnDTO<>();
        if(StringUtils.isBlank(telephone)){
            ret.setCode(ResultCode.VALIDATE_FAILED.getCode());
            ret.setMsg(ResultCode.VALIDATE_FAILED.getMessage());
            return ret;
        }
        try {
            return umsMemberService.generateAuthCode(telephone);
        } catch (Exception e){
            log.error("生成验证码信息异常", e);
            ret.setCode(ResultCode.FAILED.getCode());
            ret.setMsg(ResultCode.FAILED.getMessage());
        }
        return ret;
    }

    /**
      * @description： 校验相应验证码
      *     com.youkongyi.mall.controller.UmsMemberController.verifyAuthCode
      * @param： telephone (java.lang.String)
      * @param： authCode (java.lang.String)
      * @return： com.youkongyi.mall.common.util.ReturnDTO<java.lang.String>
      * @author： Aimer
      * @crateDate： 2022/06/27 13:55
      */
    @Operation(summary = "3002_校验相应验证码", description = "校验相应验证码")
    @Parameters({
            @Parameter(name = "telephone", description = "电话号码", in = ParameterIn.QUERY),
            @Parameter(name = "authCode", description = "验证码", in = ParameterIn.QUERY),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功",
                    content = @Content( mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
            ,@ApiResponse(responseCode = "400", description = "参数检验失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
            ,@ApiResponse(responseCode = "500", description = "操作失败",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/verify")
    public ReturnDTO<String> verifyAuthCode(String telephone, String authCode){
        ReturnDTO<String> ret = new ReturnDTO<>();
        if(StringUtils.isBlank(telephone) || StringUtils.isBlank(authCode)){
            ret.setCode(ResultCode.VALIDATE_FAILED.getCode());
            ret.setMsg(ResultCode.VALIDATE_FAILED.getMessage());
            return ret;
        }
        try {
            return umsMemberService.verifyAuthCode(telephone, authCode);
        } catch (Exception e){
            log.error("验证码校验异常", e);
            ret.setCode(ResultCode.FAILED.getCode());
            ret.setMsg(ResultCode.FAILED.getMessage());
        }
        return ret;
    }
}
