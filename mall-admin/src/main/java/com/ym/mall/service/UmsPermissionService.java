package com.ym.mall.service;

import com.ym.mall.dto.UmsPermissionNode;
import com.ym.mall.model.UmsPermission;

import java.util.List;

/**
 * 后台用户权限管理service
 * @author matao
 * @create 2019-08-15 11:26
 */
public interface UmsPermissionService {

    /**
     * 添加权限
     * @param umsPermission
     * @return
     */
    int create(UmsPermission umsPermission);

    /**
     * 根据Id修改权限
     * @param id
     * @param umsPermission
     * @return
     */
    int updatePermission(Long id,UmsPermission umsPermission);

    /**
     * 获取所有的权限列表
     * @return
     */
    List<UmsPermission> getPermissionList();

    /**
     * 根据ID批量删除权限
     * @param ids
     * @return
     */
    int BatchDeletePermissionById(List<Long> ids);

    /**
     * 以层级结构返回所有权限
     * @return
     */
    List<UmsPermissionNode> treeList();
}
