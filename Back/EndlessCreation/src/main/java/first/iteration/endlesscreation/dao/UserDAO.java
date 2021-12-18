package first.iteration.endlesscreation.dao;

import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.RoleEntity;
import first.iteration.endlesscreation.Model.UserEntity;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.RoleRepository;
import first.iteration.endlesscreation.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserDAO(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    public UserEntity getUserEntityByUserName(String name){
        return userRepository.findByAppUserName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user"));
    }

    public boolean checkIfUserExistsByUserName(String name){
        return userRepository.existsByAppUserName(name);
    }

    public RoleEntity getRoleByRoleName(String name){
        return roleRepository.findByRoleName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find role"));
    }

    public List<RoleEntity> getRoleEntityListByUserEntity(UserEntity userEntity){
        return roleRepository.getRoleEntityByUsers(userEntity)
                .orElseThrow(() -> new ResourceNotFoundException("User has no roles!"));
    }
}
