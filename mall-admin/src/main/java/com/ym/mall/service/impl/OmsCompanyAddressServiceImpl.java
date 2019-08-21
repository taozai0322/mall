package com.ym.mall.service.impl;

import com.ym.mall.mapper.OmsCompanyAddressMapper;
import com.ym.mall.model.OmsCompanyAddress;
import com.ym.mall.model.OmsCompanyAddressExample;
import com.ym.mall.service.OmsCompanyAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收货地址管理Service的实现类
 * @author matao
 * @create 2019-08-21 12:00
 */
@Service
public class OmsCompanyAddressServiceImpl implements OmsCompanyAddressService {

    @Autowired
    private OmsCompanyAddressMapper omsCompanyAddressMapper;

    /**
     * 获取所有的收货地址
     * @return
     */
    @Override
    public List<OmsCompanyAddress> getAllOmsCompanyAddress() {
        return omsCompanyAddressMapper.selectByExample(new OmsCompanyAddressExample());
    }
}
