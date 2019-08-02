package com.ym.mall.service.impl;

import com.ym.mall.dao.UmsAdminRoleRelationDao;
import com.ym.mall.dto.UmsAdminRegisterParams;
import com.ym.mall.mapper.UmsAdminLoginLogMapper;
import com.ym.mall.mapper.UmsAdminMapper;
import com.ym.mall.model.UmsAdmin;
import com.ym.mall.model.UmsAdminExample;
import com.ym.mall.model.UmsAdminLoginLog;
import com.ym.mall.model.UmsPermission;
import com.ym.mall.service.UmsAdminService;
import com.ym.mall.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private UmsAdminLoginLogMapper umsAdminLoginLogMapper;
    @Autowired
    private UmsAdminMapper umsAdminMapper;
    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;

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
}
