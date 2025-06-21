package com.tst.mall.common.handler;

import com.tst.mall.common.exception.BaseException;
import com.tst.mall.common.response.ApiResult;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.NoResourceFoundException;


/**
 * @description: 定义系统所有web请求异常全局处理类
 */

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Object handleBaseException(HttpServletRequest req, BaseException e){
        log.error("业务异常：{}",e.getMessage(),e);
        return  ResponseEntity
                .status(200)
                .body(ApiResult.fail(e,e.getCode()));
    }


    // 处理静态资源404  ResponseEntity :终结响应体避免死循环
    @ExceptionHandler(NoResourceFoundException.class)
    public Object handleNoResourceFound(NoResourceFoundException e) {
       return  ResponseEntity
                .status(200)
                .body(ApiResult.fail(e,HttpStatus.NOT_FOUND.value()));
    }




    // 也可以统一处理所有 FeignException
    @ExceptionHandler(FeignException.class)
    public Object handleFeignException(FeignException e) {

        return ResponseEntity
                .status(200)
                .body(ApiResult.fail( e, e.status()));
    }



    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest req, Exception e){
        log.error("服务器异常:{}",e.getMessage(),e);
        return  ResponseEntity
                .status(200)
                .body(ApiResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
    }
}


