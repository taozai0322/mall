package com.ym.mall.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ym.mall.dao.UmsAdminRoleRelationDao;
import com.ym.mall.dto.UmsAdminRegisterParams;
import com.ym.mall.mapper.UmsAdminLoginLogMapper;
import com.ym.mall.mapper.UmsAdminMapper;
import com.ym.mall.mapper.UmsAdminRoleRelationMapper;
import com.ym.mall.model.*;
import com.ym.mall.service.UmsAdminService;
import com.ym.mall.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * UmsAdminService的实现类
 * @author matao
 * @create 2019-07-26 14:41
 */
@Service
@Slf4j
public class UmsAdminServiceImpl implements UmsAdminService {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsAdminLoginLogMapper umsAdminLoginLogMapper;
    @Autowired
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;
    @Autowired
    private UmsAdminRoleRelationMapper umsAdminRoleRelationMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * 根据用户名获取后台管理员
     * @param username
     * @return
     */
    @Override
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(username);
        //查询
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectByExample(umsAdminExample);
        if(umsAdmins != null && umsAdmins.size()>0){
            return umsAdmins.get(0);
        }
        return null;
    }

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    @Override
    public String login(String userName, String password) {
        String token = null;
        try {
            //密码需要客户端加密后传递
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            log.info("打印密码：{}",userDetails.getPassword());
            //密码校验
            if(!password.equals(userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            //围绕该用户建立安全上下文信息
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);  //JWT生成Token
            log.info("登录时JWT生成的TOKEN为：{}",token);
            //记录登录信息
            insertLoginLog(userName);
        }catch (AuthenticationException e){
            log.info("登录异常：{}",e.getMessage());
        }
        return token;
    }

    /**
     * 添加登录记录
     * @param username
     */
    public void insertLoginLog(String username){
        //根据用户名获取后台管理员对象
        UmsAdmin umsAdmin = getAdminByUsername(username);
        UmsAdminLoginLog adminLoginLog = new UmsAdminLoginLog();
        adminLoginLog.setAdminId(umsAdmin.getId());
        adminLoginLog.setCreateTime(new Date());
        //获取httpServletRequest
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        adminLoginLog.setIp(request.getRemoteAddr());  //获取IP
        umsAdminLoginLogMapper.insert(adminLoginLog);
    }
    /**
     * 获取用户的所有权限（包括+-权限）
     * @param adminId
     * @return
     */
    @Override
    public List<UmsPermission> getPermissionList(long adminId) {
        return umsAdminRoleRelationDao.getPermissionList(adminId);
    }

    /**
     * 后台管理员用户注册
     * @param registerParams
     * @return
     */
    @Override
    public UmsAdmin register(UmsAdminRegisterParams registerParams) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(registerParams,umsAdmin);
        umsAdmin.setCreateTime(new Date());
        umsAdmin.setStatus(1);//帐号启用状态：0->禁用；1->启用
        //查询是否相同用户名的用户
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(umsAdminExample);
        if(umsAdminList.size() > 0){
            return null;
        }
        umsAdminMapper.insert(umsAdmin);
        umsAdmin.setPassword(null);
        return umsAdmin;
    }

    /**
     * 刷新Token的功能
     * @param oldToken 旧的Token
     * @return
     */
    @Override
    public String refleshToken(String oldToken) {
        String token = oldToken.substring(tokenHead.length());
        //判断Token是否能够刷新
        if(jwtTokenUtil.canReflesh(token)){
            log.info("token可以刷新：{}");
            return jwtTokenUtil.refleshToken(token);
        }
        return null;
    }

    /**
     * 查询后台管理用户所有的角色
     * @param id
     * @return
     */
    @Override
    public  List<UmsRole> getUmsRoleById(long id) {
        return umsAdminRoleRelationDao.getUmsRoleList(id);
    }

    /**
     * 根据用户名或者昵称查询用户列表
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<UmsAdmin> getUmsAdminList(String name, Integer pageNum, Integer pageSize) {
        if(StringUtils.isEmpty(name)){
            List<UmsAdmin> umsAdminList = umsAdminRoleRelationDao.getUmsAdminList(pageNum-1, pageSize);
            return umsAdminList;
        }
        List<UmsAdmin> umsAdmins = new ArrayList<>();
        UmsAdmin umsAdmin = umsAdminRoleRelationDao.getUmsAdminByName(name);
        umsAdmins.add(umsAdmin);
        return umsAdmins;
    }

    /**
     * 根据管理员的用户Id 删除指定用户信息
     * @param id
     * @return
     */
    @Override
    public int deleteById(long id) {
        return umsAdminMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取指定的管理员用户的角色
     * @param adminId
     * @return
     */
    @Override
    public List<UmsRole> getUmsRoleByAdminId(long adminId) {
        return umsAdminRoleRelationDao.getUmsRoleList(adminId);
    }

    /**
     * 修改指定用户的信息
     * @param adminId
     * @param umsAdmin
     * @return
     */
    @Override
    public int updateUmsAdminByAdminId(long adminId, UmsAdmin umsAdmin) {
        umsAdmin.setId(adminId);
        umsAdmin.setPassword(null);
        return umsAdminMapper.updateByPrimaryKeySelective(umsAdmin);
    }

    /**
     * 修改用户角色关系
     * @param adminId
     * @param roleIds
     * @return
     */
    @Override
    public int updateAdminRole(Long adminId, List<Long> roleIds) {
        //获取角色的数量
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除之前的角色对应关系
        UmsAdminRoleRelationExample umsAdminRoleRelationExample = new UmsAdminRoleRelationExample();
        umsAdminRoleRelationExample.createCriteria().andAdminIdEqualTo(adminId);
        umsAdminRoleRelationMapper.deleteByExample(umsAdminRoleRelationExample);
        //建立新的角色对应关系
        if(!CollectionUtil.isEmpty(roleIds)){
            List<UmsAdminRoleRelation>  list = new ArrayList<>();
            for(long roleId:roleIds){
                UmsAdminRoleRelation adminRoleRelation = new UmsAdminRoleRelation();
                adminRoleRelation.setAdminId(adminId);
                adminRoleRelation.setRoleId(roleId);
                list.add(adminRoleRelation);
            }
            umsAdminRoleRelationDao.insertRolesList(list);
        }
        return count;
    }

    /**
     * 获取指定用户的权限
     * @param adminId
     * @return
     */
    @Override
    public List<UmsPermission> getPermissionByAdminId(long adminId) {
        return umsAdminRoleRelationDao.getPermissionByAdminId(adminId);
    }
}
