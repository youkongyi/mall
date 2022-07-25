package com.youkongyi.mall.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
  * @description： OSS上传文件的回调结果
  *     com.youkongyi.mall.dto.OssCallbackResult
  * @author： Aimer
  * @crateDate： 2022/7/25 19:10
  */
@Setter
@Getter
@Schema(description = "OSS上传文件的回调结果")
public class OssCallbackResult {

    @Schema(description = "文件名称")
    private String filename;

    @Schema(description = "文件大小")
    private String size;

    @Schema(description = "文件的mimeType")
    private String mimeType;

    @Schema(description = "图片文件的宽")
    private String width;

    @Schema(description = "图片文件的高")
    private String height;
}
