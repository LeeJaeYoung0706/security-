package newws.client_gateway.security.authentication.provider.publisher;

import newws.client_gateway.security.authentication.Authentication;
import newws.client_gateway.security.authentication.exceptions.AuthenticationException;


public interface AuthenticationEventPublisher {
    // 성공 시 설정
    void publishAuthenticationSuccess(Authentication authentication);
    // 실패 시 설정
    void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication);
}
