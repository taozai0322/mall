package com.ym.mall.service;

import com.ym.mall.dao.OmsOrderDao;
import com.ym.mall.dao.UmsAdminRoleRelationDao;
import com.ym.mall.mapper.UmsAdminLoginLogMapper;
import com.ym.mall.mapper.UmsAdminMapper;
import com.ym.mall.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

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

    @Test
    public void getUserList(){
//        List<UmsAdmin> umsAdminList = umsAdminRoleRelationDao.getUmsAdminList( 1, 5);
        UmsAdmin admin = umsAdminRoleRelationDao.getUmsAdminByName("taozai");
        log.info("打印的UserList:{}",admin);
     }

     @Autowired
    private OmsOrderDao omsOrderDao;

    @Test
    public void omsOrderDao(){
        List ids = new ArrayList<>();
        Map<String,Long> map = new HashMap<>();
        map.put("id",23L);
        ids.add(map);
        int count = omsOrderDao.batchCloseOrder(ids);
        log.info("count:{}",count);
    }

    @Test
    public void insertOperate(){
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(12L);
        history.setCreateTime(new Date());
        history.setNote("测试一下");
        history.setOperateMan("tao");
        List<OmsOrderOperateHistory> list = new ArrayList<>();
        list.add(history);
        int count = omsOrderDao.insertOrderOpretaHistory(list);
        log.info("count:{}",count);

    }
}