package newws.client_gateway.security.authentication;

import newws.client_gateway.security.authentication.user_details.CustomUserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Json Web Token 만 사용할 경우 필요한가,
 */
public abstract class AbstractAuthenticationToken implements Authentication, CredentialsContainer{
    // 필수 조건 ( 권한 리스트 )
    private final Collection<GrantedAuthority> authorities;
    //WebAuthenticationDetails
    private Object details;
    // 빈 리스트
    public static final List<GrantedAuthority> NO_AUTHORITIES = Collections.emptyList();
    // 권한이 있는가
    private boolean authenticated = false;

    // token 인가 객체 생성
    protected AbstractAuthenticationToken(Collection<GrantedAuthority> authorities) {
        if (authorities != null)
            // 변경 불가한 리스트
            if (!authorities.isEmpty())
                this.authorities = List.copyOf(authorities);
            else
                this.authorities = NO_AUTHORITIES;
        else
            this.authorities = NO_AUTHORITIES;
    }

    /**
     * 권한 리스트 가져오기
     * @return
     */
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    // 자격 증명 삭제 -> 하위 상속받은 클래스에서 구현 -> null 만드는 구현 메소드 구축 하면 삭제,
    @Override
    public void eraseCredentials() {
//        eraseSecret(getCredentials());  // 자격 증명 지우기 // 클라이언트 인증 처리를 여기서 하는것이 올바를 것같은데,,
        this.eraseSecret(getPrincipal());    // 주체 지우기
        this.eraseSecret(this.details);      // 기타 세부 정보 지우기
    }

    private void eraseSecret(Object secret) {
        if (secret instanceof CredentialsContainer) {
            ((CredentialsContainer) secret).eraseCredentials();
        }
    }
    // IP 주소만 처리,
    public void setDetails(Object details) {
        this.details = details;
    }

    @Override
    public String getName() {
        // Object에 대한 타입 검증 SpringSecurity의 경우 UserDetails의 이름을 가져옴,
        if (this.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getUsername();
        }
        if (this.getPrincipal() instanceof Principal principal) {
            return principal.getName();
        }
        return (this.getPrincipal() == null) ? "" : this.getPrincipal().toString();
    }

    @Override
    public Object getDetails() {
        return this.details;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    public boolean equals(Object obj) {
        if (obj instanceof AbstractAuthenticationToken) {
            AbstractAuthenticationToken test = (AbstractAuthenticationToken)obj;
            if (!this.authorities.equals(test.authorities)) {
                return false;
            } else if (this.details == null && test.getDetails() != null) {
                return false;
            } else if (this.details != null && test.getDetails() == null) {
                return false;
            } else if (this.details != null && !this.details.equals(test.getDetails())) {
                return false;
            } else if (this.getCredentials() == null && test.getCredentials() != null) {
                return false;
            } else if (this.getCredentials() != null && !this.getCredentials().equals(test.getCredentials())) {
                return false;
            } else if (this.getPrincipal() == null && test.getPrincipal() != null) {
                return false;
            } else if (this.getPrincipal() != null && !this.getPrincipal().equals(test.getPrincipal())) {
                return false;
            } else {
                return this.isAuthenticated() == test.isAuthenticated();
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int code = 31;

        GrantedAuthority authority;
        for(Iterator var2 = this.authorities.iterator(); var2.hasNext(); code ^= authority.hashCode()) {
            authority = (GrantedAuthority)var2.next();
        }

        if (this.getPrincipal() != null) {
            code ^= this.getPrincipal().hashCode();
        }

        if (this.getCredentials() != null) {
            code ^= this.getCredentials().hashCode();
        }

        if (this.getDetails() != null) {
            code ^= this.getDetails().hashCode();
        }

        if (this.isAuthenticated()) {
            code ^= -37;
        }

        return code;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append(" [");
        sb.append("Principal=").append(this.getPrincipal()).append(", ");
        sb.append("Credentials=[PROTECTED], ");
        sb.append("Authenticated=").append(this.isAuthenticated()).append(", ");
        sb.append("Details=").append(this.getDetails()).append(", ");
        sb.append("Granted Authorities=").append(this.authorities);
        sb.append("]");
        return sb.toString();
    }

}
