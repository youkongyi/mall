package com.youkongyi.mall.common.util;


import lombok.Getter;
import lombok.Setter;

/**
  * @description： 通用返回
  *     com.youkongyi.mall.dto.ReturnDTO
  * @author： Aimer
  * @crateDate： 2022/05/30 09:49
  */
@Setter
@Getter
public class ReturnDTO<T> {
    /** 返回状态码 */
    private int code;
    /** 返回状态信息 */
    private String msg;
    /** 返回数据对象 */
    private T data;
}
