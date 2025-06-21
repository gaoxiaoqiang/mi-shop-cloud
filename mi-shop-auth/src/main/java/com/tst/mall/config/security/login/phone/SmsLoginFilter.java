package com.tst.mall.config.security.login.phone;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SmsLoginFilter extends  AbstractAuthenticationProcessingFilter {


    public static final String SPRING_SECURITY_FORM_PHONE_KEY = "phone";

    public static final String SPRING_SECURITY_FORM_SMS_CODE_KEY = "smsCode";

    private static final RequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = PathPatternRequestMatcher.withDefaults()
            .matcher(HttpMethod.POST, "/sms/login");


    public SmsLoginFilter(AuthenticationManager authenticationManager, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
        setAuthenticationSuccessHandler(successHandler);
        setAuthenticationFailureHandler(failureHandler);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String phone =this.obtainUsername(request);
        phone = (phone != null) ? phone.trim() : "";
        String smsCode = this.obtainPassword(request);
        smsCode = (smsCode != null) ? smsCode : "";
        SmsLoginAuthentication smsLoginAuthentication =new SmsLoginAuthentication(phone,smsCode);
        return this.getAuthenticationManager().authenticate(smsLoginAuthentication);
    }



    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_PHONE_KEY);
    }


    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_SMS_CODE_KEY);
    }



}
