package first.iteration.endlesscreation.filter;


import first.iteration.endlesscreation.Model.GroupDataEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.Model.UserEntity;
import first.iteration.endlesscreation.configuration.LoggedUserGetter;
import first.iteration.endlesscreation.dao.AuthDAO;
import first.iteration.endlesscreation.service.GroupDataService;
import first.iteration.endlesscreation.service.TileService;
import first.iteration.endlesscreation.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthHelper {


    private final AuthDAO authDAO;


    public boolean loggedAccessCheck(String path){
        String[] pathArr = path.split("/");
        if(Pattern.matches("/tile/[0-9]+",path)){
            Long tileId = Long.valueOf(pathArr[2]);
            return authDAO.isTileInPublicGroup(tileId) || authDAO.isUserOwnerOfTile(tileId,LoggedUserGetter.getUsser()) || authDAO.isUserInTileGroup(tileId,LoggedUserGetter.getUsser());
        }else if(Pattern.matches("/tile/create/group/[0-9]+",path) || Pattern.matches("/tile/edit/group/[0-9]+",path)){
            return authDAO.isUserInGroup(Long.valueOf(pathArr[4]),LoggedUserGetter.getUsser());
        }else if(Pattern.matches("/tile/delete/group/[0-9]+/[0-9]+",path)){
            Long tileId = Long.valueOf(pathArr[4]);
            return  authDAO.isUserOwnerOfTile(tileId,LoggedUserGetter.getUsser()) || authDAO.isUserOwnerOfGroupTile(tileId,LoggedUserGetter.getUsser());
        }else if(Pattern.matches("/tile/tag/[0-9]+",path) || Pattern.matches("/tile/tag/[0-9]+/[0-9]+",path)){
            Long tileId = Long.valueOf(pathArr[3]);
            return  authDAO.isUserOwnerOfTile(tileId,LoggedUserGetter.getUsser()) || authDAO.isUserOwnerOfGroupTile(tileId,LoggedUserGetter.getUsser());
        }else if(Pattern.matches("/file/group/[0-9]+",path)){
            Long groupId = Long.valueOf(pathArr[3]);
            return  authDAO.isUserOwneOfGroup(groupId,LoggedUserGetter.getUsser());
        }else{
            return  true;
        }
    }


  public boolean notLoggedAccessCheck(String path){

      if(Pattern.matches("/tiles/group/[0-9]+/.*",path) || Pattern.matches("/tiles/group/[0-9]+/",path) || Pattern.matches("/tile/group/[0-9]+/search=.*",path)){
          String[] pathArr = path.split("/");
          Long groupId = Long.valueOf(pathArr[3]);
          if(groupId == 0){
              return true;
          }else {
              return authDAO.isGroupPublic(groupId);
          }
      }else if(Pattern.matches("/tile/[0-9]+",path)){
          String[] pathArr = path.split("/");
          return authDAO.isTileInPublicGroup(Long.valueOf(pathArr[2]));
      }else{
          return true;
      }
  }

    public boolean tileNotLoggedIn(Long tileId){
//        this.tileEntity = tileService.getTileEntityById(tileId);
//        GroupDataEntity groupDataEntity = groupDataService.findByTileEntity(tileEntity);
//        return groupDataEntity.getGroupType().equals("Public");
        return true;
    }
    private boolean tileLoggedIn(Long tileId){
//        this.userEntity = userService.getUserEntityByName(LoggedUserGetter.getUsser());
//        this.tileEntity = tileService.getTileEntityById(tileId);
//        this.groupDataEntity = groupDataService.findByTileEntity(tileEntity);
//        this.groupDataEntityList = groupDataService.findByUserEntity(userEntity);
//        return groupDataEntity.getGroupType().equals("Public") || userEntity == tileEntity.getUserEntity() || groupDataEntityList.contains(groupDataEntity);
        return true;
    }

    private boolean tileOperation(Long tileId){
//        this.userEntity = userService.getUserEntityByName(LoggedUserGetter.getUsser());
//        this.tileEntity = tileService.getTileEntityById(tileId);
//        this.groupDataEntity = groupDataService.findByTileEntity(tileEntity);
//        this.groupDataEntityList = groupDataService.findByUserEntity(userEntity);
//
//        return userEntity == tileEntity.getUserEntity() || groupDataService.checkIfUserCanOperateTile(userEntity.getAppUserId(),groupDataEntity.getGroupId());
        return true;
    }

    public boolean groupNotLoggedIn(Long groupId){
//        this.groupDataEntity = groupDataService.findById(groupId);
//        return groupDataEntity.getGroupType().equals("Public");
        return true;
    }

    public boolean groupLoggedIn(String userName, Long groupId){
//        this.userEntity = userService.getUserEntityByName(userName);
//        this.groupDataEntityList = groupDataService.findByUserEntity(userEntity);
//        this.groupDataEntity = groupDataService.findByTileEntity(tileEntity);
//        return groupDataEntity.getGroupType().equals("Public") || groupDataEntityList.contains(groupDataEntity);

        return true;
    }



}
