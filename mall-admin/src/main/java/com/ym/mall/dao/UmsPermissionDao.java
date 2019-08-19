package com.ym.mall.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台用户管理管理DAO
 * @author matao
 * @create 2019-08-15 16:58
 */
public interface UmsPermissionDao {

    /**
     * 根据Id批量删除权限
     * @param ids
     * @return
     */
    int BatchDeletePermissionById(@Param("ids") List<Long> ids);
}
