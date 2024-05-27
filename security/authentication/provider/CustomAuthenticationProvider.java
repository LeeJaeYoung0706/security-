package newws.client_gateway.security.authentication.provider;

import newws.client_gateway.security.authentication.Authentication;
import newws.client_gateway.security.authentication.exceptions.AuthenticationException;




public interface CustomAuthenticationProvider {

    Authentication authenticate(Authentication authentication) throws AuthenticationException;

    boolean supports(Class<?> authentication);

}
