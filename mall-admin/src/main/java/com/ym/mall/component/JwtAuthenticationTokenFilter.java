package com.ym.mall.component;

import com.ym.mall.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OncePerRequestFilter的介绍：
 * 在servlet2.3中，Filter会经过一切请求，包括服务器内部使用的forward转发请求和<%@ include file=”/login.jsp”%>的情况
 * servlet2.4中的Filter默认情况下只过滤外部提交的请求，forward和include这些内部转发都不会被过滤，
 * 因此，为了兼容各种不同运行环境和版本，默认filter继承OncePerRequestFilter是一个比较稳妥的选择
 *internal:内部的
 * JWT登录过滤授权器
 *
 * @author taozai
 * @date 2019/8/1 0001 23:34
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;  //JWT存储的请求头
    @Value("${jwt.tokenHead}")
    private String tokenHead;  //JWT负载中拿到开头
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chainhain) throws ServletException, IOException {
        String header = request.getHeader(this.tokenHeader);
        log.info("JWT登录过滤器获取的header:{}",header);

    }
}
