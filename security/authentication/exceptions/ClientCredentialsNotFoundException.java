package newws.client_gateway.security.authentication.exceptions;

/**
 * 클라이언트 인증 정보가 없을 때
 */
public class ClientCredentialsNotFoundException extends AuthenticationException{
    public ClientCredentialsNotFoundException(String msg) {
        super(msg);
    }

    public ClientCredentialsNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
