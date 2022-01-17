package first.iteration.endlesscreation.dto;

public class GroupDataDTO {

    private Long groupId;
    private String groupName;
    private String groupType;
    private String imageLink;

    public GroupDataDTO(Long groupId, String groupName, String groupType,String imageLink){
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupType = groupType;
        this.imageLink = imageLink;
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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
