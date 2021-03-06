package com.ym.mall.dao;

import com.ym.mall.model.UmsAdminRoleRelation;
import com.ym.mall.model.UmsPermission;
import com.ym.mall.model.UmsRolePermissionRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户角色管理的dao
 * @author matao
 * @create 2019-08-19 17:23
 */
public interface UmsRoleDao {

    /**
     * 根据Id批量删除角色
     * @param ids
     * @return
     */
    int BatchDeleteRoleById( List<Long> list);

    /**
     * 获取相应角色的权限
     * @param roleId
     * @return
     */
    List<UmsPermission> getPermissionsByRoleId(@Param("roleId") Long roleId);

    /**
     * 批量插入角色和权限关系
     * @param umsRolePermissionRelations
     * @return
     */
    int insertRoleOfPermissionList(@Param("list")List<UmsRolePermissionRelation> umsRolePermissionRelations);
}
