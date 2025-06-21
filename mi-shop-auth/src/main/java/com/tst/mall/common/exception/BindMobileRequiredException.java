package com.tst.mall.common.exception;

import com.tst.mall.config.security.login.oauth2.OAuth2UserInfo;
import lombok.Getter;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

@Getter
public class BindMobileRequiredException extends OAuth2AuthenticationException {

    private final OAuth2UserInfo userInfo;

    public BindMobileRequiredException(OAuth2UserInfo userInfo) {
        super("New user requires mobile phone binding");
        this.userInfo = userInfo;
    }

}