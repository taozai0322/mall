package com.ym.mall.bo;

import com.ym.mall.model.UmsAdmin;
import com.ym.mall.model.UmsPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * SpringSecurity需要的用户详情
 * @author matao
 * @create 2019-08-01 9:31
 */
public class AdminUserDetails implements UserDetails {

    private UmsAdmin umsAdmin;

    private List<UmsPermission> permissionList;   //permission允许，许可
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
    }

    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsPermission> permissionList) {
        this.umsAdmin = umsAdmin;
        this.permissionList = permissionList;
    }

    /**
     * 账号是否过期，过期无法验证
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 是否已经过期的用户凭据（密码），过期的凭据防止认证
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否被禁用，禁用的用户不能进行身份验证
     * @return
     */
    @Override
    public boolean isEnabled() {
        return umsAdmin.getStatus().equals(1); //帐号启用状态：0->禁用；1->启用
    }
}
