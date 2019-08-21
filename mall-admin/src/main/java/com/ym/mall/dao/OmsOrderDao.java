package com.ym.mall.dao;

import com.ym.mall.dto.OmsOrderQueryParams;
import com.ym.mall.model.OmsOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}
