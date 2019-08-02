package com.ym.mall.service;

import com.ym.mall.dao.UmsAdminRoleRelationDao;
import com.ym.mall.mapper.UmsAdminLoginLogMapper;
import com.ym.mall.mapper.UmsAdminMapper;
import com.ym.mall.model.UmsAdmin;
import com.ym.mall.model.UmsAdminExample;
import com.ym.mall.model.UmsAdminLoginLog;
import com.ym.mall.model.UmsPermission;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author matao
 * @create 2019-08-01 10:00
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class UmsAdminServiceTest {

    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Autowired
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;

    @Autowired
    private UmsAdminLoginLogMapper umsAdminLoginLogMapper;
    @Test
    public void queryUser(){
        UmsAdminExample umsAdminExample = new UmsAdminExample();
        umsAdminExample.createCriteria().andUsernameEqualTo("admin");
        List<UmsAdmin> umsAdmins = umsAdminMapper.selectByExample(umsAdminExample);
        log.info("查询的总记录数：{}",umsAdmins.size());
        for (UmsAdmin ums:umsAdmins) {
            log.info("用户的信息：{}",ums);
        }
    }

    @Test
    public void queryAdminPermission(){
        List<UmsPermission> permissionList = umsAdminRoleRelationDao.getPermissionList(3);
        log.info("查询的权限的总记录数：{}",permissionList.size());
        for(UmsPermission umsPermission:permissionList){
            log.info("遍历权限：{}",umsPermission);
        }

    }

    @Test
    public void loginLog(){
        UmsAdminLoginLog umsAdminLoginLog = new UmsAdminLoginLog();
        umsAdminLoginLog.setIp("192.168.121.12");
        umsAdminLoginLog.setAddress("重庆");
        umsAdminLoginLog.setCreateTime(new Date());
        umsAdminLoginLogMapper.insert(umsAdminLoginLog);

    }
}