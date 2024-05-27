package newws.client_gateway.security.authentication.provider.publisher.event;

import newws.client_gateway.security.authentication.Authentication;
import newws.client_gateway.security.authentication.exceptions.AuthenticationException;
import org.springframework.util.Assert;

public abstract class AbstractAuthenticationFailureEvent extends AbstractAuthenticationEvent {

    private final AuthenticationException exception;

    public AbstractAuthenticationFailureEvent(Authentication authentication, AuthenticationException exception) {
        super(authentication);
        Assert.notNull(exception, "AuthenticationException is required");
        this.exception = exception;
    }
    public AuthenticationException getException() {
        return this.exception;
    }
}
