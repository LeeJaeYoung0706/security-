package newws.client_gateway.security.authentication.provider.publisher.event;

import newws.client_gateway.security.authentication.Authentication;
import newws.client_gateway.security.authentication.exceptions.AuthenticationException;

/**
 * 인증 관련 실패 이벤트
 */
public class AuthenticationFailureBadCredentialsEvent extends AbstractAuthenticationFailureEvent {
    public AuthenticationFailureBadCredentialsEvent(Authentication authentication, AuthenticationException exception) {
        super(authentication, exception);
    }
}