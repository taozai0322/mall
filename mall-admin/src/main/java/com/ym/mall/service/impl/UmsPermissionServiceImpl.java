package com.ym.mall.service.impl;

import com.ym.mall.dao.UmsPermissionDao;
import com.ym.mall.dto.UmsPermissionNode;
import com.ym.mall.mapper.UmsPermissionMapper;
import com.ym.mall.model.UmsPermission;
import com.ym.mall.model.UmsPermissionExample;
import com.ym.mall.service.UmsPermissionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台用户权限管理service的实现类
 * @author matao
 * @create 2019-08-15 11:27
 */
@Service
@Slf4j
public class UmsPermissionServiceImpl implements UmsPermissionService {

    @Autowired
    private UmsPermissionMapper umsPermissionMapper;
    @Autowired
    private UmsPermissionDao umsPermissionDao;

    /**
     * 添加权限
     * @param umsPermission
     * @return
     */
    @Override
    public int create(UmsPermission umsPermission) {
        umsPermission.setStatus(1);//启用状态；0->禁用；1->启用
        umsPermission.setCreateTime(new Date());
        umsPermission.setSort(0);
        return umsPermissionMapper.insert(umsPermission);
    }

    /**
     * 根据ID修改权限
     * @param id
     * @param umsPermission
     * @return
     */
    @Override
    public int updatePermission(Long id, UmsPermission umsPermission) {
        umsPermission.setId(id);
        int count = umsPermissionMapper.updateByPrimaryKey(umsPermission);
        return count;
    }

    /**
     * 获取所有的权限列表
     * @return
     */
    @Override
    public List<UmsPermission> getPermissionList() {
        UmsPermissionExample umsPermissionExample = new UmsPermissionExample();
        return umsPermissionMapper.selectByExample(umsPermissionExample);
    }

    /**
     * 根据Id批量删除权限
     * @param ids
     * @return
     */
    @Override
    public int BatchDeletePermissionById(List<Long> ids) {
        int count = umsPermissionDao.BatchDeletePermissionById(ids);
        return count;
    }

    /**
     * 以层级结构返回所有的权限
     * @return
     */
    @Override
    public List<UmsPermissionNode> treeList() {
        //查询所有
        List<UmsPermission> permissionList = umsPermissionMapper.selectByExample(new UmsPermissionExample());
        List<UmsPermissionNode> result = permissionList.stream()
                .filter(permission -> permission.getPid().equals(0L))
                .map(permission -> covert(permission,permissionList)).collect(Collectors.toList());
        return result;
    }

    /**
     * 将权限转换为带有子级的权限对象
     * 当找不到子级权限的时候map操作不会再递归调用covert
     */
    private UmsPermissionNode covert(UmsPermission permission,List<UmsPermission> permissionList){
        UmsPermissionNode node = new UmsPermissionNode();
        BeanUtils.copyProperties(permission,node);
        List<UmsPermissionNode> children = permissionList.stream()
                .filter(subPermission -> subPermission.getPid().equals(permission.getId()))
                .map(subPermission -> covert(subPermission,permissionList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }


}
