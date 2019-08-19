package com.ym.mall.controller;

import com.ym.mall.common.api.ResponseResult;
import com.ym.mall.model.UmsRole;
import com.ym.mall.service.UmsRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台用户的角色管理
 * @author matao
 * @create 2019-08-19 16:47
 */
@RestController
@Api(tags = "UmsRoleController",description = "后台用户的角色管理")
@RequestMapping(value = "/role")
@Slf4j
public class UmsRoleController {

    @Autowired
    private UmsRoleService umsRoleService;

    @ApiOperation(value = "创建新角色")
    @PostMapping(value = "/create")
    public ResponseResult creatRole(@RequestBody UmsRole umsRole){
        int count = umsRoleService.create(umsRole);
        if(count > 0){
            return ResponseResult.success(count);
        }
        return ResponseResult.fail("创建新角色失败");
    }

    @ApiOperation(value = "根据id批量删除角色")
    @PostMapping(value = "/delete")
    public ResponseResult batchDeleteByIds(@RequestParam("ids") List<Long> ids){
        log.info("根据id批量删除角色:{}",ids);
        int count = umsRoleService.BatchDeleteRoleByIds(ids);
        if(count > 0){
            return ResponseResult.success(count);
        }
        return ResponseResult.fail("根据id批量删除角色失败");
    }

    @ApiOperation(value = "获取所有的角色列表")
    @GetMapping(value = "/list")
    public ResponseResult getAllRole(){
        return ResponseResult.success(umsRoleService.getAllRole());
    }

    @ApiOperation(value = "获取相应角色的权限")
    @GetMapping(value = "/permission/{roleId}")
    public ResponseResult getPermissionsByRoleId(@PathVariable("roleId") Long roleId){
        return ResponseResult.success(umsRoleService.getPermissionsByRoleId(roleId));
    }
}
