package first.iteration.endlesscreation.service;

import first.iteration.endlesscreation.Model.RoleEntity;
import first.iteration.endlesscreation.Model.UserEntity;
import first.iteration.endlesscreation.dao.UserDAO;
import first.iteration.endlesscreation.repository.RoleRepository;
import first.iteration.endlesscreation.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsImpl implements UserDetailsService {

    private final UserDAO userDAO;

    public UserDetailsImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userDAO.getUserEntityByUserName(userName);
        if(userEntity == null){
            throw new UsernameNotFoundException(userName);
        }
        Set<GrantedAuthority> grantedAuthoritySet = getAuthorities(userEntity);
        return new User(userEntity.getAppUserName(), userEntity.getAppUserPassword(), grantedAuthoritySet);
    }

    private Set<GrantedAuthority> getAuthorities(UserEntity userEntity) {
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        for(RoleEntity roleEntity : userEntity.getRoles()){
            grantedAuthoritySet.add(new SimpleGrantedAuthority(roleEntity.getRoleName()));
        }
        return grantedAuthoritySet;
    }

    public void logoutUser(String token) {
        System.out.println("Useer loget out");
//        addTokenToBlockList(token);
        SecurityContextHolder.clearContext();
    }


}
