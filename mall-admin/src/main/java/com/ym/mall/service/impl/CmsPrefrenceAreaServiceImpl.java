package com.ym.mall.service.impl;

import com.ym.mall.mapper.CmsPrefrenceAreaMapper;
import com.ym.mall.model.CmsPrefrenceArea;
import com.ym.mall.model.CmsPrefrenceAreaExample;
import com.ym.mall.service.CmsPrefrenceAreaService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品优选管理Service的实现类
 * @author matao
 * @create 2019-08-21 9:33
 */
@Service
public class CmsPrefrenceAreaServiceImpl implements CmsPrefrenceAreaService {

    @Autowired
    private CmsPrefrenceAreaMapper cmsPrefrenceAreaMapper;

    @Override
    public List<CmsPrefrenceArea> getAllPrefrenceArea() {
        return cmsPrefrenceAreaMapper.selectByExample(new CmsPrefrenceAreaExample());
    }
}
