package com.tst.mall.config.security.login.username;

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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserNameLoginProvider implements AuthenticationProvider {

    protected final Log logger = LogFactory.getLog(getClass());

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Resource
    private UserFeignService userFeignService;


    @Resource
    private PasswordEncoder passwordEncoder;


    private String determineUsername(Authentication authentication) {

        this.logger.info("authentication provided name: " + authentication.getName());
        if( authentication instanceof UserNameLoginAuthentication){
            UserNameLoginAuthentication userNameLoginAuthentication = (UserNameLoginAuthentication) authentication;
            return  userNameLoginAuthentication.getUsername();
        }
        return "NONE_PROVIDED";
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = determineUsername(authentication);
        ApiResult<UserDto> userDtoApiResult= userFeignService.loadUserByUsername(username);
        UserDto user = userDtoApiResult.getData();
        if (user == null) {
            throw new UsernameNotFoundException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
            additionalAuthenticationChecks(user, (UserNameLoginAuthentication) authentication);

        UserPrincipal userPrincipal = UserPrincipal.create(user);
        authentication.setAuthenticated(true);
        ((UserNameLoginAuthentication) authentication).setUserDetails(userPrincipal);
        return authentication;
    }

    protected void additionalAuthenticationChecks(UserDto userDto,
                                                 UserNameLoginAuthentication authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("UserNameLoginAuthentication.badCredentials", "Bad credentials"));
        }
        String presentedPassword = authentication.getCredentials().toString();
        if (!this.passwordEncoder.matches(presentedPassword, userDto.getUserPassword())) {
            this.logger.debug("Failed to authenticate since password does not match stored value");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }



    @Override
    public boolean supports(Class<?> authentication) {
        return (com.tst.mall.config.security.login.username.UserNameLoginAuthentication.class.isAssignableFrom(authentication));
    }
}
