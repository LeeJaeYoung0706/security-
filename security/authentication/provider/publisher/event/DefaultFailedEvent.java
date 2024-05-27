package newws.client_gateway.security.authentication.provider.publisher.event;

import newws.client_gateway.security.authentication.Authentication;
import newws.client_gateway.security.authentication.exceptions.AuthenticationException;

public class DefaultFailedEvent extends AbstractAuthenticationFailureEvent {

    public DefaultFailedEvent(Authentication authentication, AuthenticationException exception) {
        super(authentication, exception);
    }
}
