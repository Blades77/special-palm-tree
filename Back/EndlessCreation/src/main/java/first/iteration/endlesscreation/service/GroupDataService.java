package first.iteration.endlesscreation.service;


import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.Model.UserEntity;
import first.iteration.endlesscreation.configuration.LoggedUserGetter;
import first.iteration.endlesscreation.dao.GroupDataDAO;
import first.iteration.endlesscreation.dto.GroupDataDTO;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.mapper.GroupDataMapper;
import first.iteration.endlesscreation.repository.GroupDataRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import first.iteration.endlesscreation.Model.GroupDataEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupDataService {

    private final GroupDataRepository groupDataRepository;
    private final GroupDataDAO groupDataDAO;
    private final Environment environment;


    public GroupDataService(GroupDataRepository groupDataRepository, GroupDataDAO groupDataDAO,Environment environment) {

        this.groupDataRepository = groupDataRepository;
        this.groupDataDAO = groupDataDAO;
        this.environment = environment;
    }

    public GroupDataEntity findById(Long id){
        return groupDataDAO.findById(id);

    }

    public List<GroupDataEntity> findByUserEntity(UserEntity userEntity){
        return groupDataDAO.findByUserEntity(userEntity);
    }

    public GroupDataEntity findByTileEntity(TileEntity tileEntity){
        return groupDataDAO.findByTileEntity(tileEntity);
    }

    public GroupDataDTO getGroupDataDTOByTileEntity(TileEntity tileEntity){
        GroupDataEntity groupDataEntity = groupDataDAO.findByTileEntity(tileEntity);
        String imageLink = generateImageLink(groupDataEntity.getImageLink());
        return GroupDataMapper.mapToGroupDataDTO(groupDataEntity,imageLink);
    }

    public Boolean checkIfUserCanOperateTile(Long userId,Long groupId){
        return groupDataDAO.checkIfUserCanOperateTile(userId,groupId);
    }

    public List<GroupDataDTO> getGroupsThatUserIsPartOf(){
        String userName = LoggedUserGetter.getUsser();
        List<Long> groupIdList = getUserGroupsIdList(userName);
        List<GroupDataEntity> groupDataEntityList = groupDataDAO.getGroupsForGroupIdLIst(groupIdList);
        List<GroupDataDTO> groupDataDTOList = new ArrayList<>();
        for(GroupDataEntity groupDataEntity : groupDataEntityList){
            String imageLink = generateImageLink(groupDataEntity.getImageLink());
            groupDataDTOList.add(GroupDataMapper.mapToGroupDataDTO(groupDataEntity,imageLink));
        }
        return groupDataDTOList;
    }

    public List<Long> getPublicGroupsIdList(){
        return groupDataDAO.getPublicGroupsIdList();
    }

    public List<Long> getUserGroupsIdList(String userName){ return  groupDataDAO.getUserGroupsId(userName);}
    public List<Long> getPublicAndUserGroupsIdList(String userName){
        return groupDataDAO.getPublicAndUserGroupsIdList(userName);
    }

    public void saveGroupDataEntity(GroupDataEntity groupDataEntity){
        groupDataDAO.saveGroupDataEntity(groupDataEntity);
    }

    private String generateImageLink(String imageLink){
        if(imageLink != null){
            String bucketName = environment.getRequiredProperty("FIREBASE_BUCKET_NAME");
            return "https://firebasestorage.googleapis.com/v0/b/" + bucketName + "/o/" + imageLink + "?alt=media";
        }else {
            return "";
        }

    }

    public List<GroupDataDTO> getGroups(){
         List<GroupDataEntity> groupDataEntityList = groupDataDAO.getGroups();
         List<GroupDataDTO> groupDataDTOList = new ArrayList<>();
         for(GroupDataEntity groupDataEntity : groupDataEntityList){
             String imageLink = generateImageLink(groupDataEntity.getImageLink());
             groupDataDTOList.add(GroupDataMapper.mapToGroupDataDTO(groupDataEntity,imageLink));
         }
         return groupDataDTOList;
    }

}
