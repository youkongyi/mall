package com.youkongyi.mall.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.youkongyi.mall.common.emum.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.google.gson.Gson;
import com.youkongyi.mall.common.util.ReturnDTO;

/**
  * @description： 当未登录或者token失效访问接口时，自定义的返回结果
  * @author： Aimer
  * @crateDate： 2022/06/29 16:14
  */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {

        if("/".equals(httpServletRequest.getRequestURI())){
            httpServletRequest.getRequestDispatcher("/doc.html").forward(httpServletRequest,httpServletResponse);
            return;
        }
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        ReturnDTO<String> ret = new ReturnDTO<>();
        ret.setCode(ResultCode.UNAUTHORIZED.getCode());
        ret.setMsg(ResultCode.UNAUTHORIZED.getMessage());
        ret.setData(e.getMessage());
        httpServletResponse.getWriter().println(new Gson().toJson(ret));
        httpServletResponse.getWriter().flush();

    }
}
