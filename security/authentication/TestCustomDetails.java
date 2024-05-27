package newws.client_gateway.security.authentication;

import newws.client_gateway.security.authentication.user_details.CustomUserDetails;

import java.util.Collection;

public class TestCustomDetails implements CustomUserDetails {
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getToken() {
        return null;
    }

    @Override
    public String getUsername() {
        return "111111";
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
