package first.iteration.endlesscreation.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import first.iteration.endlesscreation.configuration.JWTUtils;
import first.iteration.endlesscreation.configuration.LoggedUserGetter;
import first.iteration.endlesscreation.dao.GroupDataDAO;
import first.iteration.endlesscreation.exception.DefaultExpection;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.GroupDataRepository;
import first.iteration.endlesscreation.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import first.iteration.endlesscreation.service.GroupDataService;
import first.iteration.endlesscreation.service.TileService;
import first.iteration.endlesscreation.service.UserService;


import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    UserDetails userDetails;
    private final AuthHelper authHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String path = request.getServletPath();
        if(authorizationHeader != null){
            if(authorizationHeader.startsWith("Bearer ")){
                try{

                    DecodedJWT decodedJWT = JWTUtils.verifyToken(authorizationHeader);
                    userDetails = JWTUtils.getUserFromToken(decodedJWT);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//

                    if(authHelper.loggedAccessCheck(path)){
                        filterChain.doFilter(request,response);
                    }else {
                        throw new DefaultExpection("Access denied");
                    }


//                    filterChain.doFilter(request,response);
                }catch(Exception e){
                    response.setHeader("error", e.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message",e.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }



            } else{
                filterChain.doFilter(request,response);
            }
        }else{
            if(path.equals("/login") || path.equals("user/refresh")){
                filterChain.doFilter(request,response);
            }
            else if(authHelper.notLoggedAccessCheck(path)){
//                logger.info("jestem po autoryzacji");
                filterChain.doFilter(request,response);
            }else{
                throw new DefaultExpection("Access denied");
            }
        }


    }
}
