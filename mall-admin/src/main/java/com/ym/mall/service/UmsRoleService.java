package com.ym.mall.service;

import com.ym.mall.model.UmsPermission;
import com.ym.mall.model.UmsRole;

import java.util.List;

/**
 * 后台用户角色管理Service
 * @author matao
 * @create 2019-08-19 16:49
 */
public interface UmsRoleService {

    /**
     * 添加新角色
     * @param umsRole
     * @return
     */
    int create(UmsRole umsRole);

    /**
     * 根据Ids批量删除角色
     * @param ids
     * @return
     */
    int BatchDeleteRoleByIds(List<Long> ids);

    /**
     * 获取所有的角色列表
     * @return
     */
    List<UmsRole> getAllRole();

    /**
     * 获取相应角色的权限
     * @param roleId
     * @return
     */
    List<UmsPermission> getPermissionsByRoleId(Long roleId);
}
