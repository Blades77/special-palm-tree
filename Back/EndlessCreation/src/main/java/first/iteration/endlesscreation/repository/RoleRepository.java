package first.iteration.endlesscreation.repository;

import first.iteration.endlesscreation.Model.RoleEntity;
import first.iteration.endlesscreation.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByRoleName(String role);
    Optional<List<RoleEntity>> getRoleEntityByUsers(UserEntity userEntity);
}
