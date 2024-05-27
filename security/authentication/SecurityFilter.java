package newws.client_gateway.security.authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import newws.client_gateway.security.authentication.context.SecurityHolder;
import newws.client_gateway.security.authentication.user_details.CustomUserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        TestCustomDetails testCustomDetails = new TestCustomDetails();
        System.out.println("여기타나요 필터? ");
        JsonWebTokenAuthentication jsonWebTokenAuthentication = new JsonWebTokenAuthentication(testCustomDetails, null, testCustomDetails.getAuthorities());
        SecurityHolder.getContext().setAuthentication(jsonWebTokenAuthentication);
        filterChain.doFilter(request,response);
    }
}
