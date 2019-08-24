package com.ym.mall.service;

import com.ym.mall.dto.OmsOrderQueryParams;
import com.ym.mall.model.OmsOrder;

import java.util.List;

/**
 * 订单管理的Service
 * @author matao
 * @create 2019-08-21 15:03
 */
public interface OmsOrderService {

    /**
     * 查询订单
     * @param queryParams
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<OmsOrder> getOrderList(OmsOrderQueryParams queryParams, Integer pageNum, Integer pageSize);

    /**
     * 根据Id批量关闭订单
     * @param ids
     * @param note
     * @return
     */
    int batchCloseOrderById(List<Long> ids,String note);

    /**
     * 根据Id批量删除订单
     * @param ids
     * @return
     */
    int batchDeleteOrderById(List<Long> ids);

    
}
