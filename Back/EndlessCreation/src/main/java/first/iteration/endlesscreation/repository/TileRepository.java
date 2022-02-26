package first.iteration.endlesscreation.repository;

import first.iteration.endlesscreation.Model.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TileRepository extends JpaRepository<TileEntity, Long> {
    Optional<List<TileEntity>> getTileEntityByGroupDataEntity(GroupDataEntity groupDataEntity);
    Optional<List<TileEntity>> getTileEntityByGroupDataEntity(GroupDataEntity groupDataEntity,Pageable pageable);


    @Query(value="SELECT * FROM tile WHERE group_id IN :groupIdList AND created_at BETWEEN :endDate AND :nowDate",nativeQuery = true)
    Optional<List<TileEntity>> getTileEntitiesByGroupDataIdList(@Param("groupIdList") List<Long> groupIdList,LocalDateTime nowDate, LocalDateTime endDate, Pageable pageable);

    @Query(value="SELECT * FROM tile WHERE group_id = :groupId AND tile_id IN ( SELECT tile_id FROM tile_tag WHERE tag_id IN :tagIdList\n" +
            " GROUP BY tile_id HAVING COUNT(tile_id) = :listLength)",nativeQuery = true)
    Optional<List<TileEntity>> getTileEntityByTagIdList(@Param("tagIdList") List<Long> tagIdList, @Param("listLength") int listLength, @Param("groupId") Long groupId);

    @Query(value="SELECT DISTINCT t.* FROM tile t JOIN tile_tag g ON t.tile_id = g.tile_id WHERE t.group_id = :groupId AND g.tag_id IN :tagIdList" ,nativeQuery = true)
    Optional<List<TileEntity>> getTileEntityByAtLeastOneTagIdList(@Param("tagIdList") List<Long> tagIdList ,@Param("groupId") Long groupId);

    @Query(value="SELECT * FROM tile WHERE group_id IN :groupIdList AND tile_id IN ( SELECT tile_id FROM tile_tag WHERE tag_id IN :tagIdList\n" +
            " GROUP BY tile_id HAVING COUNT(tile_id) = :listLength)",nativeQuery = true)
    Optional<List<TileEntity>> getTileEntityByTagIdListAndGroupIdList(@Param("tagIdList") List<Long> tagIdList, @Param("listLength") int listLength, @Param("groupIdList") List<Long> groupIdList);

    @Query(value="SELECT DISTINCT t.* FROM tile t JOIN tile_tag g ON t.tile_id = g.tile_id WHERE t.group_id = :groupIdList AND g.tag_id IN :tagIdList" ,nativeQuery = true)
    Optional<List<TileEntity>> getTileEntityByAtLeastOneTagIdListAndGroupIdList(@Param("tagIdList") List<Long> tagIdList ,@Param("groupIdList")  List<Long> groupIdList);

    @Query(value ="SELECT * FROM tile WHERE tile_title LIKE %:string% AND group_id IN :groupIdList",nativeQuery = true)
    Optional<List<TileEntity>> searchTileTitleByParam(@Param("string") String search, @Param("groupIdList") List<Long> groupIdList);

    @Query(value="SELECT * FROM tile WHERE tile_title LIKE %:string% AND group_id = :groupId",nativeQuery = true)
    Optional<List<TileEntity>> getTileEntitiesByGroupDataId(@Param("string") String search,@Param("groupId") Long groupId);

    @Query(value="SELECT COUNT(*) FROM tile_like WHERE tile_id = :tileId", nativeQuery = true)
    Optional<Integer>getLikesForTile(@Param("tileId") Long tileId);

    @Query(value="SELECT COUNT(*) FROM comment_tile WHERE tile_id = :tileId", nativeQuery = true)
    Optional<Integer>getCommentsCountForTile(@Param("tileId") Long tileId);

    @Query(value ="SELECT ISNULL((SELECT 1 FROM tile_like WHERE tile_id = :tileId AND app_user_id IN (SELECT app_user_id FROM app_user WHERE app_user_name = :userName)),0)",nativeQuery = true)
    int isUserLikedTile(@Param("tileId") Long tileId,@Param("userName") String userName);

    @Modifying
    @Query(value ="DELETE FROM tile_like WHERE tile_id = :tileId AND app_user_id IN (SELECT app_user_id FROM app_user WHERE app_user_name = :userName)",nativeQuery = true)
    @Transactional
    void deleteLikeFromTile(@Param("tileId")Long tileId,@Param("userName") String userName);

    @Modifying
    @Query(value="INSERT INTO tile_like  (app_user_id,tile_id) VALUES ((SELECT app_user_id FROM app_user WHERE app_user_name = :userName),:tileId)", nativeQuery = true)
    @Transactional
    void addLikeToTile(@Param("tileId")Long tileId,@Param("userName") String userName);

    @Query(value ="SELECT ISNULL((SELECT 1 FROM save_tile WHERE tile_id = :tileId AND app_user_id IN (SELECT app_user_id FROM app_user WHERE app_user_name = :userName)),0)",nativeQuery = true)
    int isUserSavedTile(@Param("tileId") Long tileId,@Param("userName") String userName);

    //----------------------------------------------------------------------------- likes start

    @Query(value="SELECT t.tile_id, t.tile_title, t.tile_data,t.owner_user_id, t.group_id, t.created_at, t.updated_at\n" +
            "FROM tile t WHERE t.group_id IN :groupIdList AND t.created_at BETWEEN :endDate AND :nowDate ORDER BY (SELECT COUNT(*) FROM tile_like WHERE tile_id = t.tile_id) DESC",nativeQuery = true)
    Optional<List<TileEntity>> getTileEntitiesByGroupIdListSortByLikeDESC(@Param("groupIdList") List<Long> groupIdList,LocalDateTime endDate,LocalDateTime nowDate, Pageable pageable);

    @Query(value="SELECT t.tile_id, t.tile_title, t.tile_data,t.owner_user_id, t.group_id, t.created_at, t.updated_at\n" +
            "FROM tile t WHERE t.group_id IN :groupIdList AND t.created_at BETWEEN :endDate AND :nowDate\n" +
            "AND t.tile_id IN ( SELECT tile_id FROM tile_tag WHERE tag_id IN :tagIdList GROUP BY tile_id )\n" +
            " ORDER BY (SELECT COUNT(*) FROM tile_like WHERE tile_id = t.tile_id) DESC",nativeQuery = true)
    Optional<List<TileEntity>> getTileEntitiesByGroupIdListAndTagIdListSortByLikeDESC(@Param("groupIdList") List<Long> groupIdList,LocalDateTime endDate,LocalDateTime nowDate,List<Long> tagIdList, Pageable pageable);

    @Query(value="SELECT t.tile_id, t.tile_title, t.tile_data,t.owner_user_id, t.group_id, t.created_at, t.updated_at\n" +
            "FROM tile t WHERE t.group_id IN :groupIdList AND t.created_at BETWEEN :endDate AND :nowDate AND t.tile_title LIKE %:search% ORDER BY (SELECT COUNT(*) FROM tile_like WHERE tile_id = t.tile_id) DESC",nativeQuery = true)
    Optional<List<TileEntity>> getTileEntitiesByGroupIdListAndSearchSortByLikeDESC(@Param("groupIdList") List<Long> groupIdList,LocalDateTime endDate,LocalDateTime nowDate,String search, Pageable pageable);


    @Query(value="SELECT  t.tile_id, t.tile_title, t.tile_data,t.owner_user_id, t.group_id, t.created_at, t.updated_at\n" +
            "FROM tile t WHERE t.group_id IN :groupIdList AND t.created_at BETWEEN :endDate AND :nowDate ORDER BY (SELECT COUNT(*) FROM tile_like WHERE tile_id = t.tile_id) ASC",nativeQuery = true)
    Optional<List<TileEntity>> getTileEntitiesByGroupIdListSortByLikeASC(@Param("groupIdList") List<Long> groupIdList, LocalDateTime endDate,LocalDateTime nowDate,  Pageable pageable);

    @Query(value="SELECT  t.tile_id, t.tile_title, t.tile_data,t.owner_user_id, t.group_id, t.created_at, t.updated_at\n" +
            "FROM tile t WHERE t.group_id IN :groupIdList AND t.created_at BETWEEN :endDate AND :nowDate\n" +
            "AND t.tile_id IN ( SELECT tile_id FROM tile_tag WHERE tag_id IN :tagIdList GROUP BY tile_id)\n" +
            " ORDER BY (SELECT COUNT(*) FROM tile_like WHERE tile_id = t.tile_id) ASC",nativeQuery = true)
    Optional<List<TileEntity>> getTileEntitiesByGroupIdListAndTagIdListSortByLikeASC(@Param("groupIdList") List<Long> groupIdList, LocalDateTime endDate,LocalDateTime nowDate, List<Long> tagIdList, Pageable pageable);

    @Query(value="SELECT  t.tile_id, t.tile_title, t.tile_data,t.owner_user_id, t.group_id, t.created_at, t.updated_at\n" +
            "FROM tile t WHERE t.group_id IN :groupIdList AND t.created_at BETWEEN :endDate AND :nowDate AND t.tile_title LIKE %:search% ORDER BY (SELECT COUNT(*) FROM tile_like WHERE tile_id = t.tile_id) ASC",nativeQuery = true)
    Optional<List<TileEntity>> getTileEntitiesByGroupIdListAndSearchSortByLikeASC(@Param("groupIdList") List<Long> groupIdList, LocalDateTime endDate,LocalDateTime nowDate, String search, Pageable pageable);

    //----------------------------------------------------------------------------- likes end

    @Modifying
    @Query(value ="DELETE FROM save_tile WHERE tile_id = :tileId AND app_user_id IN (SELECT app_user_id FROM app_user WHERE app_user_name = :userName)",nativeQuery = true)
    @Transactional
    void unsaveTileForSaveTile(@Param("tileId")Long tileId,@Param("userName") String userName);

    @Modifying
    @Query(value="INSERT INTO save_tile  (app_user_id,tile_id) VALUES ((SELECT app_user_id FROM app_user WHERE app_user_name = :userName),:tileId)", nativeQuery = true)
    @Transactional
    void saveTileToSaveTile(@Param("tileId")Long tileId,@Param("userName") String userName);


    @Query(value="SELECT * FROM tile WHERE group_id IN :groupIdList ORDER BY created_at DESC",nativeQuery = true)
    Optional<List<TileEntity>> getNewestTileEntitiesForDashboard(List<Long> groupIdList,Pageable pageable);

    @Query(value="SELECT * FROM tile W WHERE tile_title LIKE %:search% AND group_id IN :groupIdList ORDER BY created_at DESC",nativeQuery = true)
    Optional<List<TileEntity>> getNewestTileEntitiesForDashboardWithSearch(List<Long> groupIdList,String search,Pageable pageable);
//

    @Query(value="SELECT * FROM tile WHERE group_id IN :groupIdList AND tile_id IN ( SELECT tile_id FROM tile_tag WHERE tag_id IN :tagIdList\n" +
            " GROUP BY tile_id) ORDER BY created_at DESC",nativeQuery = true)
    Optional<List<TileEntity>> getNewestTileEntitiesForDashboardWithTagIdList(List<Long> groupIdList,List<Long> tagIdList,Pageable pageable);

    @Query(value="SELECT  t.tile_id, t.tile_title, t.tile_data,t.owner_user_id, t.group_id, t.created_at, t.updated_at\n" +
            "     FROM tile t WHERE t.group_id IN :groupIdList AND t.created_at  BETWEEN :endDate AND :nowDate\n" +
            "\t ORDER BY (SELECT COUNT(*) FROM tile_like WHERE tile_id = t.tile_id) DESC, (SELECT COUNT(*) FROM comment_tile WHERE tile_id = t.tile_id) DESC",nativeQuery = true)
    Optional<List<TileEntity>> getHottestTileEntitiesForDashboard(List<Long> groupIdList,LocalDateTime endDate,LocalDateTime nowDate,Pageable pageable);

    @Query(value="SELECT  t.tile_id, t.tile_title, t.tile_data,t.owner_user_id, t.group_id, t.created_at, t.updated_at\n" +
            "     FROM tile t WHERE t.group_id IN :groupIdList AND tile_title LIKE %:search% AND t.created_at  BETWEEN :endDate AND :nowDate\n" +
            "\t ORDER BY (SELECT COUNT(*) FROM tile_like WHERE tile_id = t.tile_id) DESC, (SELECT COUNT(*) FROM comment_tile WHERE tile_id = t.tile_id) DESC",nativeQuery = true)
    Optional<List<TileEntity>> getHottestTileEntitiesForDashboardWithSearch(List<Long> groupIdList,LocalDateTime endDate,LocalDateTime nowDate,String search,Pageable pageable);
//
    @Query(value="SELECT  t.tile_id, t.tile_title, t.tile_data,t.owner_user_id, t.group_id, t.created_at, t.updated_at\n" +
            "     FROM tile t WHERE t.group_id IN :groupIdList AND t.created_at  BETWEEN :endDate AND :nowDate\n" +
            "AND tile_id IN ( SELECT tile_id FROM tile_tag WHERE tag_id IN :tagIdList GROUP BY tile_id HAVING COUNT(tile_id) = :listLength)\n"+
            "\t ORDER BY (SELECT COUNT(*) FROM tile_like WHERE tile_id = t.tile_id) DESC, (SELECT COUNT(*) FROM comment_tile WHERE tile_id = t.tile_id) DESC",nativeQuery = true)
    Optional<List<TileEntity>> getHottestTileEntitiesForDashboardWithTags(List<Long> groupIdList,LocalDateTime endDate,LocalDateTime nowDate,List<Long> tagIdList,int listLength,Pageable pageable);

}
