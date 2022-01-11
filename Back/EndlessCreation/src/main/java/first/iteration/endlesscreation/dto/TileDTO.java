package first.iteration.endlesscreation.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class TileDTO {
    private Long tileId;
    private String tileTitle;
    private String tileData;
    private Long ownerUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long groupId;
    private Map<String, String> tags;
    private Integer likesCount;
    private boolean isTileLikedByTheUser;

    public TileDTO(){}

    public TileDTO(Long tileId, String tileTitle, String tileData, Long ownerUserId, LocalDateTime createdAt, LocalDateTime updatedAt, Long groupId,Map<String, String> tags,Integer likesCount, boolean isTileLikedByTheUser ){
        this.tileId = tileId;
        this.tileTitle = tileTitle;
        this.tileData = tileData;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ownerUserId = ownerUserId;
        this.groupId = groupId;
        this.tags = tags;
        this.likesCount = likesCount;
        this.isTileLikedByTheUser = isTileLikedByTheUser;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public boolean isTileLikedByTheUser() {
        return isTileLikedByTheUser;
    }

    public void setTileLikedByTheUser(boolean tileLikedByTheUser) {
        isTileLikedByTheUser = tileLikedByTheUser;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }
}
