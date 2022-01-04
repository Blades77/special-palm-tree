package first.iteration.endlesscreation.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import first.iteration.endlesscreation.configuration.JWTUtils;
import first.iteration.endlesscreation.service.GroupDataService;
import first.iteration.endlesscreation.service.TileService;
import first.iteration.endlesscreation.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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




import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String path = request.getServletPath();
        if(path.equals("/login/**") || path.equals("user/refresh?**")){
            filterChain.doFilter(request,response);
        } else{
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                try{

                    DecodedJWT decodedJWT = JWTUtils.verifyToken(authorizationHeader);
                    UserDetails userDetails = JWTUtils.getUserFromToken(decodedJWT);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);


                    if(path.equals("/tile/**")){
                        String tileId = path.substring(path.lastIndexOf("/")+1);
                        logger.info("Loguje odpowiednie id: "+tileId);

                    }
                    //-----------------------------
                    filterChain.doFilter(request,response);
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
        }
    }
}
