package com.ym.mall.service;

import com.ym.mall.model.UmsAdmin;
import com.ym.mall.model.UmsPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台管理员的Service
 * @author matao
 * @create 2019-07-26 14:29
 */
public interface UmsAdminService {

    /**
     * 根据用户名获取后台管理员
     * @param username
     * @return
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 登录功能
     * @param userName
     * @param password
     * @return   生成JWT的token
     */
    String login(String userName,String password);

    /**
     * 获取用户的所有权限（包括+-权限）
     * @param adminId
     * @return
     */
    List<UmsPermission> getPermissionList(long adminId);
}
