

package com.tst.mall.config.security;

import com.tst.mall.config.security.login.oauth2.CustomOAuth2UserService;
import com.tst.mall.config.security.login.oauth2.OAuth2FailureHandler;
import com.tst.mall.config.security.login.oauth2.OAuth2SuccessHandler;
import com.tst.mall.config.security.login.phone.SmsLoginFilter;
import com.tst.mall.config.security.login.phone.SmsLoginProvider;
import com.tst.mall.config.security.login.username.UserNameLoginFilter;
import com.tst.mall.config.security.login.username.UserNameLoginProvider;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig   {

    @Resource
    ApplicationContext applicationContext;

    @Qualifier("LoginSuccessHandler")
    @Resource
    AuthenticationSuccessHandler successHandler;

    @Qualifier("LoginFailureHandler")
    @Resource
    AuthenticationFailureHandler failureHandler;

    @Resource
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Qualifier("OAuth2SuccessHandler")
    @Resource
    private OAuth2SuccessHandler oauth2SuccessHandler;

    @Qualifier("OAuth2FailureHandler")
    @Resource
    private  OAuth2FailureHandler oauth2FailureHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 默认加密方式
    }


    private void  commonSettingFilter(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        commonSettingFilter(http);
        // 授权配置
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                        "/auth/**",
                        "/oauth2/authorization/**",    // 放行OAuth2初始化端点
                        "/login/oauth2/code/**"       // 放行OAuth2回调端点
                ).permitAll().anyRequest().authenticated()
        );

        http.oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(auth -> auth
                        .baseUri("/oauth2/authorization")
                )
                .redirectionEndpoint(redirectionEndpointConfig -> redirectionEndpointConfig
                        .baseUri("/login/oauth2/code/*")
                )
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(getOauth2UserService())
                )
                .successHandler(oauth2SuccessHandler)
                .failureHandler(oauth2FailureHandler)
        );
        http.addFilterBefore(
                new UserNameLoginFilter(
                        new ProviderManager(getAuthenticationProvider(UserNameLoginProvider.class)),
                        successHandler,
                        failureHandler
                ),
                UsernamePasswordAuthenticationFilter.class
        );
        http.addFilterBefore(
                new SmsLoginFilter(
                        new ProviderManager(getAuthenticationProvider(SmsLoginProvider.class)),
                        successHandler,
                        failureHandler
                ),
                UsernamePasswordAuthenticationFilter.class
        );
          http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }


    private <T extends AuthenticationProvider> T getAuthenticationProvider(Class<T> providerClass) {
        return applicationContext.getBean(providerClass);
    }

    private CustomOAuth2UserService  getOauth2UserService() {
        return  applicationContext.getBean(CustomOAuth2UserService.class);
    }


}

