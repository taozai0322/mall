package com.ym.mall.controller;

import com.ym.mall.common.api.CommonPage;
import com.ym.mall.common.api.ResponseResult;
import com.ym.mall.model.CmsSubject;
import com.ym.mall.service.CmsSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品专题管理的Controller
 * @author matao
 * @create 2019-08-21 10:15
 */
@Api(tags = "CmsSubjectController",description = "商品专题管理")
@RestController
@RequestMapping(value = "/subject")
public class CmsSubjectController {

    @Autowired
    private CmsSubjectService cmsSubjectService;

    @ApiOperation(value = "获取全部的商品主题")
    @GetMapping(value = "/listAll")
    public ResponseResult getAllCmsSubject(){
        List<CmsSubject> allCmsSubject = cmsSubjectService.getAllCmsSubject();
        return ResponseResult.success(allCmsSubject);
    }

    @ApiOperation(value = "根据专题名称分页获取专题")
    @GetMapping(value = "/list")
    public ResponseResult getCmsSubjectByPageOrKeyword(@RequestParam(value = "keyword",required = false) String keyword,
                                                       @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                       @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize){
        List<CmsSubject> cmsSubjects = cmsSubjectService.getCmsSubjectByPageOrKeyword(keyword, pageNum, pageSize);
        return  ResponseResult.success(CommonPage.restPage(cmsSubjects));
    }
}
