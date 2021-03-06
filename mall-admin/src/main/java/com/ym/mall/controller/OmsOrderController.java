package com.ym.mall.controller;

import com.ym.mall.common.api.CommonPage;
import com.ym.mall.common.api.ResponseResult;
import com.ym.mall.dto.OmsOrderQueryParams;
import com.ym.mall.model.OmsOrder;
import com.ym.mall.service.OmsOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单管理的Controller
 * @author matao
 * @create 2019-08-21 15:01
 */
@Api(tags = "OmsOrderController",description = "订单管理")
@RestController
@RequestMapping(value = "/order")
@Slf4j
public class OmsOrderController {

    @Autowired
    private OmsOrderService omsOrderService;

    @ApiOperation(value = "查询订单")
    @GetMapping(value = "/list")
    public ResponseResult getOrderList(OmsOrderQueryParams queryParams,
                                       @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                       @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize){
        List<OmsOrder> orderList = omsOrderService.getOrderList(queryParams, pageNum, pageSize);
        return ResponseResult.success(CommonPage.restPage(orderList));

    }

    @ApiOperation(value = "根据Id批量关闭订单")
    @PostMapping(value = "/update/close")
    public ResponseResult batchCloseOrderById(@RequestParam("ids") List<Long> ids,@RequestParam("note") String note){
        int count = omsOrderService.batchCloseOrderById(ids,note);
        if(count > 0){
            return ResponseResult.success(count);
        }
        return ResponseResult.fail("根据Id批量关闭订单失败");
    }

    @ApiOperation(value = "根据Id批量删除订单")
    @PostMapping(value = "/delete")
    public ResponseResult batchDeleteOrderById(@RequestParam()List<Long> ids){
        int count = omsOrderService.batchDeleteOrderById(ids);if(count > 0){
            return ResponseResult.success(count);
        }
        return ResponseResult.fail("根据Id批量删除订单失败");
    }

//    @ApiOperation(value = "批量发货")
//    @PostMapping(value = "/update/delivery")
//    public ResponseResult batchDeliveryOrder(){
//
//    }
}
