package com.ym.mall.controller;

import com.ym.mall.common.api.ResponseResult;
import com.ym.mall.dto.UmsAdminLoginParams;
import com.ym.mall.dto.UmsAdminRegisterParams;
import com.ym.mall.model.UmsAdmin;
import com.ym.mall.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    
    @ApiOperation(value="登录之后返回token")
    @PostMapping(value = "/login")
    public ResponseResult login(@RequestBody UmsAdminLoginParams umsAdminLoginParams){
        log.info("用户的登录信息：{}",umsAdminLoginParams);
        String token = umsAdminService.login(umsAdminLoginParams.getUserName(), umsAdminLoginParams.getPassword());
        if(token == null){
            return ResponseResult.fail("用户名或者密码错误");
        }
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return ResponseResult.success(tokenMap);
    }

    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public ResponseResult register(@RequestBody UmsAdminRegisterParams umsAdminRegisterParams){
        log.info("用户的注册信息：{}",umsAdminRegisterParams);
        UmsAdmin umsAdmin = umsAdminService.register(umsAdminRegisterParams);
        if(umsAdmin == null){
            return ResponseResult.fail("注册失败,用户名可能重复");
        }
        return ResponseResult.success(umsAdmin);
    }
}
