package com.ym.mall.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录参数实体
 * @author matao
 * @create 2019-07-26 10:25
 */
@Data
@ToString
@ApiModel(value = "umsAdminLoginParams",description = "用户登录参数实体")
public class UmsAdminLoginParams {

    @ApiModelProperty(value = "用户名",required = true)
    @NotEmpty(message = "用户名不能为空")
    private String userName;
    @ApiModelProperty(value = "用户密码",required = true)
    @NotEmpty(message = "用户密码不能为空")
    private String password;
}
