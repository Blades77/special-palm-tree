package first.iteration.endlesscreation.repository;

import first.iteration.endlesscreation.Model.GroupDataEntity;

import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupDataRepository extends JpaRepository<GroupDataEntity, Long> {
    Optional<GroupDataEntity> getGroupDataEntityByTiles(TileEntity tileEntity);
    Optional<List<GroupDataEntity>> getGroupDataEntityByUsers(UserEntity userEntity);

    @Query(value="SELECT app_user_id FROM user_group WHERE app_user_id = :userId AND group_id = :groupId AND (position =  'MEMBER' OR  position = 'MOD');" ,nativeQuery = true)
    Boolean checkIfUserCanOperateTile(@Param("userId") Long userId, @Param("groupId") Long groupId);

    @Query(value="SELECT group_id FROM group_data WHERE group_type = 'Public'" ,nativeQuery = true)
    List<Long> getPublicGroupsId();

    @Query(value="SELECT group_id FROM group_data WHERE group_type = 'Public'\n" +
            "UNION \n" +
            "SELECT group_id FROM user_group WHERE app_user_id  IN (SELECT app_user_id FROM app_user WHERE app_user_name = :userName )" ,nativeQuery = true)
    List<Long> getPublicAndUserGroupsId(@Param("userName") String userName);


    @Query(value="SELECT group_id FROM user_group WHERE app_user_id  IN (SELECT app_user_id FROM app_user WHERE app_user_name = :userName )" ,nativeQuery = true)
    List<Long> getUserGroupsId(@Param("userName") String userName);
}
