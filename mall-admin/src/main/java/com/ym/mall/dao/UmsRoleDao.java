package com.ym.mall.dao;

import com.ym.mall.model.UmsPermission;
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
}
