package first.iteration.endlesscreation.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import first.iteration.endlesscreation.Model.RoleEntity;
import first.iteration.endlesscreation.Model.UserEntity;
import first.iteration.endlesscreation.configuration.SpringFoxConfig;
import first.iteration.endlesscreation.dto.BookDTO;
import first.iteration.endlesscreation.dto.JWTokenDTO;
import first.iteration.endlesscreation.dto.UserDTO;
import first.iteration.endlesscreation.dto.UserLoginDTO;
import first.iteration.endlesscreation.dto.create.BookCreateDTO;
import first.iteration.endlesscreation.repository.UserRepository;
import first.iteration.endlesscreation.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@Api(tags = {SpringFoxConfig.user})
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService,UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @ApiOperation(value ="Return data of specific user")
    @GetMapping("user/{name}")
    private UserDTO getSpecifiUser(@ApiParam(value = "Name of user", example = "Hadzior", required = true) @PathVariable String name) {
        return userService.getUserByUserName(name);
    }

    @ApiOperation(value ="Login user")
    @PostMapping("login")
    private JWTokenDTO signup(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return userService.authenticateAndGetToken(userLoginDTO);

    }

    @ApiOperation(value ="Refresh token")
    @PostMapping("user/refresh")
    private JWTokenDTO authenticateAndRefreshToken(@Valid @RequestBody JWTokenDTO jwTokenDTO) throws Exception {
        return userService.authenticateAndRefreshToken(jwTokenDTO);
    }

    @ApiOperation(value = "Creates user")
    @PostMapping("/user")
    private void createUser(@RequestBody UserDTO userDTO) {
        userService.saveUser(userDTO);
    }
}
