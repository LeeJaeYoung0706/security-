package newws.client_gateway.security.authentication.provider.messages;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class CustomSecurityMessageSource extends ResourceBundleMessageSource {

    public CustomSecurityMessageSource() {
        this.setBasename("messages/messages_ko");
        this.setDefaultEncoding("UTF-8");
    }

    public static MessageSourceAccessor getAccessor() {
        return new MessageSourceAccessor(new CustomSecurityMessageSource());
    }
}