package com.tst.mall.config.security.login.oauth2.gitee;

import com.tst.mall.config.security.login.oauth2.OAuth2UserInfo;

import java.util.Map;

public class GiteeOAuth2UserInfo implements OAuth2UserInfo {
    private Map<String, Object> attributes;

    public GiteeOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("avatar_url");
    }
}
