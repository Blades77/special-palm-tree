package first.iteration.endlesscreation.repository;

import first.iteration.endlesscreation.Model.GroupDataEntity;

import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupDataRepository extends JpaRepository<GroupDataEntity, Long> {
    GroupDataEntity getGroupDataEntityByTiles(TileEntity tileEntity);
}
