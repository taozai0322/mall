package com.ym.mall.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.ym.mall.dao.OmsOrderDao;
import com.ym.mall.dto.OmsOrderQueryParams;
import com.ym.mall.mapper.OmsOrderMapper;
import com.ym.mall.model.OmsOrder;
import com.ym.mall.model.OmsOrderOperateHistory;
import com.ym.mall.service.OmsOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    /**
     * 根据Id批量删除订单
     * @param ids
     * @param note
     * @return
     */
    @Override
    public int batchCloseOrderById(List<Long> ids,String note) {
        //删除订单列表的订单
        List list = new ArrayList<>();
        for(Long id :ids){
            JSONObject object = new JSONObject();
            object.put("id",id);
             list.add(object);
        }
        log.info("批量的入参：{}",list);
        omsOrderDao.batchCloseOrder(list);
        //记录的关闭订单的操作
        List<OmsOrderOperateHistory> historyList = new ArrayList<>();
        for(Long orderId:ids){
            OmsOrderOperateHistory operateHistory = new OmsOrderOperateHistory();
            operateHistory.setOperateMan("taozai"); //暂时设置，以后从Session中获取
            operateHistory.setNote(note);
            operateHistory.setOrderId(orderId);
            operateHistory.setOrderStatus(4);//4：关闭订单
            operateHistory.setCreateTime(new Date());
            historyList.add(operateHistory);
        }
        int count = omsOrderDao.insertOrderOpretaHistory(historyList);
        return count;
    }

    /**
     * 根据Id批量删除订单
     * @param ids
     * @return
     */
    @Override
    public int batchDeleteOrderById(List<Long> ids) {
        //删除订单
        omsOrderDao.batchDeleteOrder(ids);
        //记录的删除订单的操作
        List<OmsOrderOperateHistory> historyList = new ArrayList<>();
        for(Long orderId:ids){
            OmsOrderOperateHistory operateHistory = new OmsOrderOperateHistory();
            operateHistory.setOperateMan("taozai"); //暂时设置，以后从Session中获取
            operateHistory.setNote("删除订单");
            operateHistory.setOrderId(orderId);
            operateHistory.setOrderStatus(6);//6：关闭订单
            operateHistory.setCreateTime(new Date());
            historyList.add(operateHistory);
        }
        int count = omsOrderDao.insertOrderOpretaHistory(historyList);
        return count;
    }
}
