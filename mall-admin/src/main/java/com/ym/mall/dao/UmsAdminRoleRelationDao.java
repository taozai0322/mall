package com.ym.mall.dao;

import com.ym.mall.model.UmsPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户与角色管理的自定义daos
 * @author matao
 * @create 2019-08-01 15:13
 */
@Mapper
public interface UmsAdminRoleRelationDao {
    /**
     * 获取用户的所有权限（包括+-权限）
     * @param adminId
     * @return
     */
    List<UmsPermission> getPermissionList(@Param("adminId") long adminId);
}
