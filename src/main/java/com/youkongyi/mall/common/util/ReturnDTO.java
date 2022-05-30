package com.youkongyi.mall.common.util;


/**
  * @description： 通用返回
  *     com.youkongyi.mall.dto.ReturnDTO
  * @author： Aimer
  * @crateDate： 2022/05/30 09:49
  */
public class ReturnDTO<T> {
    /** 返回状态码 */
    private int code;
    /** 返回状态信息 */
    private String msg;
    /** 返回数据对象 */
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
