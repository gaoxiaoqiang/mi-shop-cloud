package com.tst.mall.common.exception;

import org.springframework.http.HttpStatus;

/**
 * @description: 定义系统中最基础的异常类
 */
public class BaseException  extends  RuntimeException{
    private static final long serialVersionUID = 1071104922857848303L;
    /**
     * 具体异常码
     */
    protected int code = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public BaseException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
    }

    public BaseException(int code, Throwable cause) {
        super(cause);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
