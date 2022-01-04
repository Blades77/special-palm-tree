package first.iteration.endlesscreation.configuration;


import first.iteration.endlesscreation.service.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class CustomLogoutHandler implements LogoutHandler{

    private final UserDetailsImpl userService;

    public CustomLogoutHandler(UserDetailsImpl userService) {
        this.userService = userService;
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication){
        userService.logoutUser(httpServletRequest.getHeader("Authorization"));
    }

}
