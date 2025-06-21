package com.tst.mall.common.response;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = -7028191521653413376L;

    /**
     * 数据封装载体
     */
    private T data;

    /**
     * 状态码
     */
    private Integer code = 200;

    /**
     * 响应消息
     */
    private String message = "OK";

    /**
     * 响应时间戳
     */
    private String timestamps = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    public ApiResult() {
    }

    public ApiResult(T data) {
        this.data = data;
    }

    public ApiResult(T data, Integer code) {
        this.data = data;
        this.code = code;
    }

    public ApiResult(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public ApiResult(Throwable throwable) {
        this.message = throwable.getMessage();
        this.code = 500;
    }

    public ApiResult(Throwable throwable, Integer code) {
        this.message = throwable.getMessage();
        this.code = code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamps(String timestamps) {
        this.timestamps = timestamps;
    }

    public static <T> ApiResult<T> success(T data) {
        return new ApiResult<>(data);
    }

    public static <T> ApiResult<T> success() {
        return new ApiResult<>();
    }

    public static <T> ApiResult<T> fail(Throwable throwable, int code) {
        return new ApiResult<>(throwable, code);
    }

    public static <T> ApiResult<T> fail(Throwable throwable) {
        return new ApiResult<>(throwable);
    }

    public static <T> ApiResult<T> fail(String message) {
        return new ApiResult<>(message, 500);
    }

    public static <T> ApiResult<T> info(T data, int code) {
        return new ApiResult<>(data, code);
    }
}
