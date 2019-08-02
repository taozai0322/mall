package com.ym.mall.component;

import com.ym.mall.common.api.ResponseResult;
import com.ym.mall.common.api.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当访问接口没有权限，拒绝访问时，返回的自定义的结果
 * @author matao
 * @create 2019-08-02 9:53
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(ResponseResult.fail(ResultCode.FORBIDDEN.getCode(),ResultCode.FORBIDDEN.getMsg()));
        response.getWriter().flush();
    }
}
