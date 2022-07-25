package com.youkongyi.mall.controller;

import com.youkongyi.mall.common.emum.ResultCode;
import com.youkongyi.mall.common.util.ReturnDTO;
import com.youkongyi.mall.dto.OssCallbackResult;
import com.youkongyi.mall.dto.OssPolicyResult;
import com.youkongyi.mall.service.IOssService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/aliyun/oss")
@Tag(name = "OSS管理",description = "阿里云OSS管理")
public class OssController {

    private final IOssService ossService;

    @Autowired
    public OssController(IOssService ossService) {
        this.ossService = ossService;
    }

    @Operation(summary = "oss上传签名生成", description = "oss上传签名生成")
    @GetMapping(value = "/policy")
    public ReturnDTO<OssPolicyResult> policy() {
        ReturnDTO<OssPolicyResult> returnDTO = new ReturnDTO<>();
        OssPolicyResult result = ossService.policy();
        returnDTO.setCode(ResultCode.SUCCESS.getCode());
        returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
        returnDTO.setData(result);
        return returnDTO;
    }

    @Operation(summary = "oss上传成功回调", description = "oss上传成功回调")
    @PostMapping(value = "/callback")
    public ReturnDTO<OssCallbackResult> callback(HttpServletRequest request) {
        ReturnDTO<OssCallbackResult> returnDTO = new ReturnDTO<>();
        OssCallbackResult ossCallbackResult = ossService.callback(request);
        returnDTO.setCode(ResultCode.SUCCESS.getCode());
        returnDTO.setMsg(ResultCode.SUCCESS.getMessage());
        returnDTO.setData(ossCallbackResult);
        return returnDTO;
    }
}
