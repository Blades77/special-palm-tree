package first.iteration.endlesscreation.dto;

public class TileDTO {
    private Long tileId;
    private String tileTitle;
    private String tileData;
    private Long ownerUserId;
    private Long groupId;

    public TileDTO(){}

    public TileDTO(Long tileId, String tileTitle, String tileData, Long ownerUserId, Long groupId){
        this.tileId = tileId;
        this.tileTitle = tileTitle;
        this.tileData = tileData;
        this.ownerUserId = ownerUserId;
        this.groupId = groupId;
    }

    public Long getTileId() {
        return tileId;
    }

    public void setTileId(Long tileId) {
        this.tileId = tileId;
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
