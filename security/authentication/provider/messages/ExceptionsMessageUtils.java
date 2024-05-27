package newws.client_gateway.security.authentication.provider.messages;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ExceptionsMessageUtils implements InitializingBean {

    protected static MessageSourceAccessor messages = CustomSecurityMessageSource.getAccessor();

    public static String USERNAME_NOTFOUND_EXCEPTION = messages.getMessage("authentication.failed.exception.username");
    public static String CLIENT_CREDENTIALS_NOTFOUND_EXCEPTION =  messages.getMessage("authentication.failed.exception.client_credentials");
    public static String AUTHENTICATION_INSTANCE_EXCEPTION =  messages.getMessage("authentication.failed.exception.authentication_instance");
    public static String SERVICE_INJECTION_EXCEPTION = messages.getMessage("authentication.failed.exception.service_injection");
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messages, "메세지 소스가 존재하지 않습니다. 확인 해주세요.");
    }
}
