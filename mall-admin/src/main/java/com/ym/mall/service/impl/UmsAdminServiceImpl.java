package com.ym.mall.service.impl;

import com.ym.mall.dao.UmsAdminRoleRelationDao;
import com.ym.mall.mapper.UmsAdminMapper;
import com.ym.mall.model.UmsAdmin;
import com.ym.mall.model.UmsAdminExample;
import com.ym.mall.model.UmsPermission;
import com.ym.mall.service.UmsAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UmsAdminService的实现类
 * @author matao
 * @create 2019-07-26 14:41
 */
@Service
@Slf4j
public class UmsAdminServiceImpl implements UmsAdminService {

    @Autowired
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;


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
            //密码校验
            if(password.equals(userDetails.getPassword())){
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (AuthenticationException e){
            log.info("登录异常：{}",e.getMessage());
        }
        return null;
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
}
