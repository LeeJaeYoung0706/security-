package newws.client_gateway.security.authentication.context;

import newws.client_gateway.security.SecuritySerialVersionUID;
import newws.client_gateway.security.authentication.Authentication;
import org.springframework.context.annotation.Import;
import org.springframework.util.ObjectUtils;

/**
 * Security Context
 */
public class SecurityContext implements CustomSecurityContext{

    private static final long serialVersionUID = SecuritySerialVersionUID.SERIAL_SECURITY_CONTEXT_UID;
    private Authentication authentication;

    public SecurityContext() {
    }

    public SecurityContext(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public boolean isAuthenticated() {
        return authentication != null;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    @Override
    public Authentication getAuthentication() {
        System.out.println(" Custom Context Authentication 객체 = " + this.authentication.getClass());
        return this.authentication;
    }

    // 가져온 코드
    public boolean equals(Object obj) {
        if (obj instanceof SecurityContext) {
            SecurityContext other = (SecurityContext)obj;
            if (this.getAuthentication() == null && other.getAuthentication() == null) {
                return true;
            }

            if (this.getAuthentication() != null && other.getAuthentication() != null && this.getAuthentication().equals(other.getAuthentication())) {
                return true;
            }
        }

        return false;
    }
    // null 일 경우 0 반환 null 이 아닐 경우 해쉬코드 반환
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.authentication);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName()).append(" [");
        if (this.authentication == null) {
            sb.append("인증 객체가 없습니다.");
        }
        else {
            sb.append("인증 객체 = ").append(this.authentication);
        }
        sb.append("]");
        return sb.toString();
    }
}
