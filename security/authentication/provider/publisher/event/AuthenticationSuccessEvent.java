package newws.client_gateway.security.authentication.provider.publisher.event;

import newws.client_gateway.security.authentication.Authentication;

public class AuthenticationSuccessEvent extends AbstractAuthenticationEvent {
    public AuthenticationSuccessEvent(Authentication authentication) {
        super(authentication);
    }
}
