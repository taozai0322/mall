package com.ym.mall.controller;

import com.ym.mall.common.api.ResponseResult;
import com.ym.mall.dto.UmsAdminLoginParams;
import com.ym.mall.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 后台用户管理的Controller
 * @author matao
 * @create 2019-07-26 10:11
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "UmsAdminController",description = "后台用户管理")
@Slf4j
public class UmsAdminController {

    @Autowired
    private UmsAdminService umsAdminService;
    
    @ApiOperation(value="登录之后返回token")
    @PostMapping(value = "/login")
    public ResponseResult login(@RequestBody UmsAdminLoginParams umsAdminLoginParams){
        log.info("用户的登录信息：{}",umsAdminLoginParams);
        String token = umsAdminService.login(umsAdminLoginParams.getUserName(), umsAdminLoginParams.getPassword());
        if(token == null){
            return ResponseResult.fail("用户名或者密码错误");
        }
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        return ResponseResult.success(map);
    }

}
