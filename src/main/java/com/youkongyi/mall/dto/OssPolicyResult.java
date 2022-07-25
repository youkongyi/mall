package com.youkongyi.mall.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * @description： 获取OSS上传文件授权返回结果
 * com.youkongyi.mall.dto.OssPolicyResult.
 * @author： Aimer
 * @crateDate： 2022/7/25 19:06
 */
@Setter
@Getter
@Schema(description = "获取OSS上传文件授权返回结果")
public class OssPolicyResult {

    @Schema(description = "访问身份验证中用到用户标识")
    private String accessKeyId;

    @Schema(description = "用户表单上传的策略,经过base64编码过的字符串")
    private String policy;

    @Schema(description = "对policy签名后的字符串")
    private String signature;

    @Schema(description = "上传文件夹路径前缀")
    private String dir;

    @Schema(description = "oss对外服务的访问域名")
    private String host;

    @Schema(description = "上传成功后的回调设置")
    private String callback;
}
