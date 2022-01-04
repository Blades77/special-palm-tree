package first.iteration.endlesscreation.service;


import com.auth0.jwt.interfaces.DecodedJWT;
import first.iteration.endlesscreation.Model.RoleEntity;
import first.iteration.endlesscreation.Model.UserDetailsImpl;
import first.iteration.endlesscreation.Model.UserEntity;
import first.iteration.endlesscreation.configuration.JWTUtils;
import first.iteration.endlesscreation.dao.UserDAO;
import first.iteration.endlesscreation.dto.JWTokenDTO;
import first.iteration.endlesscreation.dto.UserDTO;
import first.iteration.endlesscreation.dto.UserLoginDTO;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.RoleRepository;
import first.iteration.endlesscreation.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.*;
@Slf4j
@Service
public class UserService {

    private final UserDAO userDAO;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,UserDAO userDAO,PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userDAO = userDAO;
        this.authenticationManager = authenticationManager;
    }

    public JWTokenDTO authenticateAndGetToken(UserLoginDTO userLoginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginDTO.getUsername(), userLoginDTO.getPassword()
        ));

        return JWTUtils.generateTokens(authentication);

    }

    public JWTokenDTO authenticateAndRefreshToken(JWTokenDTO jwTokenDTO) throws Exception{
        try{
            DecodedJWT decodedRefreshJWT = JWTUtils.verifyToken(jwTokenDTO.getRefreshToken());
//            log.info("Doszło za decoded");
            UserEntity userEntity = userDAO.getUserEntityByUserName(decodedRefreshJWT.getSubject());
//            log.info("Doszło za entity");
            UserDetailsImpl userDetails = UserDetailsImpl.build(userEntity);
//            log.info("Doszło za userDetails");
            jwTokenDTO.setAccessToken(JWTUtils.generateAccessToken(userDetails));
//            log.info("Doszło za setAccess");
        }catch(Exception e){
            throw new ResourceNotFoundException("Jakis problem z userem");
        }
        return jwTokenDTO;
    }




    public UserDTO getUserByUserName(String name) {
        UserEntity userEntity = userDAO.getUserEntityByUserName(name);
        List<RoleEntity> roleEntityList = userDAO.getRoleEntityListByUserEntity(userEntity);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userEntity.getAppUserPassword());
        userDTO.setPassword(userEntity.getAppUserPassword());
        userDTO.setEmail(userEntity.getAppUserEmail());
        Set<String> roles = new HashSet<>();

        for(RoleEntity roleEntity : roleEntityList){
            roles.add(roleEntity.getRoleName());
        }
        userDTO.setRoles(roles);
        return userDTO;

    }

    public UserEntity getUserEntityByName (String name){
        return userDAO.getUserEntityByUserName(name);
    }
    public UserEntity getUserEntityById (Long id){ return  userDAO.getUserEntityById(id);}



    public Set<GrantedAuthority> getAuthorities(UserEntity userEntity) {
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        for(RoleEntity roleEntity : userEntity.getRoles()){
            grantedAuthoritySet.add(new SimpleGrantedAuthority(roleEntity.getRoleName()));
        }
        return grantedAuthoritySet;
    }

    public void saveUser(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setAppUserName(userDTO.getUsername());
        userEntity.setAppUserEmail(userDTO.getEmail());
        userEntity.setAppUserPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userEntity);
    }

    public void logoutUser(String token) {
//        addTokenToBlockList(token);
        SecurityContextHolder.clearContext();
    }


}
