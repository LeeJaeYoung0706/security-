package newws.client_gateway.security.authentication.config;

import newws.client_gateway.security.authentication.provider.publisher.AuthenticationEventPublisher;
import newws.client_gateway.security.authentication.provider.publisher.CustomAuthenticationEventPublisher;
import newws.client_gateway.security.authentication.provider.publisher.event.DefaultFailedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {


    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        // 빈 설정시 추가 해준다면,
        //Map<Class<? extends AuthenticationException>, Class<? extends AbstractAuthenticationFailureEvent>> mapping = Collections.singletonMap(FooException.class, FooEvent.class);
        CustomAuthenticationEventPublisher authenticationEventPublisher = new CustomAuthenticationEventPublisher(applicationEventPublisher);
        // authenticationEventPublisher.setAdditionalExceptionMappings(mapping);
        // 기본 실패 이벤트 실행 설정
        authenticationEventPublisher.setDefaultAuthenticationFailureEvent(DefaultFailedEvent.class);
        return authenticationEventPublisher;
    }


}
