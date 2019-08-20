package com.ym.mall.service.impl;

import com.ym.mall.dao.UmsRoleDao;
import com.ym.mall.mapper.UmsRoleMapper;
import com.ym.mall.mapper.UmsRolePermissionRelationMapper;
import com.ym.mall.model.*;
import com.ym.mall.service.UmsRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private UmsRolePermissionRelationMapper umsRolePermissionRelationMapper;
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

    /**
     * 修改角色权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    @Override
    public int updatePermissionOfRole(Long roleId, List<Long> permissionIds) {
        //1、先删除该角色原来对应的所有权限
        UmsRolePermissionRelationExample example = new UmsRolePermissionRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        umsRolePermissionRelationMapper.deleteByExample(example);
        //2、批量插入新的角色和权限的对应关系
        List<UmsRolePermissionRelation> relationList = new ArrayList<>();
        for (Long permissionId: permissionIds) {
            UmsRolePermissionRelation rolePermissionRelation = new UmsRolePermissionRelation();
            rolePermissionRelation.setRoleId(roleId);
            rolePermissionRelation.setPermissionId(permissionId);
            relationList.add(rolePermissionRelation);
        }
        int count = umsRoleDao.insertRoleOfPermissionList(relationList);
        return count;
    }

    /**
     * 修改角色
     * @param roleId
     * @param role
     * @return
     */
    @Override
    public int updateRole(long roleId, UmsRole role) {
        role.setId(roleId);
        return umsRoleMapper.updateByPrimaryKey(role);
    }
}
