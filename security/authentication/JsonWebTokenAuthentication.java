package newws.client_gateway.security.authentication;

import newws.client_gateway.security.SecuritySerialVersionUID;
import newws.client_gateway.security.authentication.user_details.CustomUserDetails;

import java.util.Collection;

public class JsonWebTokenAuthentication extends AbstractAuthenticationToken{
    private static final long serialVersionUID = SecuritySerialVersionUID.SERIAL_SECURITY_CONTEXT_UID;
    private final CustomUserDetails principal;
    private Object credentials;

    public JsonWebTokenAuthentication(CustomUserDetails principal, Object credentials, Collection<GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public JsonWebTokenAuthentication(CustomUserDetails principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    @Override
    public CustomUserDetails getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    // 인증되지 않은 사용자
    public static JsonWebTokenAuthentication unauthenticated(CustomUserDetails principal, Object credentials) {
        return new JsonWebTokenAuthentication(principal, credentials);
    }
    // 인증된 사용자
    public static JsonWebTokenAuthentication authenticated(CustomUserDetails principal, Object credentials,
                                                     Collection<GrantedAuthority> authorities) {
        return new JsonWebTokenAuthentication(principal, credentials, authorities);
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        super.setAuthenticated(isAuthenticated);
    }

    // 초기화
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

}
