package newws.client_gateway.security.authentication.user_details;

import newws.client_gateway.security.authentication.Authentication;

public interface CustomUserDetailsService {

    CustomUserDetails getUserDetailsByUsername(String username);
    public void Test(Authentication authentication);
}
