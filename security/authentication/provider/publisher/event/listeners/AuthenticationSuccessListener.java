package newws.client_gateway.security.authentication.provider.publisher.event.listeners;

import newws.client_gateway.security.authentication.provider.publisher.event.AuthenticationSuccessEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 성공시 이벤트 처리
 * < @EventListener 는 초기에 적용이 안되는 상황이 발생해서 구현체로 구현 >
 */
@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event){
        System.out.println("test 임니다.");
    };
}
