package com.ym.mall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Spring-security的配置
 * @author taozai
 * @date 2019/8/1 0001 22:36
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()//由于使用的JWT,这里不需要csrf
                .disable()
                .sessionManagement()  //基于token，也不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, //允许对于网站的静态资源无授权访问
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/swagger-resources/**",
                        "/v2/api-docs/**"
                        ).permitAll()
                        .antMatchers("/admin/login","/admin/register")  //注册和登录允许匿名访问
                        .permitAll()
                        .anyRequest() //除上面外的所有请求全部需要鉴权认证
                        .authenticated();
                //禁用缓存
               http.headers().cacheControl();
    }
}
