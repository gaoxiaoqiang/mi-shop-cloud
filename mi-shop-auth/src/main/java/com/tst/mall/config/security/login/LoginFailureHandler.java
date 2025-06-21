package com.tst.mall.config.security.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tst.mall.common.response.ApiResult;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;


@Slf4j
@Component("LoginFailureHandler")
public class LoginFailureHandler implements AuthenticationFailureHandler {

    private final static String UTF8 = "utf-8";
    private final static String CONTENT_TYPE = "application/json";

    @Resource
    private  ObjectMapper objectMapper;

    public LoginFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        log.info("登录失败 原因: {}", exception.getMessage());
        response.setCharacterEncoding(UTF8);
        response.setContentType(CONTENT_TYPE);
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString( ApiResult.fail( exception.getMessage())));

    }
}
