package com.tst.mall.config.security.login.oauth2;


import com.tst.mall.common.constant.Oauth2Type;
import com.tst.mall.config.security.login.oauth2.gitee.GiteeOAuth2UserInfo;
import com.tst.mall.config.security.login.oauth2.github.GithubOAuth2UserInfo;
import com.tst.mall.config.security.login.oauth2.wx.WxOAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(Oauth2Type.GIT_HUB.getRegistrationId())) {
            return new GithubOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(Oauth2Type.WX.getRegistrationId())) {
            return new WxOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase(Oauth2Type.GIT_EE.getRegistrationId())) {
            return new GiteeOAuth2UserInfo(attributes);
        } else {
            throw new IllegalArgumentException("Unsupported login provider: " + registrationId);
        }
    }
}