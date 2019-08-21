package com.ym.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.ym.mall.dao.OmsOrderDao;
import com.ym.mall.dto.OmsOrderQueryParams;
import com.ym.mall.mapper.OmsOrderMapper;
import com.ym.mall.model.OmsOrder;
import com.ym.mall.service.OmsOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单管理Service的实现类
 * @author matao
 * @create 2019-08-21 15:07
 */
@Slf4j
@Service
public class OmsOrderServiceImpl implements OmsOrderService {

    @Autowired
    private OmsOrderDao omsOrderDao;
    @Autowired
    private OmsOrderMapper omsOrderMapper;
    /**
     * 查询订单
     * @param queryParams
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<OmsOrder> getOrderList(OmsOrderQueryParams queryParams, Integer pageNum, Integer pageSize) {
        //分页查询
        PageHelper.startPage(pageNum,pageSize);
        return omsOrderDao.getOrderList(queryParams);
    }
}
