package com.ym.mall.controller;

import com.ym.mall.common.api.ResponseResult;
import com.ym.mall.dto.UmsAdminLoginParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @ApiOperation(value="登录之后返回token")
    @PostMapping(value = "/login")
    public ResponseResult login(@RequestBody UmsAdminLoginParams umsAdminLoginParams){
        log.info("用户的登录信息：{}",umsAdminLoginParams);
        return null;
    }

}
