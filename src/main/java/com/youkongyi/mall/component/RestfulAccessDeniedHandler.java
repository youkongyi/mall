package com.youkongyi.mall.component;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youkongyi.mall.common.emum.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.google.gson.Gson;
import com.youkongyi.mall.common.util.ReturnDTO;

/**
  * @description： 当访问接口没有权限时，自定义的返回结果
  *     com.youkongyi.mall.component.RestfulAccessDeniedHandler
  * @author： Aimer
  * @crateDate： 2022/06/29 16:14
  */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        ReturnDTO<String> ret = new ReturnDTO<>();
        ret.setCode(ResultCode.FORBIDDEN.getCode());
        ret.setMsg(ResultCode.FORBIDDEN.getMessage());
        ret.setData(e.getMessage());
        httpServletResponse.getWriter().println(new Gson().toJson(ret));
        httpServletResponse.getWriter().flush();

    }
}
