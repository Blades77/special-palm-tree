package first.iteration.endlesscreation.repository;

import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<UserEntity, Long> {



    @Query(value ="SELECT ISNULL((SELECT 1 FROM group_data WHERE group_id IN(SELECT group_id FROM tile WHERE tile_id = :tileId) AND group_type = 'Public'),0)",nativeQuery = true)
    int isTileInPublicGroup(@Param("tileId") Long tileId);

    @Query(value ="SELECT ISNULL((SELECT 1 FROM app_user WHERE app_user_id IN(SELECT owner_user_id FROM tile WHERE tile_id = :tileId) AND app_user_name = :userName),0)",nativeQuery = true)
    int isUserOwnerOfTile(@Param("tileId") Long tileId,@Param("userName") String userName);

    @Query(value ="SELECT ISNULL((SELECT 1 FROM user_group WHERE app_user_id IN(\n" +
            "    SELECT app_user_id FROM app_user WHERE app_user_name = :userName)\n" +
            "    AND group_id IN(\n" +
            "    SELECT group_id FROM tile WHERE tile_id= :tileId),0)",nativeQuery = true)
    int isUserInTileGroup(@Param("tileId") Long tileId,@Param("userName") String userName);

    @Query(value ="SELECT ISNULL((SELECT 1 FROM group_data WHERE group_id = :groupId AND group_type = 'Public'),0)",nativeQuery = true)
    int isGroupPublic(@Param("groupId") Long groupId);

    @Query(value ="SELECT ISNULL((SELECT 1 FROM user_group WHERE group_id = :groupId AND app_user_id IN(SELECT app_user_id FROM app_user WHERE app_user_name= :userName),0)",nativeQuery = true)
    int isUserInGroup(@Param("groupId") Long groupId,@Param("userName") String userName);

    @Query(value ="is user mod or is user owner\n" +
            "SELECT ISNULL((SELECT 1 FROM user_group WHERE app_user_id IN(\n" +
            "    SELECT app_user_id FROM app_user WHERE app_user_name = :userName)\n" +
            "    AND group_id IN(\n" +
            "    SELECT group_id FROM tile WHERE tile_id= :tileId)\n" +
            "    AND (position = 'MOD' OR position = 'OWNER'),0)",nativeQuery = true)
    int isUserOwnerOfGroupTile(@Param("tileId") Long tileId,@Param("userName") String userName);

    @Query(value ="SELECT ISNULL((SELECT 1 FROM user_group WHERE app_user_id IN(\n" +
            "    SELECT app_user_id FROM app_user WHERE app_user_name = :userName)\n" +
            "    AND group_id IN(\n" +
            "    SELECT group_id FROM tile WHERE tile_id IN (\n" +
            "        SELECT tile_id FROM comment_tile WHERE comment_id = :commentId))\n" +
            "    AND (position = 'MOD' OR position = 'OWNER'),0);",nativeQuery = true)
    int isUserInCommentGroupAndHavePermission(@Param("commentId") Long commentId,@Param("userName") String userName);



}
