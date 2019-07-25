package com.ym.mall.common.api;

/**
 * @author taozai
 * @date 2019/7/25  23:55
 */
public enum ResultCode implements ErrorCode{

    SUCCESS("200","操作成功"),
    FAIL("500","操作失败");



    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
