package newws.client_gateway.security.authentication.context;

import newws.client_gateway.security.authentication.Authentication;

import java.io.Serializable;

public interface CustomSecurityContext extends Serializable {

    boolean isAuthenticated();

    void setAuthentication(Authentication authentication);

    Authentication getAuthentication();
}
