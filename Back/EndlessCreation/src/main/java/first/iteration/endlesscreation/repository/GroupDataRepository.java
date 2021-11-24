package first.iteration.endlesscreation.repository;

import first.iteration.endlesscreation.Model.Group_dataEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface GroupDataRepository extends JpaRepository<Group_dataEntity, Long> {

}
