package newws.client_gateway.security.authentication.exceptions;

public class CredentialsExpiredException extends AuthenticationException{
    public CredentialsExpiredException(String message) {
        super(message);
    }

    public CredentialsExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}