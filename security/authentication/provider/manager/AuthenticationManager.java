package newws.client_gateway.security.authentication.provider.manager;

import newws.client_gateway.security.authentication.Authentication;
import newws.client_gateway.security.authentication.client.ClientCredentials;
import newws.client_gateway.security.authentication.exceptions.AuthenticationException;

public interface AuthenticationManager {
    Authentication authenticate(Authentication authentication, ClientCredentials credentials) throws AuthenticationException;
}