package com.tst.mall.config.security.login.phone;

import com.tst.mall.common.response.ApiResult;
import com.tst.mall.model.dto.UserDto;
import com.tst.mall.model.dto.UserPrincipal;
import com.tst.mall.service.UserFeignService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SmsLoginProvider implements AuthenticationProvider {

    protected final Log logger = LogFactory.getLog(getClass());

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Resource
    private UserFeignService userFeignService;




    private String determineUsername(Authentication authentication) {

        this.logger.info("authentication provided name: " + authentication.getName());
        if( authentication instanceof SmsLoginAuthentication){
            SmsLoginAuthentication smsLoginAuthentication = (SmsLoginAuthentication) authentication;
            return  smsLoginAuthentication.getPhone();
        }
        return "NONE_PROVIDED";
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String phone = determineUsername(authentication);
        ApiResult<UserDto> userDtoApiResult= userFeignService.loadUserByUserPhone(phone);
        UserDto user = userDtoApiResult.getData();
        if (user == null) {
            throw new UsernameNotFoundException(
                    "User not found with phone: " + phone);
        }
        UserPrincipal userPrincipal = UserPrincipal.create(user);

        additionalAuthenticationChecks(user, (SmsLoginAuthentication) authentication);
        authentication.setAuthenticated(true);
        ((SmsLoginAuthentication) authentication).setUserDetails(userPrincipal);
        return authentication;
    }

    protected void additionalAuthenticationChecks(UserDto userDto,SmsLoginAuthentication authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Failed to authenticate since no sms code provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("UserNameLoginAuthentication.badCredentials", "Bad credentials"));
        }
        String smsCode = authentication.getCredentials().toString();

        //compare with redisUtil_code

    }



    @Override
    public boolean supports(Class<?> authentication) {
        return (SmsLoginAuthentication.class.isAssignableFrom(authentication));
    }
}
