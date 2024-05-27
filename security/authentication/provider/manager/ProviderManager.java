package newws.client_gateway.security.authentication.provider.manager;

import newws.client_gateway.security.authentication.AbstractAuthenticationToken;
import newws.client_gateway.security.authentication.Authentication;
import newws.client_gateway.security.authentication.CredentialsContainer;
import newws.client_gateway.security.authentication.client.ClientCredentials;
import newws.client_gateway.security.authentication.exceptions.AuthenticationException;
import newws.client_gateway.security.authentication.exceptions.ClientCredentialsNotFoundException;
import newws.client_gateway.security.authentication.provider.providers.JsonWebTokenAuthenticationProvider;
import newws.client_gateway.security.authentication.provider.messages.CustomSecurityMessageSource;
import newws.client_gateway.security.authentication.provider.providers.AuthenticationProvider;
import newws.client_gateway.security.authentication.provider.publisher.AuthenticationEventPublisher;
import newws.client_gateway.security.authentication.provider.publisher.CustomAuthenticationEventPublisher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Component
public class ProviderManager implements AuthenticationManager, MessageSourceAware, InitializingBean {

    private AuthenticationEventPublisher eventPublisher;
    private List<AuthenticationProvider> providers;
    protected MessageSourceAccessor messages;
    // 인증 후 credentials 정보를 삭제 하기 위한 멤버변수  -> 이는 stateless 인 REST API 에서 인증정보에 대한 캐시 를 가지고 있을 때, 문제가 발생할 수 있습니다.
    // false 로 설정할 경우 캐싱 처리를 사용할 수 있습니다. 원래 Security에서는
    private boolean eraseCredentialsAfterAuthentication;
    // 기존 Security는 해당 manager가 관리하는 provider 들로 인증을 처리할 수 없는 경우 다른 manager를 참조하기 위해서
    //     private AuthenticationManager parent; 설정합니다.

    public ProviderManager(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = new CustomAuthenticationEventPublisher(applicationEventPublisher);
        this.providers = initProviders();
        // 언어 설정 ( 한국어 )
        this.messages = CustomSecurityMessageSource.getAccessor();
        this.eraseCredentialsAfterAuthentication = true;
        this.checkState();
    }

    private void checkState() {
        Assert.isTrue( !this.providers.isEmpty(), "인증 프로바이더가 주입되지 않았습니다.");
        Assert.isTrue(!CollectionUtils.contains(this.providers.iterator(), (Object)null), "프로바이더에 값이 존재하지 않습니다.");
    }

    private List<AuthenticationProvider> initProviders(){
        try {
            return Arrays.asList(new JsonWebTokenAuthenticationProvider());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // false 로 설정
    public void setEraseCredentialsAfterAuthentication(boolean eraseCredentialsAfterAuthentication) {
        this.eraseCredentialsAfterAuthentication = eraseCredentialsAfterAuthentication;
    }



    public List<AuthenticationProvider> getProviders() {
        return this.providers;
    }

    public boolean isEraseCredentialsAfterAuthentication() {
        return this.eraseCredentialsAfterAuthentication;
    }

    @Override
    public Authentication authenticate(Authentication authentication, ClientCredentials credentials) throws AuthenticationException {
        // 인증 객체 타입 검증
        Class<? extends Authentication> toTest = authentication.getClass();
        Iterator providerIterator = this.getProviders().iterator();
        Authentication result = null;

        while (providerIterator.hasNext()) {
            AuthenticationProvider provider = (AuthenticationProvider) providerIterator.next();
            // 인증 객체 타입이 맞다면,
            if (provider.supports(toTest)) {
                result = provider.authenticate(authentication,credentials);
                if (result != null) {
                    this.copyDetails(authentication, result);
                    break;
                }
            }
        }
        if (result != null) {
            // 인증 정보 비우기
            if (this.eraseCredentialsAfterAuthentication && result instanceof CredentialsContainer) {
                ((CredentialsContainer)result).eraseCredentials();
            }
            this.eventPublisher.publishAuthenticationSuccess(result);
            return result;
        } else {
            throw new ClientCredentialsNotFoundException("error");
        }
    }

    // 디테일 복사
    private void copyDetails(Authentication source, Authentication dest) {
        if (dest instanceof AbstractAuthenticationToken) {
            AbstractAuthenticationToken token = (AbstractAuthenticationToken)dest;
            if (dest.getDetails() == null) {
                token.setDetails(source.getDetails());
            }
        }
    }

    @Override
    public void afterPropertiesSet() {
        this.checkState();
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }
}
