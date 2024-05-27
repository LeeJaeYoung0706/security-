package newws.client_gateway.security.authentication.provider.publisher;

import newws.client_gateway.security.authentication.Authentication;
import newws.client_gateway.security.authentication.exceptions.AuthenticationException;
import newws.client_gateway.security.authentication.exceptions.ClientCredentialsNotFoundException;
import newws.client_gateway.security.authentication.exceptions.CredentialsExpiredException;
import newws.client_gateway.security.authentication.exceptions.UsernameNotFoundException;
import newws.client_gateway.security.authentication.provider.publisher.event.AbstractAuthenticationEvent;
import newws.client_gateway.security.authentication.provider.publisher.event.AbstractAuthenticationFailureEvent;
import newws.client_gateway.security.authentication.provider.publisher.event.AuthenticationFailureBadCredentialsEvent;
import newws.client_gateway.security.authentication.provider.publisher.event.AuthenticationSuccessEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CustomAuthenticationEventPublisher implements AuthenticationEventPublisher, ApplicationEventPublisherAware {

    private final HashMap<String, Constructor<? extends AbstractAuthenticationEvent>> exceptionMappings;
    private ApplicationEventPublisher applicationEventPublisher;
    private Constructor<? extends AbstractAuthenticationFailureEvent> defaultAuthenticationFailureEventConstructor;

    public CustomAuthenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.exceptionMappings = new HashMap<>();
        this.applicationEventPublisher = applicationEventPublisher;
        this.addMapping(UsernameNotFoundException.class.getName(), AuthenticationFailureBadCredentialsEvent.class);
        this.addMapping(CredentialsExpiredException.class.getName(), AuthenticationFailureBadCredentialsEvent.class);
        this.addMapping(ClientCredentialsNotFoundException.class.getName(), AuthenticationFailureBadCredentialsEvent.class);
    }

    /**
     * 성공 시 이벤트
     * @param authentication
     */
    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        if (this.applicationEventPublisher != null) {
            this.applicationEventPublisher.publishEvent(new AuthenticationSuccessEvent(authentication));
        }
    }

    /**
     * 실패 시
     * @param exception
     * @param authentication
     */
    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        // exception Event 터트려서 인증 실패 시 -> log 쌓는 캐시맵 만들고 -> 3회 누적시 아이디 잠금 / -> Object Details 에 있는 IP 주소로
        // 주소 특정해서 대한민국이 아니라면 이메일 알람 시스템 만들기
        Constructor<? extends AbstractAuthenticationEvent> constructor = this.getEventConstructor(exception);

        AbstractAuthenticationEvent event = null;
        // null 일 경우에 새로운 생성자를 생성
        if (constructor != null) {
            try {
                event = (AbstractAuthenticationEvent) constructor.newInstance(authentication, exception);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                Throwable cause = e.getCause();
                cause.printStackTrace();
            }
        }
        // 이벤트가 널이 아닐 경우 이벤트 발급
        if (event != null) {
            if (this.applicationEventPublisher != null) {
                this.applicationEventPublisher.publishEvent(event);
            }
        }
        //else if (this.logger.isDebugEnabled()) {
          //  this.logger.debug("No event was found for the exception " + exception.getClass().getName());
        //}

    }

    /**
     * AuthenticationException 인스턴스를 매개변수로 받아 이벤트 생성자 반환
     * @param exception
     * @return
     */
    private Constructor<? extends AbstractAuthenticationEvent> getEventConstructor(AuthenticationException exception) {
        // 생성자
        Constructor<? extends AbstractAuthenticationEvent> eventConstructor = (Constructor)this.exceptionMappings.get(exception.getClass().getName());
        return eventConstructor != null ? eventConstructor : this.defaultAuthenticationFailureEventConstructor;
    }

    /**
     * Exception HashMap Put
     * @param exceptionClass
     * @param eventClass
     */
    private void addMapping(String exceptionClass, Class<? extends AbstractAuthenticationFailureEvent> eventClass) {
        try {
            Constructor<? extends AbstractAuthenticationEvent> constructor = eventClass.getConstructor(Authentication.class, AuthenticationException.class);
            this.exceptionMappings.put(exceptionClass, constructor);
        } catch (NoSuchMethodException var4) {
            throw new RuntimeException("인증 과정 실패" + eventClass.getName() + "를 확인하세요.");
        }
    }
    // 기본 설정 오토딩 package newws.client_gateway.security.authentication.config; 빈 설정시 호출 ( 커스텀 익셉션 추가 )
    public void setAdditionalExceptionMappings(Map<Class<? extends AuthenticationException>, Class<? extends AbstractAuthenticationFailureEvent>> mappings) {
        Assert.notEmpty(mappings, "The mappings Map must not be empty nor null");
        Iterator var2 = mappings.entrySet().iterator();

        while(var2.hasNext()) {
            Map.Entry<Class<? extends AuthenticationException>, Class<? extends AbstractAuthenticationFailureEvent>> entry = (Map.Entry)var2.next();
            Class<?> exceptionClass = (Class)entry.getKey();
            Class<?> eventClass = (Class)entry.getValue();
            Assert.notNull(exceptionClass, "예외 처리 클래스는 필수 요소 입니다.");
            Assert.notNull(eventClass, "이벤트 클래스는 필수 요소 입니다.");
            this.addMapping(exceptionClass.getName(), (Class<? extends AbstractAuthenticationFailureEvent>) eventClass);
        }

    }
    // ApplicationEventPublisherAware
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void setDefaultAuthenticationFailureEvent(Class<? extends AbstractAuthenticationFailureEvent> defaultAuthenticationFailureEventClass) {
        Assert.notNull(defaultAuthenticationFailureEventClass, "기본 실패 이벤트 매개 변수는 필수 요소 입니다. ");

        try {
            this.defaultAuthenticationFailureEventConstructor = defaultAuthenticationFailureEventClass.getConstructor(Authentication.class, AuthenticationException.class);
        } catch (NoSuchMethodException var3) {
            throw new RuntimeException("기본으로 실행되는 실패 이벤트 클래스 " + defaultAuthenticationFailureEventClass.getName() + " 에 문제가 있습니다.");
        }
    }
}
