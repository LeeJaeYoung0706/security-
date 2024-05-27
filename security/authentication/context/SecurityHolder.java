package newws.client_gateway.security.authentication.context;

import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.util.function.Supplier;

@Component
public class SecurityHolder {

    private static SecurityContextHolderStrategy customHolderStrategy;
    // 초기화 횟수 test 용
    private static int initializeCount = 0;

    // 정적 초기화 블럭 > 클래스가 생성될 때 실행됨 ( Spring Application으로 치면 PostConstructor )
    static {
        initialize();
        initializeCount++;
    }

    private static void initialize() {
        try {
            customHolderStrategy = new SecurityContextHolderStrategy();
        } catch (Exception ex) {
            ReflectionUtils.handleReflectionException(ex);
        }
    }
    // context 비우기 After ( response 로 나갈 때 처리 )
    public static void clearContext() {
        customHolderStrategy.clearContext();
    }

    public static SecurityContext getContext() {
        return customHolderStrategy.getContext();
    }

    public static Supplier<SecurityContext> getDeferredContext() {
        return customHolderStrategy.getDeferredContext();
    }

    public static SecurityContextHolderStrategy getContextHolderStrategy() {
        return customHolderStrategy;
    }

    // 초기화 횟수 테스트
    public static int getInitializeCount() {
        return initializeCount;
    }
    // Context 셋팅
    public static void setContext(SecurityContext context) {
        customHolderStrategy.setContext(context);
    }
    // 지연, 사용할지,,
    public static void setDeferredContext(Supplier<SecurityContext> deferredContext) {
        customHolderStrategy.setDeferredContext(deferredContext);
    }
    public static SecurityContext createEmptyContext() {
        return customHolderStrategy.createEmptyContext();
    }

}
