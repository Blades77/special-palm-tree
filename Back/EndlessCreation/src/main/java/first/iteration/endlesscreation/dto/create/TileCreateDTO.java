package first.iteration.endlesscreation.dto.create;

public class TileCreateDTO {
    private String tileTitle;
    private String tileData;
    private Long ownerUserId;
    private Long groupId;

    public TileCreateDTO(){}

    public TileCreateDTO(String tileTitle, String tileData, Long ownerUserId, Long groupId){
        this.tileTitle = tileTitle;
        this.tileData = tileData;
        this.ownerUserId = ownerUserId;
        this.groupId = groupId;
    }

    public String getTileTitle() {
        return tileTitle;
    }

    public void setTileTitle(String tileTitle) {
        this.tileTitle = tileTitle;
    }

    public String getTileData() {
        return tileData;
    }

    public void setTileData(String tileData) {
        this.tileData = tileData;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
