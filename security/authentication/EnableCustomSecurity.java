package newws.client_gateway.security.authentication;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 런타임에도 유지
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE}) // 클래스, 인터페이스, 열거형만 가능
@Documented // java doc에 추가
public @interface EnableCustomSecurity {
}
