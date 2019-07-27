package com.ym.mall.service;

/**
 * 后台管理员的Service
 * @author matao
 * @create 2019-07-26 14:29
 */
public interface UmsAdminService {

    /**
     * 登录功能
     * @param userName
     * @param password
     * @return   生成JWT的token
     */
    String login(String userName,String password);
}
