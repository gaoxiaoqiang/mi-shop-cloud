package com.tst.mall.config.security.login.oauth2;

public interface OAuth2UserInfo {
    String getId();
    String getName();
    String getEmail();
    String getImageUrl();
}
