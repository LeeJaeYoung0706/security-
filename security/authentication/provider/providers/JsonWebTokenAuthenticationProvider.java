package newws.client_gateway.security.authentication.provider.providers;

import newws.client_gateway.security.authentication.AbstractAuthenticationToken;
import newws.client_gateway.security.authentication.Authentication;
import newws.client_gateway.security.authentication.JsonWebTokenAuthentication;
import newws.client_gateway.security.authentication.client.ClientCredentials;
import newws.client_gateway.security.authentication.exceptions.AuthenticationException;
import newws.client_gateway.security.authentication.user_details.CustomUserDetails;
import newws.client_gateway.security.authentication.user_details.CustomUserDetailsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import static newws.client_gateway.security.authentication.provider.messages.ExceptionsMessageUtils.AUTHENTICATION_INSTANCE_EXCEPTION;
import static newws.client_gateway.security.authentication.provider.messages.ExceptionsMessageUtils.CLIENT_CREDENTIALS_NOTFOUND_EXCEPTION;
import static newws.client_gateway.security.authentication.provider.messages.ExceptionsMessageUtils.SERVICE_INJECTION_EXCEPTION;

/**
 * 인증객체 제공자
 */
@Component
public class JsonWebTokenAuthenticationProvider implements AuthenticationProvider, InitializingBean {

    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public void setCustomUserDetailsService(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication, ClientCredentials credentials) throws AuthenticationException {
        Assert.isInstanceOf(AbstractAuthenticationToken.class, authentication, () -> AUTHENTICATION_INSTANCE_EXCEPTION);
        String username = this.determineUsername(authentication);
        CustomUserDetails userDetailsByUsername = customUserDetailsService.getUserDetailsByUsername(username);
        return this.createJwtAuthentication(userDetailsByUsername, credentials);
    }
    // username 셋팅
    private String determineUsername(Authentication authentication) {
        return authentication.getPrincipal() == null ? "NONE_PROVIDED" : authentication.getName();
    }
    // jsonwebtoken 객체 변환
    private JsonWebTokenAuthentication createJwtAuthentication(CustomUserDetails customUserDetails, ClientCredentials credentials) {
        Assert.isInstanceOf(ClientCredentials.class, credentials, () -> CLIENT_CREDENTIALS_NOTFOUND_EXCEPTION);
        return new JsonWebTokenAuthentication(customUserDetails, credentials, customUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JsonWebTokenAuthentication.class.isAssignableFrom(authentication));
    }

    @Override
    public void afterPropertiesSet() {
        // advice에 안잡히도록,
        Assert.notNull(this.customUserDetailsService , SERVICE_INJECTION_EXCEPTION );
    }
}
