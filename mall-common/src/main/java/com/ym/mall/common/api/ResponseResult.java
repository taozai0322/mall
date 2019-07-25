package com.ym.mall.common.api;

import lombok.Data;
import lombok.ToString;

/**
 * 通用返回对象
 * @author matao
 * @create 2019-07-25 17:47
 */
@Data
@ToString
public class ResponseResult<T> {
    /**
     * 返回码
     */
    private String code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    public ResponseResult() {
    }

    public ResponseResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功返回有数据
     * @param data
     * @return
     */
    public ResponseResult<T> success(T data){
        return new ResponseResult(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMsg(),data);
    }
}
