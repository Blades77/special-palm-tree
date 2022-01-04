package first.iteration.endlesscreation.service;


import first.iteration.endlesscreation.Model.GroupDataEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.Model.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final GroupDataService groupDataService;
    private final UserService userService;
    private final TileService tileService;

    public AuthService(GroupDataService groupDataService, UserService userService, TileService tileService) {
        this.groupDataService = groupDataService;
        this.userService = userService;
        this.tileService = tileService;

    }

    public boolean isUserOwnerOfTile(String userName,Long tileId){
        UserEntity userEntity = userService.getUserEntityByName(userName);
        TileEntity tileEntity = tileService.getTileEntityById(tileId);
        return userEntity == tileEntity.getUserEntity();
    }
    public boolean isUserInTileGroup(String userName,Long tileId){
        UserEntity userEntity = userService.getUserEntityByName(userName);
        List<GroupDataEntity> groupDataEntityList = groupDataService.findByUserEntity(userEntity);
        TileEntity tileEntity = tileService.getTileEntityById(tileId);
        GroupDataEntity groupDataEntity = groupDataService.findByTileEntity(tileEntity);
        return groupDataEntityList.contains(groupDataEntity);

    }
    public boolean isTailInPublicGroup(Long tileId){
        TileEntity tileEntity = tileService.getTileEntityById(tileId);
        GroupDataEntity groupDataEntity = groupDataService.findByTileEntity(tileEntity);
        return groupDataEntity.getGroupType().equals("Public");
    }
//    public boolean isUserModOrOwnerOfGroup(){}
}
