package com.ym.mall.service;

import com.ym.mall.model.OmsCompanyAddress;

import java.util.List;

/**
 * 收货地址管理的Service
 * @author matao
 * @create 2019-08-21 11:58
 */
public interface OmsCompanyAddressService {

    /**
     * 获取所有的收货地址
     * @return
     */
    List<OmsCompanyAddress> getAllOmsCompanyAddress();
}
