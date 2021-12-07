package first.iteration.endlesscreation.dto.create;

import first.iteration.endlesscreation.dto.GroupDataDTO;

import java.time.LocalDateTime;
import java.util.List;

public class TileCreateDTO {
    private String tileTitle;
    private String tileData;
    private Long ownerUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private GroupDataDTO groupDataDTO;
    private List<TagCreateDTO> tagCreateDTOList;

    public TileCreateDTO(){}

    public TileCreateDTO(String tileTitle, String tileData, Long ownerUserId, LocalDateTime createdAt, LocalDateTime updatedAt, GroupDataDTO groupDataDTO, List<TagCreateDTO> tagCreateDTOList){
        this.tileTitle = tileTitle;
        this.tileData = tileData;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ownerUserId = ownerUserId;
        this.groupDataDTO = groupDataDTO;
        this.tagCreateDTOList = tagCreateDTOList;

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

    public GroupDataDTO getGroupDataDTO() {
        return groupDataDTO;
    }

    public void setGroupDataDTO(GroupDataDTO groupDataDTO) {
        this.groupDataDTO = groupDataDTO;
    }

    public List<TagCreateDTO> getTagCreateDTOList() {
        return tagCreateDTOList;
    }

    public void setTagCreateDTOList(List<TagCreateDTO> tagCreateDTOList) {
        this.tagCreateDTOList = tagCreateDTOList;
    }
}
