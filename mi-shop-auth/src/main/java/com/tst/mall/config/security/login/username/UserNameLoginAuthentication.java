package com.tst.mall.config.security.login.username;


import com.tst.mall.model.dto.UserPrincipal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.SpringSecurityCoreVersion;


@Setter
@Getter
public class UserNameLoginAuthentication  extends AbstractAuthenticationToken   {


    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private  String username;

    private String password;

    private UserPrincipal userDetails;


    public UserNameLoginAuthentication() {
        super(null);
    }

    @Override
    public Object getCredentials() {
        return  isAuthenticated()?null:password;
    }

    @Override
    public Object getPrincipal() {
        return isAuthenticated()?userDetails:null;
    }




}
