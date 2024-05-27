package newws.client_gateway.security.authentication.provider.publisher.event;

import newws.client_gateway.security.authentication.Authentication;
import org.springframework.context.ApplicationEvent;
// ApplicationEvent -> 애플리케이션의 특정 상태 변화를 나타내는 이벤트 정의 -> 브로드 캐스팅 같은 역할 인가 ?
// 상태 변화 시 알림 역할 이벤트를 만드는 데이터를 가져야 함. CustomAbstractAuthenticationFailureEvent
public abstract class AbstractAuthenticationEvent extends ApplicationEvent {
    // 인증 객체를 받아서 -> ApplicationEvent 의 생성자로 전달 -> 인벤트의 소스로 인증 객체 설정
    public AbstractAuthenticationEvent(Authentication authentication) {
        super(authentication);
    }

    public Authentication getAuthentication() {
        return (Authentication)super.getSource();
    }
}
