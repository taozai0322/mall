package com.ym.mall.bo;

import com.ym.mall.model.UmsAdmin;
import com.ym.mall.model.UmsPermission;
import com.ym.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UmsAdminConfig实现UserDetailsService接口重写loadUserByUsername方法
 * @author matao
 * @create 2019-08-01 11:52
 */
@Service
public class UmsAdminLoginService implements UserDetailsService {
    @Autowired
    private com.ym.mall.service.UmsAdminService umsAdminService;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UmsAdmin umsAdmin = umsAdminService.getAdminByUsername(s);
        if(umsAdmin == null){
            throw new UsernameNotFoundException("用户名或者密码错误");
        }
        List<UmsPermission> permissionList = umsAdminService.getPermissionList(umsAdmin.getId());
        AdminUserDetails userDetails = new AdminUserDetails(umsAdmin, permissionList);
        return userDetails;
    }
}
