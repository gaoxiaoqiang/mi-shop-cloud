package com.tst.mall.config.security.login.phone;


import com.tst.mall.model.dto.UserPrincipal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.SpringSecurityCoreVersion;


@Setter
@Getter
public class SmsLoginAuthentication extends AbstractAuthenticationToken   {


    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private  String phone;

    private String smsCode;

    private UserPrincipal userDetails;


    public SmsLoginAuthentication(String phone, String smsCode ) {
        super(null);
        this.phone = phone;
        this.smsCode = smsCode;
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return  isAuthenticated()?null:smsCode;
    }

    @Override
    public Object getPrincipal() {
        return isAuthenticated()?userDetails:null;
    }




}
