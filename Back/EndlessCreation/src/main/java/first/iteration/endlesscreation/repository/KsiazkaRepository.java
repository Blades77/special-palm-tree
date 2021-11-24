package first.iteration.endlesscreation.repository;

import first.iteration.endlesscreation.Model.ksiazkaEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface KsiazkaRepository extends JpaRepository<ksiazkaEntity, Long> {

}
