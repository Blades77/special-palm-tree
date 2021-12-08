package first.iteration.endlesscreation.mapper;

import first.iteration.endlesscreation.Model.GroupDataEntity;
import first.iteration.endlesscreation.dto.GroupDataDTO;

public class GroupDataMapper {

    public static GroupDataDTO mapToGroupDataDTO(GroupDataEntity groupDataEntity){
        GroupDataDTO groupDataDTO = new GroupDataDTO();
        groupDataDTO.setGroupId(groupDataEntity.getGroupId());
        groupDataDTO.setGroupName(groupDataEntity.getGroupName());
        groupDataDTO.setGroupType(groupDataEntity.getGroupType());
        return groupDataDTO;
    }

}
