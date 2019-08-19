package com.ym.mall.service.impl;

import com.ym.mall.dao.UmsRoleDao;
import com.ym.mall.mapper.UmsRoleMapper;
import com.ym.mall.model.UmsPermission;
import com.ym.mall.model.UmsRole;
import com.ym.mall.model.UmsRoleExample;
import com.ym.mall.service.UmsRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 后台用户的角色管理
 * @author matao
 * @create 2019-08-19 16:51
 */
@Slf4j
@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleMapper umsRoleMapper;
    @Autowired
    private UmsRoleDao umsRoleDao;
    /**
     * 添加新角色
     * @param umsRole
     * @return
     */
    @Override
    public int create(UmsRole umsRole) {
        umsRole.setAdminCount(0);
        umsRole.setCreateTime(new Date());
        umsRole.setSort(0);
        umsRole.setStatus(1); //启用状态：0->禁用；1->启用
        return umsRoleMapper.insert(umsRole);
    }

    /**
     * 根据Ids批量删除角色
     * @param ids
     * @return
     */
    @Override
    public int BatchDeleteRoleByIds(List<Long> ids) {
        int count = umsRoleDao.BatchDeleteRoleById(ids);
        return count;
    }

    /**
     * 获取所有的角色列表
     * @return
     */
    @Override
    public List<UmsRole> getAllRole() {
        return umsRoleMapper.selectByExample(new UmsRoleExample());
    }

    /**
     * 获取相应角色的权限
     * @param roleId
     * @return
     */
    @Override
    public List<UmsPermission> getPermissionsByRoleId(Long roleId) {
        List<UmsPermission> permissions = umsRoleDao.getPermissionsByRoleId(roleId);
        return permissions;
    }
}
