package com.tst.mall.config.security.login.oauth2;

import com.tst.mall.common.constant.Oauth2Type;
import com.tst.mall.common.exception.BindMobileRequiredException;
import com.tst.mall.common.response.ApiResult;
import com.tst.mall.model.dto.UserDto;
import com.tst.mall.model.dto.UserPrincipal;
import com.tst.mall.service.UserFeignService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Resource
    UserFeignService userFeignService;

    @Resource PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. 获取默认的OAuth2用户信息
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 2. 获取第三方登录提供者信息
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 3. 构建统一的用户信息
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                registrationId,
                oAuth2User.getAttributes()
        );
        // 4. 查询或创建用户

        Oauth2Type oauth2Type = Oauth2Type.fromRegistrationId(registrationId);
        String columnName = oauth2Type.getColumnName();
        ApiResult<UserDto> userDtoApiResult = userFeignService.loadUserByOpenId(userInfo.getId(),columnName);

        if (ObjectUtils.isEmpty(userDtoApiResult) || userDtoApiResult.getData() == null) {
            // 新用户，需要绑定手机号
            throw new BindMobileRequiredException(userInfo);
        }
        // 5. 返回自定义UserPrincipal对象
        return UserPrincipal.create(userDtoApiResult.getData(), oAuth2User.getAttributes());
    }


}
