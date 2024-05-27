package newws.client_gateway.security.authentication.provider.publisher.event.listeners;

import newws.client_gateway.security.authentication.provider.publisher.event.AbstractAuthenticationFailureEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 실패 시 이벤트 처리
 * < @EventListener 는 초기에 적용이 안되는 상황이 발생해서 구현체로 구현 >
 */
@Component
public class AuthenticationFailureListener implements ApplicationListener<AbstractAuthenticationFailureEvent>{

    @Override
    public void onApplicationEvent(AbstractAuthenticationFailureEvent event){
        System.out.println("test 입니다.");
    };


}
