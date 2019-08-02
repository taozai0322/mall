package com.ym.mall.component;

import com.ym.mall.common.api.ResponseResult;
import com.ym.mall.common.api.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当未登录时，或者token失效访问接口时，自定义的返回结果
 * @author matao
 * @create 2019-08-02 9:44
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest requestequest, HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(ResponseResult.fail(ResultCode.UNAUTHORIZED.getCode(),ResultCode.UNAUTHORIZED.getMsg()));
        response.getWriter().flush();
    }
}
