package com.ym.mall.controller;

import com.ym.mall.common.api.ResponseResult;
import com.ym.mall.dto.UmsPermissionNode;
import com.ym.mall.model.UmsPermission;
import com.ym.mall.service.UmsPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 后台用户的权限管理
 * @author matao
 * @create 2019-08-15 10:39
 */
@RestController
@RequestMapping(value = "/permission")
@Api(tags = "UmsPermissionController",description = "后台用户的权限管理")
public class UmsPermissionController {

    @Autowired
    private UmsPermissionService umsPermissionService;

    @ApiOperation(value = "添加权限")
    @PostMapping(value = "/create")
    public ResponseResult create(@RequestBody UmsPermission umsPermission){
        int count = umsPermissionService.create(umsPermission);
        if(count > 0){
            return ResponseResult.success(count);
        }
        return ResponseResult.fail("添加权限失败");
    }

    @ApiOperation(value = "根据ID修改权限")
    @PostMapping(value = "/update/{id}")
    public ResponseResult updatePermission(@PathVariable("id") Long id, @RequestBody UmsPermission umsPermission){
        int count = umsPermissionService.updatePermission(id, umsPermission);
        if(count > 0){
            return ResponseResult.success(count);
        }
        return ResponseResult.fail("修改权限失败");
    }

    @ApiOperation(value = "获取所有权限列表")
    @GetMapping(value = "/list")
    public ResponseResult getPermissionList(){
        List<UmsPermission> permissionList = umsPermissionService.getPermissionList();
        return ResponseResult.success(permissionList);
    }

    @ApiOperation(value = "根据Id批量删除权限")
    @PostMapping(value = "/delete")
    public ResponseResult BatchDeletePermissionById(@RequestParam("ids") List<Long> ids){
        int count = umsPermissionService.BatchDeletePermissionById(ids);
        if(count > 0){
            return ResponseResult.success(count);
        }
        return ResponseResult.fail("根据Id批量删除权限失败");
    }

    @ApiOperation(value = "以层级结构查询所有的权限")
    @GetMapping(value = "/treeList")
    public ResponseResult treeList(){
        List<UmsPermissionNode> permissionNodes = umsPermissionService.treeList();
        return ResponseResult.success(permissionNodes);
    }
}
