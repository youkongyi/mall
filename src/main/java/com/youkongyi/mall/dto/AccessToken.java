package com.youkongyi.mall.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
  * @description： 令牌身份验证
  *     com.youkongyi.mall.dto.AccessToken
  * @author： Aimer
  * @crateDate： 2022/06/29 11:31
  */
@Setter
@Getter
public class AccessToken {

    @Schema(description = "token")
    private String token;

    @Schema(description = "token头")
    private String head;

    @Schema(description = "t有效时间戳")
    private long expire;

}
