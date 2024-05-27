package newws.client_gateway.security.authentication.provider.providers;

import newws.client_gateway.security.authentication.Authentication;
import newws.client_gateway.security.authentication.client.ClientCredentials;
import newws.client_gateway.security.authentication.exceptions.AuthenticationException;

public interface AuthenticationProvider {
    Authentication authenticate(Authentication authentication, ClientCredentials credentials) throws AuthenticationException;
    boolean supports(Class<?> authentication);
}
