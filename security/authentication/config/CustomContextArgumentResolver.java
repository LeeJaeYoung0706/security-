package newws.client_gateway.security.authentication.config;

import newws.client_gateway.security.authentication.Authentication;
import newws.client_gateway.security.authentication.annotation.AuthPrincipal;
import newws.client_gateway.security.authentication.context.SecurityContextHolderStrategy;
import newws.client_gateway.security.authentication.context.SecurityHolder;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

@Component
public class CustomContextArgumentResolver implements HandlerMethodArgumentResolver {

    private SecurityContextHolderStrategy securityContextHolderStrategy = SecurityHolder.getContextHolderStrategy();
    // 런타임 객체 그래프 조회
    private ExpressionParser parser = new SpelExpressionParser();
    private BeanResolver beanResolver;

    public void setBeanResolver(BeanResolver beanResolver) {
        this.beanResolver = beanResolver;
    }

    public CustomContextArgumentResolver() {
    }

    public void setSecurityContextHolderStrategy(SecurityContextHolderStrategy securityContextHolderStrategy) {
        Assert.notNull(securityContextHolderStrategy, "시큐리티홀더스트레이트지는 필수 값입니다.");
        this.securityContextHolderStrategy = securityContextHolderStrategy;
    }

    // 특정 애노테이션 검색
    private <T extends Annotation> T findMethodAnnotation(Class<T> annotationClass, MethodParameter parameter) {
        T annotation = parameter.getParameterAnnotation(annotationClass);
        if (annotation != null) {
            return annotation;
        } else {
            Annotation[] annotationsToSearch = parameter.getParameterAnnotations();
            Annotation[] var5 = annotationsToSearch;
            int var6 = annotationsToSearch.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Annotation toSearch = var5[var7];
                annotation = AnnotationUtils.findAnnotation(toSearch.annotationType(), annotationClass);
                if (annotation != null) {
                    return annotation;
                }
            }

            return null;
        }
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return this.findMethodAnnotation(AuthPrincipal.class, parameter) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = this.securityContextHolderStrategy.getContext().getAuthentication();
        System.out.println(" Custom Resolver Authentication 객체 = " + authentication.getClass());
        if (authentication == null) {
            return null;
        } else {
            Object principal = authentication;
            System.out.println(" Custom Resolver Authentication 객체 = " + authentication.getClass());
            AuthPrincipal annotation = (AuthPrincipal)this.findMethodAnnotation(AuthPrincipal.class, parameter);
            String expressionToParse = annotation.expression();
            if (StringUtils.hasLength(expressionToParse)) {
                StandardEvaluationContext context = new StandardEvaluationContext();
                context.setRootObject(principal);
                context.setVariable("this", principal);
                context.setBeanResolver(this.beanResolver);
                Expression expression = this.parser.parseExpression(expressionToParse);
                principal = expression.getValue(context);
            }
            // 할당이 가능한지 체크
            if (principal != null && !ClassUtils.isAssignable(parameter.getParameterType(), principal.getClass())) {
                if (annotation.errorOnInvalidType()) {
                    throw new ClassCastException(principal + " is not assignable to " + parameter.getParameterType());
                } else {
                    return null;
                }
            } else {
                return principal;
            }
        }
    }
}
