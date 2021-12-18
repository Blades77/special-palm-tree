package first.iteration.endlesscreation.service;


import first.iteration.endlesscreation.Model.RoleEntity;
import first.iteration.endlesscreation.Model.UserEntity;
import first.iteration.endlesscreation.dao.UserDAO;
import first.iteration.endlesscreation.dto.UserDTO;
import first.iteration.endlesscreation.repository.RoleRepository;
import first.iteration.endlesscreation.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserDAO userDAO;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,UserDAO userDAO) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userDAO = userDAO;
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




}
