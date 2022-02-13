package first.iteration.endlesscreation.service;


import com.auth0.jwt.interfaces.DecodedJWT;
import first.iteration.endlesscreation.Model.RoleEntity;
import first.iteration.endlesscreation.Model.UserDetailsImpl;
import first.iteration.endlesscreation.Model.UserEntity;
import first.iteration.endlesscreation.configuration.JWTUtils;
import first.iteration.endlesscreation.configuration.LoggedUserGetter;
import first.iteration.endlesscreation.dao.UserDAO;
import first.iteration.endlesscreation.dto.*;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.RoleRepository;
import first.iteration.endlesscreation.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
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
    private final Environment environment;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,UserDAO userDAO,PasswordEncoder passwordEncoder,AuthenticationManager authenticationManager,Environment environment) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userDAO = userDAO;
        this.authenticationManager = authenticationManager;
        this.environment = environment;
    }

    public void saveUserEntity(UserEntity userEntity){
        userDAO.saveUserEntity(userEntity);
    }

    public JWTokenDTO authenticateAndGetToken(UserLoginDTO userLoginDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userLoginDTO.getUsername(), userLoginDTO.getPassword()
        ));

        return JWTUtils.generateTokens(authentication);

    }

    public JWTokenDTO authenticateAndRefreshToken(RefresTokenDTO refreshTokenDTO) throws Exception{
        System.out.println("sprawdzam czy wogóle jestrem w tym tam gdize ten ------------------------------------------------");
        JWTokenDTO jwTokenDTO = new JWTokenDTO();
        try{
            DecodedJWT decodedRefreshJWT = JWTUtils.verifyToken(refreshTokenDTO.getRefreshToken());
            log.info("Doszło za decoded");
            UserEntity userEntity = userDAO.getUserEntityByUserName(decodedRefreshJWT.getSubject());
            log.info("Doszło za entity");
            UserDetailsImpl userDetails = UserDetailsImpl.build(userEntity);
            log.info("Doszło za userDetails");
            jwTokenDTO.setAccessToken(JWTUtils.generateAccessToken(userDetails));
            jwTokenDTO.setRefreshToken(JWTUtils.generateRefreshToken(userDetails));
            log.info("Doszło za setAccess");
        }catch(Exception e){
            throw new ResourceNotFoundException("Jakis problem z userem"+e.getMessage());
        }
        return jwTokenDTO;
    }


    public UserSendDTO getBasicUserData(){
        String userName = LoggedUserGetter.getUsser();
        if(userName.equals("anonymous")){
            throw new ResourceNotFoundException("Nie jestes zalogowanyt byq");
        }
        UserSendDTO userSendDTO = new UserSendDTO();
        UserEntity userEntity = userDAO.getUserEntityByUserName(userName);
        return new UserSendDTO(userName,userEntity.getAppUserId(),generateImageLink(userEntity.getImageLink()));
    }

    private String generateImageLink(String imageLink){
        if(imageLink != null){
            String bucketName = environment.getRequiredProperty("FIREBASE_BUCKET_NAME");
            return "https://firebasestorage.googleapis.com/v0/b/" + bucketName + "/o/" + imageLink + "?alt=media";
        }else {
            return "";
        }

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
