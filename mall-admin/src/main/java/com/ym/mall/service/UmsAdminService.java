package com.ym.mall.service;

import com.ym.mall.dto.UmsAdminRegisterParams;
import com.ym.mall.model.UmsAdmin;
import com.ym.mall.model.UmsPermission;
import com.ym.mall.model.UmsRole;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台管理员的Service
 * @author matao
 * @create 2019-07-26 14:29
 */
public interface UmsAdminService {

    /**
     * 根据用户名获取后台管理员
     * @param username
     * @return
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 后台管理员用户注册
     * @param registerParams
     * @return
     */
    UmsAdmin register(UmsAdminRegisterParams registerParams);
    /**
     * 登录功能
     * @param userName
     * @param password
     * @return   生成JWT的token
     */
    String login(String userName,String password);

    /**
     * 获取用户的所有权限（包括+-权限）
     * @param adminId
     * @return
     */
    List<UmsPermission> getPermissionList(long adminId);

    /**
     * 刷新Token的功能
     * @param oldToken 旧的Token
     * @return
     */
    String refleshToken(String oldToken);

    /**
     * 查询后台管理用户所有的角色
     * @param id
     * @return
     */
    List<UmsRole> getUmsRoleById(long id);

    /**
     * 根据用户名或者昵称查询用户列表
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<UmsAdmin> getUmsAdminList(String name, Integer pageNum,Integer pageSize);

    /**
     *根据管理员的用户Id 删除指定用户信息
     * @param id
     * @return
     */
    int deleteById(long id);

    /**
     * 获取指定的管理员用户的角色
     * @param id
     * @return
     */
    List<UmsRole> getUmsRoleByAdminId(long adminId);

    /**
     * 修改指定用户信息
     * @param adminId
     * @param umsAdmin
     * @return
     */
    int updateUmsAdminByAdminId(long adminId,UmsAdmin umsAdmin);

    /**
     * 修改用户角色关系
     * @param adminId
     * @param roleIds
     * @return
     */
    @Transactional
    int updateAdminRole(Long adminId,List<Long> roleIds);

    /**
     * 获取指定用户的权限
     * @param adminId
     * @return
     */
    List<UmsPermission> getPermissionByAdminId(long adminId);
}
