package com.ym.mall.controller;

import com.ym.mall.common.api.ResponseResult;
import com.ym.mall.model.OmsCompanyAddress;
import com.ym.mall.service.OmsCompanyAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 收货地址管理的Controller
 * @author matao
 * @create 2019-08-21 11:54
 */
@Api(tags = "OmsCompanyAddressController",description = "收货地址管理")
@RestController
@RequestMapping(value = "/companyAddress")
public class OmsCompanyAddressController {

    @Autowired
    private OmsCompanyAddressService omsCompanyAddressService;

    @ApiOperation(value = "获取所有的收货地址")
    @GetMapping(value = "/listAll")
    public ResponseResult getAllOmsCompanyAddress(){
        List<OmsCompanyAddress> addressList = omsCompanyAddressService.getAllOmsCompanyAddress();
        return ResponseResult.success(addressList);
    }
}
