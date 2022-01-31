package first.iteration.endlesscreation.dao;

import first.iteration.endlesscreation.Model.GroupDataEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.TileRepository;
import first.iteration.endlesscreation.repository.CommentRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TileDAO {
    private final TileRepository tileRepository;
    private final CommentRepository commentRepository;

    public TileDAO(TileRepository tileRepository, CommentRepository commentRepository) {

        this.tileRepository = tileRepository;
        this.commentRepository = commentRepository;
    }

    public TileEntity getTileEntity(Long id){
        return  tileRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cannot find specified tile"));
    }

    public List<TileEntity> getTileEntityByGroupDataEntity(GroupDataEntity groupDataEntity){
        return  tileRepository.getTileEntityByGroupDataEntity(groupDataEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find tile by specific group"));
    }

    public List<TileEntity> getTileEntityByTagIdList(List<Long> tagIdList, int listLength,Sort sort,Long groupId){
        return  tileRepository.getTileEntityByTagIdList(tagIdList,listLength,groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find tile by provided tags"));
    }

    public List<TileEntity> getTileEntityByAtLeastOneTagIdList(List<Long> tagIdList,Sort sort,Long groupId){
        return  tileRepository.getTileEntityByAtLeastOneTagIdList(tagIdList,groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find tile by provided tags"));
    }


    public List<TileEntity> getTileEntityByTagIdListAndGroupIdList(List<Long> tagIdList, int listLength,Sort sort,List<Long> groupIdList){
        return  tileRepository.getTileEntityByTagIdListAndGroupIdList(tagIdList,listLength,groupIdList)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find tile by provided tags"));
    }

    public List<TileEntity> getTileEntityByAtLeastOneTagIdListAndGroupIdList(List<Long> tagIdList,Sort sort,List<Long> groupIdList){
        return  tileRepository.getTileEntityByAtLeastOneTagIdListAndGroupIdList(tagIdList,groupIdList)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find tile by provided tags"));
    }

    public List<TileEntity> searchTileTitleByParam(String tileTitleSearch,List<Long> groupIdList){
        return  tileRepository.searchTileTitleByParam(tileTitleSearch, groupIdList)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find  any tile with matching param in title"));
    }

    public List<TileEntity> searchTileEntitiesByGroupDataId(String tileTitleSearch, Long groupId){
        return tileRepository.getTileEntitiesByGroupDataId(tileTitleSearch,groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find  any tile with matching param in title"));
    }

    public List<TileEntity> getTileEntitiesByGroupDataIdList(List<Long> groupIdList, LocalDateTime nowDate, LocalDateTime endDate, Pageable pageable){
        return tileRepository.getTileEntitiesByGroupDataIdList(groupIdList,nowDate,endDate,pageable)
                .orElseThrow(() -> new ResourceNotFoundException("Something went wrong"));
    }
    public List<TileEntity> getTilesByGroupDataEnityWithSort(GroupDataEntity groupDataEntity,Pageable pageable){
        return tileRepository.getTileEntityByGroupDataEntity(groupDataEntity,pageable)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find  any tile in provided group"));
    }

    public Integer getLikesForTile(Long tileId){
        return tileRepository.getLikesForTile(tileId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find  any tile with matching param in title"));
    }

    public void deleteLikeFromTile(Long tileId,String userName){
        tileRepository.deleteLikeFromTile(tileId,userName);
    }

    public void addLikeToTile(Long tileId,String userName){
        tileRepository.addLikeToTile(tileId,userName);
    }

    public boolean isUserLikedTile(Long tileId,String userName){
        return tileRepository.isUserLikedTile(tileId,userName) == 1;
    }

    public Integer getCommentsCountForTile(Long tileId){
        return tileRepository.getCommentsCountForTile(tileId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find  any tile with matching param in title"));
    }

    public void unsaveTileFromTileSave(Long tileId,String userName){
        tileRepository.unsaveTileForSaveTile(tileId,userName);
    }

    public void saveTileToTileSave(Long tileId,String userName){
        tileRepository.saveTileToSaveTile(tileId,userName);
    }

    public boolean isUserSavedTile(Long tileId,String userName){
        return tileRepository.isUserSavedTile(tileId,userName) == 1;
    }

    public List<TileEntity> getTileEntitiesByGroupIdListSortByLikeDESC(List<Long> groupIdList,LocalDateTime nowDate, LocalDateTime endDate,Pageable pageable){
        return tileRepository.getTileEntitiesByGroupIdListSortByLikeDESC(groupIdList,nowDate,endDate,pageable)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find  any tile in provided group"));
    }

    public List<TileEntity> getTileEntitiesByGroupIdListSortByLikeASC(List<Long> groupIdList,LocalDateTime nowDate, LocalDateTime endDate,Pageable pageable){
        return tileRepository.getTileEntitiesByGroupIdListSortByLikeASC(groupIdList,nowDate,endDate,pageable)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find  any tile in provided group"));
    }



}
