package com.ym.mall.dao;

import com.ym.mall.dto.OmsOrderQueryParams;
import com.ym.mall.model.OmsOrder;
import com.ym.mall.model.OmsOrderOperateHistory;
import netscape.javascript.JSObject;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 订单自定义的查询Dao
 * @author matao
 * @create 2019-08-21 15:13
 */
public interface OmsOrderDao {

    /**
     * 条件查询订单
     * @param queryParams
     * @return
     */
    List<OmsOrder> getOrderList(@Param("queryParams") OmsOrderQueryParams queryParams);


    /**
     * 批量关闭订单：订单状态为：0：未付款时，才属于关闭
     * @param ids
     * @return
     */
    int batchCloseOrder( List<JSObject> list);

    /**
     * 批量插入订单的操作历史记录
     * @param operateHistoryList
     * @return
     */
    int insertOrderOpretaHistory(@Param("list") List<OmsOrderOperateHistory> operateHistoryList);
}
