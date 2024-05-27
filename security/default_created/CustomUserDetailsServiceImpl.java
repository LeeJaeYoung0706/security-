package newws.client_gateway.security.default_created;

import newws.client_gateway.security.authentication.Authentication;
import newws.client_gateway.security.authentication.user_details.CustomUserDetails;
import newws.client_gateway.security.authentication.user_details.CustomUserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Override
    public CustomUserDetails getUserDetailsByUsername(String username) {
        return null;
    }
    @Override
    public void Test(Authentication authentication){
        System.out.println("test");
        System.out.println("객체 " + authentication.getName());
    }
}
