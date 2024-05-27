package newws.client_gateway.security.authentication.exceptions;

/**
 * 사용자 정보 없을 때 Exception
 */
public class UsernameNotFoundException extends AuthenticationException{
    public UsernameNotFoundException(String msg) {
        super(msg);
    }

    public UsernameNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
