package first.iteration.endlesscreation.dto;

public class GroupDataDTO {

    private Long groupId;
    private String groupName;
    private String groupType;

    public GroupDataDTO(Long groupId, String groupName, String groupType){
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupType = groupType;
    }

    public GroupDataDTO(){}

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }
}
