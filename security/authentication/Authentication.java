package newws.client_gateway.security.authentication;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

/**
 * 인증 객체 ( 부모 )
 * AbstractAuthenticationToken 객체에서 구현 -> 상속 JsonWebTokenAuthentication
 */
public interface Authentication extends Principal, Serializable {
    Collection<GrantedAuthority> getAuthorities();
    Object getDetails();
    Object getPrincipal();
    Object getCredentials();
    boolean isAuthenticated();
    void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;
}
