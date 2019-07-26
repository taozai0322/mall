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
    public static <T> ResponseResult<T> success(T data){
        return new ResponseResult(ResultCode.SUCCESS.getCode(),ResultCode.SUCCESS.getMsg(),data);
    }

    /**
     * 成功返回有数据
     * @param data   获取的数据
     * @param msg    提示信息
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> success(T data,String msg){
        return new ResponseResult(ResultCode.SUCCESS.getCode(),msg,data);
    }

    /**
     * 失败返回结果
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> fail(){
        return new ResponseResult(ResultCode.FAIL.getCode(),ResultCode.FAIL.getMsg(),null);
    }

    /**
     * 失败返回结果
     * @param msg   提示错误信息
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> fail(String msg){
        return new ResponseResult(ResultCode.FAIL.getCode(),msg,null);
    }

    /**
     * 失败返回结果
     * @param code  提示错误码
     * @param msg   错误提示信息
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> fail(String code,String msg){
        return new ResponseResult(code,msg,null);
    }
}
