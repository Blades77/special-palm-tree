package first.iteration.endlesscreation.repository;

import first.iteration.endlesscreation.Model.GroupDataEntity;
import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.Model.TileEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TileRepository extends JpaRepository<TileEntity, Long> {
    Optional<List<TileEntity> > getTileEntityByGroupDataEntity(GroupDataEntity groupDataEntity);

    @Query(value="SELECT * FROM tile WHERE group_id = :groupId AND tile_id IN ( SELECT tile_id FROM tile_tag WHERE tag_id IN :tagIdList\n" +
            " GROUP BY tile_id HAVING COUNT(tile_id) = :listLength)",nativeQuery = true)
    Optional<List<TileEntity>> getTileEntityByTagIdList(@Param("tagIdList") List<Long> tagIdList, @Param("listLength") int listLength, @Param("groupId") Long groupId);

    @Query(value="SELECT DISTINCT t.* FROM tile t JOIN tile_tag g ON t.tile_id = g.tile_id WHERE t.group_id = :groupId AND g.tag_id IN :tagIdList" ,nativeQuery = true)
    Optional<List<TileEntity>> getTileEntityByAtLeastOneTagIdList(@Param("tagIdList") List<Long> tagIdList,@Param("groupId") Long groupId);

    @Query(value ="SELECT * FROM tile WHERE tile_title LIKE :string",nativeQuery = true)
    Optional<List<TileEntity>> searchTileTitleByParam(@Param("string") String search);
}
