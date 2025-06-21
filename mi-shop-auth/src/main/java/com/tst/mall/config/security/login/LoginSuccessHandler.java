package com.tst.mall.config.security.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tst.mall.common.response.ApiResult;
import com.tst.mall.model.dto.UserPrincipal;
import com.tst.mall.util.JwtTokenUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component("LoginSuccessHandler")
public class LoginSuccessHandler  extends AbstractAuthenticationTargetUrlRequestHandler implements AuthenticationSuccessHandler {

    private final static String UTF8 = "utf-8";
    private final static String CONTENT_TYPE = "application/json";

    @Resource
    private ObjectMapper objectMapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setCharacterEncoding(UTF8);
        response.setContentType(CONTENT_TYPE);
        PrintWriter printWriter = response.getWriter();
        UserPrincipal userDetails =(UserPrincipal) authentication.getPrincipal();


        String token = JwtTokenUtil.generateAccessToken(userDetails);

        String refreshToken = JwtTokenUtil.generateRefreshToken(userDetails);


        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("refreshToken", refreshToken);

        printWriter.append(objectMapper.writeValueAsString(ApiResult.success(map)));
    }
}
