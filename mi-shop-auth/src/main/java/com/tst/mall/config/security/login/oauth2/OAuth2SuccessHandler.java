package com.tst.mall.config.security.login.oauth2;

import com.tst.mall.model.dto.UserPrincipal;
import com.tst.mall.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component("OAuth2SuccessHandler")
@Slf4j
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${frontend.url:http://localhost:5173}")
    private String frontendUrl;

    @Value("${frontend.auth-callback-path:/auth-callback}")
    private String authCallbackPath;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String token = JwtTokenUtil.generateAccessToken(userPrincipal);

        // 使用 UriComponentsBuilder 安全构建 URL
        String redirectUrl = UriComponentsBuilder.fromUriString(frontendUrl)
                .path(authCallbackPath)
                .queryParam("token", token)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        log.info("OAuth2 login success, redirecting to: {}", redirectUrl);
            response.sendRedirect(redirectUrl);
        }
}