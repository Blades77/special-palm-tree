package first.iteration.endlesscreation.repository;


import first.iteration.endlesscreation.Model.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, Long> {
    Optional<ColorEntity> findByColorValueHex(String hex);

}
