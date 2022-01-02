package first.iteration.endlesscreation.dao;

import first.iteration.endlesscreation.Model.GroupDataEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.TileRepository;
import first.iteration.endlesscreation.repository.CommentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

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

    public List<TileEntity> getTileEntityByTagIdList(List<Long> tagIdList, int listLength,Long groupId){
        return  tileRepository.getTileEntityByTagIdList(tagIdList,listLength,groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find tile by provided tags"));
    }

    public List<TileEntity> getTileEntityByAtLeastOneTagIdList(List<Long> tagIdList,Long groupId){
        return  tileRepository.getTileEntityByAtLeastOneTagIdList(tagIdList,groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find tile by provided tags"));
    }

    public List<TileEntity> searchTileTitleByParam(String tileTitleSearch){
        return  tileRepository.searchTileTitleByParam(tileTitleSearch)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find  any tile with matching param in title"));
    }

    public List<TileEntity> getTilesWithSort(Sort sort){
        return tileRepository.findAll(sort);
    }
    public List<TileEntity> getTilesByGroupDataEnityWithSort(GroupDataEntity groupDataEntity,Sort sort){
        return tileRepository.getTileEntityByGroupDataEntity(groupDataEntity,sort)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find  any tile in provided group"));
    }





}
