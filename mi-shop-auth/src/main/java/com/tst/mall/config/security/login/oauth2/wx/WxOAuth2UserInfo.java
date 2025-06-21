package com.tst.mall.config.security.login.oauth2.wx;

import com.tst.mall.config.security.login.oauth2.OAuth2UserInfo;

import java.util.Map;

public class WxOAuth2UserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    public WxOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getId() {
        return (String) attributes.get("openid");
    }

    @Override
    public String getName() {
        return (String) attributes.get("nickname");
    }

    @Override
    public String getEmail() {
        return getId() + "@weixin.com"; // 微信没有email，用openid模拟
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("headimgurl");
    }
}
