package newws.client_gateway.security.authentication.user_details;

import newws.client_gateway.security.authentication.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public interface CustomUserDetails extends Serializable {
    Collection<GrantedAuthority> getAuthorities();
    // 토큰 기반 인증인데 패스워드가 필요할 것인가,
    String getToken();
    String getUsername();
    boolean isAccountNonExpired();
//    boolean isAccountNonLocked();
    boolean isCredentialsNonExpired();
    boolean isEnabled();
}
