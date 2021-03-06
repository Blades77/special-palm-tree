package first.iteration.endlesscreation.mapper;

import first.iteration.endlesscreation.Model.GroupDataEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.Model.UserEntity;
import first.iteration.endlesscreation.dto.GroupDataDTO;
import first.iteration.endlesscreation.dto.TagDTO;
import first.iteration.endlesscreation.dto.TileDTO;
import first.iteration.endlesscreation.dto.Update.TileUpdateDTO;
import first.iteration.endlesscreation.dto.create.TileCreateDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class TileMapper {

    public static TileEntity mapCreateToTileEntity(TileCreateDTO tileCreateDTO , GroupDataEntity groupDataEntity, UserEntity userEntity){
        TileEntity tileEntity = new TileEntity();
        tileEntity.setTileTitle(tileCreateDTO.getTileTitle());
        tileEntity.setTileData(tileCreateDTO.getTileData());
        tileEntity.setCreatedAt(LocalDateTime.now());
        tileEntity.setUpdatedAt(LocalDateTime.now());
        tileEntity.setUserEntity(userEntity);
        tileEntity.setGroupDataEntity(groupDataEntity);
        return tileEntity;
    }

    public static TileEntity mapUpdateToTileEntity(TileUpdateDTO tileUpdateDTO, TileEntity tileEntity){
        tileEntity.setTileTitle(tileUpdateDTO.getTileTitle());
        tileEntity.setTileData(tileUpdateDTO.getTileData());
        tileEntity.setUpdatedAt(LocalDateTime.now());
        return tileEntity;
    }



    public static TileDTO mapToTileDTO(TileEntity tileEntity, Long groupDataId, Map<String, String> tags,Integer likesCount,Boolean isUserLikedTile,GroupDataDTO groupDataDTO, Boolean isTagsNotEmpty, Integer commentsCount, Boolean savedTile){
        TileDTO tileDTO = new TileDTO();
        tileDTO.setTileId(tileEntity.getTileId());
        tileDTO.setTileTitle(tileEntity.getTileTitle());
        tileDTO.setTileData(tileEntity.getTileData());
        tileDTO.setOwnerUserId(tileEntity.getUserEntity().getAppUserId());
        tileDTO.setCreatedAt(tileEntity.getCreatedAt());
        tileDTO.setUpdatedAt(tileEntity.getUpdatedAt());
        tileDTO.setGroupId(groupDataId);
        tileDTO.setTags(tags);
        tileDTO.setLikesCount(likesCount);
        tileDTO.setTileLikedByTheUser(isUserLikedTile);
        tileDTO.setOwnerUserName(tileEntity.getUserEntity().getAppUserName());
        tileDTO.setGroupName(groupDataDTO.getGroupName());
        tileDTO.setGroupImageLink(groupDataDTO.getImageLink());
        tileDTO.setTagsNotEmpty(isTagsNotEmpty);
        tileDTO.setCommentsCount(commentsCount);
        tileDTO.setUserSavedTile(savedTile);
        return tileDTO;
    }

}
