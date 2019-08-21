package com.ym.mall.controller;

import com.ym.mall.common.api.ResponseResult;
import com.ym.mall.model.CmsPrefrenceArea;
import com.ym.mall.service.CmsPrefrenceAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品优选管理的Controller
 * @author matao
 * @create 2019-08-21 9:27
 */
@RestController
@Api(tags = "CmsPrefrenceAreaController",description = "商品优选管理")
@RequestMapping(value = "/prefrenceArea")
public class CmsPrefrenceAreaController {

    @Autowired
    private CmsPrefrenceAreaService cmsPrefrenceAreaService;

    @ApiOperation(value = "获取所有的商品优选")
    @GetMapping(value = "listAll")
    public ResponseResult getAllPrefrenceArea(){
        List<CmsPrefrenceArea> cmsPrefrenceAreaList = cmsPrefrenceAreaService.getAllPrefrenceArea();
        return ResponseResult.success(cmsPrefrenceAreaList);
    }
}
