package com.tst.mall.common.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @auther: maven
 * @date: 2020/2/27 15:52
 * @description: 定义系统中最基础的消息响应类
 */
@Data
public class BaseResponse implements Serializable {

    private static final long serialVersionUID = -7028191521653413376L;
    /**
     * 状态码
     */
    private Integer code = HttpStatus.OK.value();

    /**
     * 响应消息
     */
    private String message = HttpStatus.OK.getReasonPhrase();

    private String timestamps = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

    public BaseResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResponse() {

    }

}
