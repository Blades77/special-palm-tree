package first.iteration.endlesscreation.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import first.iteration.endlesscreation.Model.UserDetailsImpl;
import first.iteration.endlesscreation.dto.JWTokenDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTUtils {

    private final String secret = "sekret";
    private final int expireTime = 36000;
    private static final Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

    public static JWTokenDTO generateTokens(Authentication authentication){
        User userDetails = (User) authentication.getPrincipal();
        String access_token = generateAccessToken(userDetails);
        String refresh_token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 120 * 60 * 1000)) // tu jest 120 minut
                .withIssuer("EndlessCreation")
                .sign(algorithm);

        return new JWTokenDTO(access_token,refresh_token);
    }

    public static String generateRefreshToken(UserDetails userDetails){
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 240 * 60 * 1000)) // tu jest 240 minut
                .withIssuer("EndlessCreation")
                .sign(algorithm);

    }

    public static String generateAccessToken(UserDetails userDetails){
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000)) // tu jest 10 minut
                .withIssuer("EndlessCreation")
                .withClaim("roles",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public static DecodedJWT verifyToken(String authorizationHeader){
        String token = authorizationHeader.substring("Bearer ".length());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return  verifier.verify(token);

    }

    public static UserDetailsImpl getUserFromToken(DecodedJWT decodedJWT) {
        return new UserDetailsImpl(
                decodedJWT.getSubject(),
                null,
                decodedJWT.getClaim("roles").asList(String.class).stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
    }


}
