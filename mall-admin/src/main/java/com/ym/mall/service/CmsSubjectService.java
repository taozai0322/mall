package com.ym.mall.service;

import com.ym.mall.model.CmsSubject;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * 商品专题管理的Service
 * @author matao
 * @create 2019-08-21 10:29
 */
public interface CmsSubjectService {

    /**
     * 获取所有的商品专题
     * @return
     */
    List<CmsSubject> getAllCmsSubject();

    /**
     * 分页或者根据关键字查询
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<CmsSubject> getCmsSubjectByPageOrKeyword(String keyword, Integer pageNum, Integer pageSize);
}
