package first.iteration.endlesscreation.repository;

import first.iteration.endlesscreation.Model.GroupDataEntity;

import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupDataRepository extends JpaRepository<GroupDataEntity, Long> {
    Optional<GroupDataEntity> getGroupDataEntityByTiles(TileEntity tileEntity);
    Optional<List<GroupDataEntity>> getGroupDataEntityByUsers(UserEntity userEntity);
}
