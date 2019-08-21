package com.ym.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 订单查询参数
 * @author matao
 * @create 2019-08-21 14:42
 */
@Data
public class OmsOrderQueryParams {

    @ApiModelProperty(value = "订单编号")
    private String orderSn;
    @ApiModelProperty(value = "收货人的姓名/号码")
    private String receiverKeyword;
    @ApiModelProperty(value = "订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    private Integer status;
    @ApiModelProperty(value = "订单类型：0->正常订单；1->秒杀订单")
    private Integer orderType;
    @ApiModelProperty(value = "订单来源：0->PC订单；1->app订单")
    private Integer sourceType;
    @ApiModelProperty(value = "订单提交时间")
    private String createTime;
}
