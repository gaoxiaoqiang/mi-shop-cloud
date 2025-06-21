package com.tst.mall.config.security.login.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tst.mall.common.exception.BindMobileRequiredException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component("OAuth2FailureHandler")
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${frontend.url:http://localhost:5173}")
    private String frontendUrl;

    @Value("${frontend.login-path:/login}")
    private String loginPath;

    @Value("${frontend.bind-mobile-path:/bind-mobile}")
    private String bindMobilePath;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        if (exception instanceof BindMobileRequiredException) {
            // 处理需要绑定手机号的情况
            BindMobileRequiredException bindEx = (BindMobileRequiredException) exception;
            OAuth2UserInfo userInfo = bindEx.getUserInfo();

            // 构建重定向URL，携带用户信息
            String redirectUrl = UriComponentsBuilder.fromUriString(frontendUrl)
                    .path(bindMobilePath)
                    .queryParam("openid", userInfo.getId())
                    .queryParam("nickname", URLEncoder.encode(userInfo.getName(), StandardCharsets.UTF_8))
                    .queryParam("avatar", URLEncoder.encode(userInfo.getImageUrl(), StandardCharsets.UTF_8))
                    .build()
                    .toUriString();

            response.sendRedirect(redirectUrl);
        } else {
            // 其他错误情况，重定向到登录页面并携带错误信息
            String errorMessage = URLEncoder.encode(exception.getMessage(), StandardCharsets.UTF_8);

            String redirectUrl = UriComponentsBuilder.fromUriString(frontendUrl)
                    .path(loginPath)
                    .queryParam("error", errorMessage)
                    .build()
                    .toUriString();

            response.sendRedirect(redirectUrl);
        }
    }
}