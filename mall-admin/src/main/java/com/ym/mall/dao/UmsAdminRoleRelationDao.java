package com.ym.mall.dao;

import com.ym.mall.model.UmsAdmin;
import com.ym.mall.model.UmsAdminRoleRelation;
import com.ym.mall.model.UmsPermission;
import com.ym.mall.model.UmsRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * 后台用户与角色管理的自定义dao
 * @author matao
 * @create 2019-08-01 15:13
 */
public interface UmsAdminRoleRelationDao {
    /**
     * 获取用户的所有权限（包括+-权限）
     * @param adminId
     * @return
     */
    List<UmsPermission> getPermissionList(@Param("adminId") long adminId);

    /**
     * 获取当前用户所拥有的角色
     * @param adminId
     * @return
     */
    List<UmsRole> getUmsRoleList(@Param("adminId") long adminId);

    /**
     * 查询后台管理员用户的列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<UmsAdmin> getUmsAdminList(@Param("pageNum") Integer pageNum,
                                   @Param("pageSize") Integer pageSize);

    /**
     * 根据管理员用户的名称查询
     * @param name
     * @return
     */
    UmsAdmin getUmsAdminByName(@Param("name") String name);

    /**
     * 获取指定的管理员用户的角色
     * @param id
     * @return
     */
    UmsRole getUmsRoleByAdminId(@Param("adminId") long adminId);

    /**
     * 批量插入用户角色关系
     * @param umsAdminRoleRelations
     * @return
     */
    int insertRolesList(@Param("list")List<UmsAdminRoleRelation> umsAdminRoleRelations);

    /**
     *获取指定用户的所有权限
     * @param adminId
     * @return
     */
    List<UmsPermission> getPermissionByAdminId(@Param("adminId") long adminId);

}
