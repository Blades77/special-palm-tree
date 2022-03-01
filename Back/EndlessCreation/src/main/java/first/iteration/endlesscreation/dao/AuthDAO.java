package first.iteration.endlesscreation.dao;

import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.AuthRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthDAO {
    private final AuthRepository authRepository;

    public AuthDAO(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public boolean isTileInPublicGroup(Long tileId){
        return authRepository.isTileInPublicGroup(tileId) == 1;
    }

    public boolean isUserOwnerOfTile(Long tileId,String userName){
        return authRepository.isUserOwnerOfTile(tileId,userName) == 1;
    }

    public boolean isUserInTileGroup(Long tileId, String userName){
        return  authRepository.isUserInTileGroup(tileId,userName) == 1;
    }

    public boolean isGroupPublic(Long groupId){
        return authRepository.isGroupPublic(groupId) == 1;
    }

    public boolean isUserInGroup(Long groupId,String userName){
        return  authRepository.isUserInGroup(groupId,userName) == 1;
    }

    public boolean isUserOwnerOfGroupTile(Long tileId,String userName){
        return authRepository.isUserOwnerOfGroupTile(tileId,userName) == 1;
    }

    public boolean isUserInCommentGroupAndHavePermission(Long comment_id,String userName){
        return authRepository.isUserInCommentGroupAndHavePermission(comment_id,userName) == 1;
    }

    public boolean isUserOwneOfGroup(Long groupId,String userName){
        return authRepository.isUserOwnerOfGroup(groupId,userName) == 1;
    }



}
