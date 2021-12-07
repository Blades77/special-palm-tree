package first.iteration.endlesscreation.service;

import first.iteration.endlesscreation.Model.GroupDataEntity;
import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.dao.GroupDataDAO;
import first.iteration.endlesscreation.dao.TagDAO;
import first.iteration.endlesscreation.dao.TileDAO;
import first.iteration.endlesscreation.dto.GroupDataDTO;
import first.iteration.endlesscreation.dto.TagDTO;
import first.iteration.endlesscreation.dto.TileDTO;
import first.iteration.endlesscreation.dto.Update.TileUpdateDTO;
import first.iteration.endlesscreation.dto.create.TagCreateDTO;
import first.iteration.endlesscreation.dto.create.TileCreateDTO;
import first.iteration.endlesscreation.repository.TagRepository;
import first.iteration.endlesscreation.repository.TileRepository;
import first.iteration.endlesscreation.repository.GroupDataRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TileService {

    private final TileRepository tileRepository;
    private final TagRepository tagRepository;
    private final GroupDataRepository groupDataRepository;
    private final GroupDataService groupDataService;
    private final TagService tagService;
    private final TileDAO tileDAO;
    private final TagDAO tagDAO;
    private final GroupDataDAO groupDataDAO;

    public TileService(TileRepository tileRepository, GroupDataRepository groupDataRepository, GroupDataService groupDataService, TagRepository tagRepository, TileDAO tileDAO, TagDAO tagDAO,TagService tagService, GroupDataDAO groupDataDAO) {
        this.tileRepository = tileRepository;
        this.groupDataRepository = groupDataRepository;
        this.groupDataService = groupDataService;
        this.tagRepository = tagRepository;
        this.tagService = tagService;
        this.tileDAO = tileDAO;
        this.tagDAO = tagDAO;
        this.groupDataDAO = groupDataDAO;

    }
    public TileDTO getTileById(Long id){
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        GroupDataDTO groupDataDTO = getGroupForTile(tileEntity.getTileId());
        List<TagDTO> tagDTOList = getTagsForTile(tileEntity.getTileId());
        return mapToTileDTO(tileEntity, groupDataDTO ,tagDTOList);
    }

    public List<TileDTO> getTiles() {
        List<TileEntity> tileEntityList = tileRepository.findAll();
        return mapToTileDTOList(tileEntityList);
    }

    public List<TileDTO> getTilesByGroupId(long id) {
        GroupDataEntity groupDataEntity = groupDataDAO.findById(id);
        List<TileEntity> tileEntityList = tileRepository.getTileEntityByGroupDataEntity(groupDataEntity);
        return mapToTileDTOList(tileEntityList);
    }

    public List<TileDTO> getTilesIncludingAllTagIdList(String searchType,List<Long> tagIdList,Long groupId){
        List<TileEntity> tileEntityList = new ArrayList<>();
        if(searchType.equals("all")) {
            int listLength = tagIdList.size();
            tileEntityList = tileRepository.getTileEntityByTagIdList(tagIdList, listLength,groupId);
        }
        else if(searchType.equals("one")){
            tileEntityList = tileRepository.getTileEntityByAtLeastOneTagIdList(tagIdList, groupId);
            }
        return mapToTileDTOList(tileEntityList);
        }

    public List<TileDTO> getTilesBySearchTileTitle(String tileTitleSearch){
        List<TileEntity> tileEntityList = tileRepository.searchTileTitleByParam(tileTitleSearch);
        return mapToTileDTOList(tileEntityList);
    }


    public void createTile(TileCreateDTO tileCreateDTO){
        TileEntity tileEntity = mapCreateToTileEntity(tileCreateDTO);
        List<TagEntity> tagEntityList = tagService.getTagsEntityListByTagCreateDTOList(tileCreateDTO.getTagCreateDTOList());
        for(TagEntity tagEntity : tagEntityList){
            tileEntity.addTag(tagEntity);
        }
        tileRepository.save(tileEntity);

    }

    public void editTile(TileUpdateDTO tileUpdateDTO){
        Long id = tileUpdateDTO.getTileId();
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        tileRepository.save(mapUpdateToTileEntity(tileUpdateDTO,tileEntity));
    }

    public void deleteTile(Long id){
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

    public GroupDataDTO getGroupForTile(Long id){
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        GroupDataEntity groupDataEntity = groupDataRepository.getGroupDataEntityByTiles(tileEntity);
        return mapToGroupDataDTO(groupDataEntity);
    }

    public List<TagDTO> getTagsForTile(Long id){
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        List<TagEntity> tagEntityList = tagRepository.getTagEntityByTiles(tileEntity);
        List<TagDTO> tagDTOList = new ArrayList<>();
        for(TagEntity tagEntity : tagEntityList){
            tagDTOList.add(tagService.mapToTagDTO(tagEntity));

        }
        return tagDTOList;
    }

    public void addTagToTile(Long tileId, TagCreateDTO tagCreateDTO){
        TileEntity tileEntity = tileDAO.getTileEntity(tileId);
        TagEntity tagEntity = tagService.findOrCreateTag(tagCreateDTO);
        tileEntity.addTag(tagEntity);
        tileRepository.save(tileEntity);
    }

    public void deleteTagFromTile(Long tileId, Long tagId){
        TileEntity tileEntity = tileDAO.getTileEntity(tileId);
        TagEntity tagEntity = tagDAO.getTagEntityById(tagId);
        tileEntity.removeTag(tagEntity);
        tileRepository.save(tileEntity);
    }

    private TileEntity mapUpdateToTileEntity(TileUpdateDTO tileUpdateDTO, TileEntity tileEntity){
        tileEntity.setTileTitle(tileUpdateDTO.getTileTitle());
        tileEntity.setTileData(tileUpdateDTO.getTileData());
        tileEntity.setUpdatedAt(LocalDateTime.now());
        return tileEntity;
    }

    private static TileDTO mapToTileDTO(TileEntity tileEntity, GroupDataDTO groupDataDTO, List<TagDTO> tagDTOList){
        TileDTO tileDTO = new TileDTO();
        tileDTO.setTileId(tileEntity.getTileId());
        tileDTO.setTileTitle(tileEntity.getTileTitle());
        tileDTO.setTileData(tileEntity.getTileData());
        tileDTO.setOwnerUserId(tileEntity.getOwnerUserId());
        tileDTO.setCreatedAt(tileEntity.getCreatedAt());
        tileDTO.setUpdatedAt(tileEntity.getUpdatedAt());
        tileDTO.setGroupDataDTO(groupDataDTO);
        tileDTO.setTagDTOList(tagDTOList);
        return tileDTO;
    }

    private List<TileDTO> mapToTileDTOList(List<TileEntity> tileEntityList){
        List<TileDTO> tileDTOList = new ArrayList<>();
        for(TileEntity tileEntity : tileEntityList){
            GroupDataDTO groupDataDTO = getGroupForTile(tileEntity.getTileId());
            List<TagDTO> tagDTOList = getTagsForTile(tileEntity.getTileId());
            tileDTOList.add(mapToTileDTO(tileEntity, groupDataDTO,tagDTOList));
        }
        return tileDTOList;
    }

    private TileEntity mapCreateToTileEntity(TileCreateDTO tileCreateDTO){
        TileEntity tileEntity = new TileEntity();
        tileEntity.setTileTitle(tileCreateDTO.getTileTitle());
        tileEntity.setTileData(tileCreateDTO.getTileData());
        tileEntity.setCreatedAt(LocalDateTime.now());
        tileEntity.setUpdatedAt(LocalDateTime.now());
        tileEntity.setOwnerUserId(tileCreateDTO.getOwnerUserId());
        tileEntity.setGroupDataEntity(groupDataService.findById(tileCreateDTO.getGroupDataDTO().getGroupId()));
        return tileEntity;
    }

    private TileEntity mapToTileEntity(TileDTO tileDTO,TileEntity tileEntity){
        tileEntity.setTileTitle(tileDTO.getTileTitle());
        tileEntity.setTileData(tileDTO.getTileData());
        tileEntity.setOwnerUserId(tileDTO.getOwnerUserId());
        tileEntity.setGroupDataEntity(groupDataService.findById(tileDTO.getGroupDataDTO().getGroupId()));
        return tileEntity;
    }

    private GroupDataDTO mapToGroupDataDTO(GroupDataEntity groupDataEntity){
        GroupDataDTO groupDataDTO = new GroupDataDTO();
        groupDataDTO.setGroupId(groupDataEntity.getGroupId());
        groupDataDTO.setGroupName(groupDataEntity.getGroupName());
        groupDataDTO.setGroupType(groupDataEntity.getGroupType());
        return groupDataDTO;
    }


//    private TagDTO mapToTagDTO(TagEntity tagEntity){
//        TagDTO tagDTO = new TagDTO();
//        tagDTO.setTagId(tagEntity.getTagId());
//        tagDTO.setTagName(tagEntity.getTagName());
//        tagDTO.setTagColor(tagEntity.getTagColor());
//        return tagDTO;
//    }
}

