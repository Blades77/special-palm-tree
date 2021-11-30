package first.iteration.endlesscreation.service;

import first.iteration.endlesscreation.Model.GroupDataEntity;
import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.dao.TileDAO;
import first.iteration.endlesscreation.dto.GroupDataDTO;
import first.iteration.endlesscreation.dto.TagDTO;
import first.iteration.endlesscreation.dto.TileDTO;
import first.iteration.endlesscreation.dto.create.TileCreateDTO;
import first.iteration.endlesscreation.repository.TagRepository;
import first.iteration.endlesscreation.repository.TileRepository;
import first.iteration.endlesscreation.repository.GroupDataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TileService {

    private final TileRepository tileRepository;
    private final TagRepository tagRepository;
    private final GroupDataRepository groupDataRepository;
    private final GroupDataService groupDataService;
    private final TileDAO tileDAO;

    public TileService(TileRepository tileRepository, GroupDataRepository groupDataRepository, GroupDataService groupDataService, TagRepository tagRepository, TileDAO tileDAO) {
        this.tileRepository = tileRepository;
        this.groupDataRepository = groupDataRepository;
        this.groupDataService = groupDataService;
        this.tagRepository = tagRepository;
        this.tileDAO = tileDAO;

    }
    public TileDTO getTileById(Long id){
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        return mapToTileDTO(tileEntity);
    }

    public List<TileDTO> getTiles() {
        List<TileEntity> tileEntityList = tileRepository.findAll();
        List<TileDTO> tileDTOList = new ArrayList<>();
        for(TileEntity tileEntity : tileEntityList){
            tileDTOList.add(mapToTileDTO(tileEntity));
        }
        return tileDTOList;
    }

    public void createTile(TileCreateDTO tileCreateDTO){
        tileRepository.save(mapCreateToTileEntity(tileCreateDTO));
    }

    public void editTile(TileDTO tileDTO){
        Long id = tileDTO.getTileId();
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        tileRepository.save(mapToTileEntity(tileDTO,tileEntity));
    }

    public void deleTile(Long id){
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        List<TagEntity> tagEntityList = tagRepository.getTagEntityByTiles(tileEntity);
        if(tagEntityList.isEmpty() == false) {
            for (TagEntity tagEntity : tagEntityList) {
                tileEntity.removeTag(tagEntity);
            }
        }
        tileRepository.save(tileEntity);
        tileRepository.delete(tileEntity);
    }

    public GroupDataDTO getGroupsForTile(Long id){
        TileEntity tileEntity = tileRepository.getById(id);
        GroupDataEntity groupDataEntity = groupDataRepository.getGroupDataEntityByTiles(tileEntity);
        return mapToGroupDataDTO(groupDataEntity);
    }

    public List<TagDTO> getTagsForTile(Long id){
        TileEntity tileEntity = tileRepository.getById(id);
        List<TagEntity> tagEntityList = tagRepository.getTagEntityByTiles(tileEntity);
        List<TagDTO> tagDTOList = new ArrayList<>();
        for(TagEntity tagEntity : tagEntityList){
            TagDTO tagDTO = new TagDTO();
            tagDTO.setTagId(tagEntity.getTagId());
            tagDTO.setTagName(tagEntity.getTagName());
            tagDTO.setTagColor(tagEntity.getTagColor());
            tagDTOList.add(tagDTO);

        }
        return tagDTOList;
    }

    public void addTagsToTile(){
        long id = 3;
        TileEntity tileEntity =  tileRepository.getById(id);
        TagEntity tag = new TagEntity();
        tag.setTagName("test1");
        tag.setTagColor("color1");
        tileEntity.addTag(tag);
        tileRepository.save(tileEntity);
    }
    public void deleteTagsForTile(){
        long id = 5;
        TileEntity tileEntity =  tileRepository.getById(id);
        long[] myLongArray = {2, 3,4};
        int i =0;
        while(i< myLongArray.length){
           long id4 = myLongArray[i];
           TagEntity tagEntity = tagRepository.getById(id4);
           tileEntity.removeTag(tagEntity);
           i++;
        }
        tileRepository.save(tileEntity);
    }


    private static TileDTO mapToTileDTO(TileEntity tileEntity){
        TileDTO tileDTO = new TileDTO();
        tileDTO.setTileId(tileEntity.getTileId());
        tileDTO.setTileTitle(tileEntity.getTileTitle());
        tileDTO.setTileData(tileEntity.getTileData());
        tileDTO.setGroupId(tileEntity.getGroupDataEntity().getGroupId());
        return tileDTO;
    }

    private TileEntity mapCreateToTileEntity(TileCreateDTO tileCreateDTO){
        TileEntity tileEntity = new TileEntity();
        tileEntity.setTileTitle(tileCreateDTO.getTileTitle());
        tileEntity.setTileData(tileCreateDTO.getTileData());
        tileEntity.setOwnerUserId(tileCreateDTO.getOwnerUserId());
        tileEntity.setGroupDataEntity(groupDataService.findById(tileCreateDTO.getGroupId()));
        return tileEntity;
    }

    private TileEntity mapToTileEntity(TileDTO tileDTO,TileEntity tileEntity){
        tileEntity.setTileTitle(tileDTO.getTileTitle());
        tileEntity.setTileData(tileDTO.getTileData());
        tileEntity.setOwnerUserId(tileDTO.getOwnerUserId());
        tileEntity.setGroupDataEntity(groupDataService.findById(tileDTO.getGroupId()));
        return tileEntity;
    }

    private GroupDataDTO mapToGroupDataDTO(GroupDataEntity groupDataEntity){
        GroupDataDTO groupDataDTO = new GroupDataDTO();
        groupDataDTO.setGroupId(groupDataEntity.getGroupId());
        groupDataDTO.setGroupName(groupDataEntity.getGroupName());
        groupDataDTO.setGroupType(groupDataEntity.getGroupType());
        return groupDataDTO;
    }
}

