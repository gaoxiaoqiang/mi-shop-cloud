package com.tst.mall.common.response;

public enum ResponseCodeEnum {
    /**
     * 请求成功
     */
    SUCCESS(200, "success"),
    /**
     * 请求需要登录
     */
    NOT_LOGIN(-1,"need login"),
    /**
     * 请求失败
     */
    FAIL(-1,"fail"),
    /**
     * 请求权限不足
     */
    PERMISSION_DEFINED(2,"permission defined"),

    RIGHTFUL(501,"data not right");



    public Integer code;

    public String message;

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
