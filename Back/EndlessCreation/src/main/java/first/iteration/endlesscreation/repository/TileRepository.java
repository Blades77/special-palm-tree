package first.iteration.endlesscreation.repository;

import first.iteration.endlesscreation.Model.TileEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface TileRepository extends JpaRepository<TileEntity, Long> {

}
