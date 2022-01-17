package first.iteration.endlesscreation.mapper;

import first.iteration.endlesscreation.Model.GroupDataEntity;
import first.iteration.endlesscreation.dto.GroupDataDTO;

public class GroupDataMapper {

    public static GroupDataDTO mapToGroupDataDTO(GroupDataEntity groupDataEntity,String imageLink){
        GroupDataDTO groupDataDTO = new GroupDataDTO();
        groupDataDTO.setGroupId(groupDataEntity.getGroupId());
        groupDataDTO.setGroupName(groupDataEntity.getGroupName());
        groupDataDTO.setGroupType(groupDataEntity.getGroupType());
        groupDataDTO.setImageLink(imageLink);
        return groupDataDTO;
    }

}
