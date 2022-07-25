package com.youkongyi.mall.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
  * @description： oss上传成功后的回调参数
  *     com.youkongyi.mall.dto.OssCallbackParam.
  * @author： Aimer
  * @crateDate： 2022/7/25 19:09
  */
@Setter
@Getter
@Schema(description = "OSS上传成功后的回调参数")
public class OssCallbackParam {

    @Schema(description = "请求的回调地址")
    private String callbackUrl;

    @Schema(description = "回调是传入request中的参数")
    private String callbackBody;

    @Schema(description = "回调时传入参数的格式，比如表单提交形式")
    private String callbackBodyType;
    
}
