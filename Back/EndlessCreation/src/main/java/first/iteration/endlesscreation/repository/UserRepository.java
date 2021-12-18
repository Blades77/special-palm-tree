package first.iteration.endlesscreation.repository;


import first.iteration.endlesscreation.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByAppUserName(String name);
    Boolean existsByAppUserName(String username);
}
