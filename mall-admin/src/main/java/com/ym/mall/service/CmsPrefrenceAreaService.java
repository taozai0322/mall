package com.ym.mall.service;

import com.ym.mall.model.CmsPrefrenceArea;

import java.util.List;

/**
 * 商品优选管理的Service
 * @author matao
 * @create 2019-08-21 9:32
 */
public interface CmsPrefrenceAreaService {

    /**
     * 查询所有的优选商品
     * @return
     */
    List<CmsPrefrenceArea> getAllPrefrenceArea();
}
