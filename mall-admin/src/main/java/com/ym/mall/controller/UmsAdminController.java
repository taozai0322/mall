package com.ym.mall.controller;

import com.ym.mall.common.api.CommonPage;
import com.ym.mall.common.api.ResponseResult;
import com.ym.mall.common.api.ResultCode;
import com.ym.mall.dto.UmsAdminLoginParams;
import com.ym.mall.dto.UmsAdminRegisterParams;
import com.ym.mall.model.UmsAdmin;
import com.ym.mall.model.UmsPermission;
import com.ym.mall.model.UmsRole;
import com.ym.mall.service.UmsAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    
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

    @ApiOperation(value = "刷新Token")
    @GetMapping(value = "/token/reflesh")
    public ResponseResult refleshToken(HttpServletRequest request){
        String token = request.getHeader(this.tokenHeader);
        String refleshToken = umsAdminService.refleshToken(token);
        if(refleshToken == null){
            return ResponseResult.fail(ResultCode.NOEXPIRETOKEN.getCode(),ResultCode.NOEXPIRETOKEN.getMsg());
        }
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",refleshToken);
        tokenMap.put("tokenHead",tokenHead);
        return ResponseResult.success(tokenMap);
    }

    @ApiOperation(value = "获取当前登录用户的信息")
    @GetMapping(value = "/info")
    public ResponseResult getUserInfo(Principal principal){
        log.info("Principal获取的当前登录用户的信息：{}",principal.getName());
        UmsAdmin umsAdmin = umsAdminService.getAdminByUsername(principal.getName());
        List<UmsRole> umsRoleList = umsAdminService.getUmsRoleById(umsAdmin.getId());
        if(umsAdmin == null || umsRoleList.size() == 0){
            return ResponseResult.fail("获取当前登录用户的信息失败");
        }
        Map<String,Object> infoMap = new HashMap<>();
        infoMap.put("icon",umsAdmin.getIcon());
        infoMap.put("username",umsAdmin.getUsername());
        List roleList = new ArrayList<>();
        for (UmsRole umsRole:umsRoleList) {
            roleList.add(umsRole.getName());
        }
        infoMap.put("roles",roleList);
        return ResponseResult.success(infoMap);
    }

    @ApiOperation("根据用户名或分页获取用户列表")
    @GetMapping(value = "/list")//required:false  表示username 是非必传的
    public ResponseResult getUmsAdminList(@RequestParam(value = "username",required = false) String username,
                                          @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){
        List<UmsAdmin> umsAdminList = umsAdminService.getUmsAdminList(username, pageNum, pageSize);
        if(umsAdminList.size() == 0){
            return ResponseResult.fail("未查到用户信息");
        }
        return ResponseResult.success(CommonPage.restPage(umsAdminList));
    }

    @ApiOperation(value = "根据Id删除指定用户信息")
    @PostMapping(value = "/delete/{adminId}")
    public ResponseResult deleteById(@PathVariable("adminId") long id){
        int count = umsAdminService.deleteById(id);
        if(count > 0){
            return ResponseResult.success(count);
        }
        return ResponseResult.fail("删除指定用户信息失败");
    }

    @ApiOperation(value = "获取指定管理员用户的角色")
    @GetMapping(value = "/role/{adminId}")
    public ResponseResult getUmsRoleByAdminId(@PathVariable("adminId")long adminId){
        log.info("获取指定管理员用户的角色：{}",adminId);
        List<UmsRole> umsRoleList = umsAdminService.getUmsRoleByAdminId(adminId);
        if(umsRoleList == null || umsRoleList.size() == 0){
            return ResponseResult.success("查不到信息");
        }
        return ResponseResult.success(umsRoleList);
    }

    @ApiOperation(value = "修改指定用户的信息")
    @PostMapping(value = "/update/{adminId}")
    public ResponseResult updateUmsAdminByAdminId(@PathVariable("adminId") long adminId,@RequestBody UmsAdmin umsAdmin){
        int count = umsAdminService.updateUmsAdminByAdminId(adminId, umsAdmin);
        if(count > 0){
            return ResponseResult.success(count);
        }
        return ResponseResult.fail("修改指定用户的信息失败");
    }

    @ApiOperation(value = "给用户分配角色")
    @PostMapping(value = "/role/update")
    public ResponseResult updateRolesList(@RequestParam("adminId") Long adminId,
                                          @RequestParam("roleIds") List<Long> roleIds){
        log.info("给用户分配角色的入参adminId：{}",adminId);
        log.info("给用户分配角色的入参roleIds：{}",roleIds);
        int count = umsAdminService.updateAdminRole(adminId, roleIds);
        if(count > 0){
            return ResponseResult.success(count);
        }
        return ResponseResult.fail("给用户分配角色失败");
    }


    @ApiOperation(value = "获取指定用户的权限")
    @GetMapping(value = "/permission/{adminId}")
    public ResponseResult getPermissionByAdminId(@RequestParam("adminId") long adminId){
        List<UmsPermission> umsPermissionList = umsAdminService.getPermissionByAdminId(adminId);
        return ResponseResult.success(umsPermissionList);
    }
}
