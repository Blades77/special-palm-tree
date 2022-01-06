package first.iteration.endlesscreation.service;

import first.iteration.endlesscreation.Model.*;
import first.iteration.endlesscreation.dao.TagDAO;
import first.iteration.endlesscreation.dao.TileDAO;
import first.iteration.endlesscreation.dto.*;
import first.iteration.endlesscreation.dto.Update.TileUpdateDTO;
import first.iteration.endlesscreation.dto.create.TagCreateDTO;
import first.iteration.endlesscreation.dto.create.TileCreateDTO;
import first.iteration.endlesscreation.exception.InvalidPathVariableExpection;
import first.iteration.endlesscreation.mapper.TileMapper;
import first.iteration.endlesscreation.repository.CommentRepository;
import first.iteration.endlesscreation.repository.TagRepository;
import first.iteration.endlesscreation.repository.TileRepository;
import first.iteration.endlesscreation.repository.GroupDataRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TileService {

    private final TileRepository tileRepository;
    private final TagRepository tagRepository;
    private final GroupDataRepository groupDataRepository;
    private final CommentRepository commentRepository;
    private final GroupDataService groupDataService;
    private final UserService userService;
    private final TagService tagService;
    private final TileDAO tileDAO;
    private final TagDAO tagDAO;

    public TileService(TileRepository tileRepository, GroupDataRepository groupDataRepository, GroupDataService groupDataService, TagRepository tagRepository, TileDAO tileDAO, TagDAO tagDAO,TagService tagService, CommentRepository commentRepository,UserService userService) {
        this.tileRepository = tileRepository;
        this.groupDataRepository = groupDataRepository;
        this.commentRepository = commentRepository;
        this.groupDataService = groupDataService;
        this.tagRepository = tagRepository;
        this.tagService = tagService;
        this.tileDAO = tileDAO;
        this.tagDAO = tagDAO;
        this.userService = userService;

    }

    public TileEntity getTileEntityById(Long id){
       return tileDAO.getTileEntity(id);
    }

    public TileDTO getFullTileById(Long id){
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        GroupDataDTO groupDataDTO = groupDataService.getGroupDataDTOByTileEntity(tileEntity);
        Map<String, String> tags = tagService.getTagsMapForTile(tileEntity);
        return TileMapper.mapToTileDTO(tileEntity, groupDataDTO.getGroupId() ,tags);
    }

    public List<TileDTO> getTiles() { //tu będzie paginacja raczej
        List<TileEntity> tileEntityList = tileRepository.findAll();
        return mapToTileDTOList(tileEntityList);
    }

    public List<TileDTO> getTilesByGroupId(Long id) {
        GroupDataEntity groupDataEntity = groupDataService.findById(id);
        List<TileEntity> tileEntityList = tileDAO.getTileEntityByGroupDataEntity(groupDataEntity);
        return mapToTileDTOList(tileEntityList);
    }

    public List<TileDTO> getTilesIncludingAllTagIdList(String searchType,List<Long> tagIdList,Long groupId){
        List<TileEntity> tileEntityList = new ArrayList<>();
        if(searchType.equals("all")) {
            int listLength = tagIdList.size();
            tileEntityList = tileDAO.getTileEntityByTagIdList(tagIdList, listLength,groupId);
        }
        else if(searchType.equals("one")){
            tileEntityList = tileDAO.getTileEntityByAtLeastOneTagIdList(tagIdList, groupId);
            }
        else{
            throw new InvalidPathVariableExpection("Invalid search type, should be \"all\" or \"one\".");
        }
        return mapToTileDTOList(tileEntityList);
        }

    public List<TileDTO> getTilesBySearchTileTitle(Long groupId,String tileTitleSearch){
        List<TileEntity> tileEntityList = tileDAO.searchTileTitleByParam(tileTitleSearch);
        return mapToTileDTOList(tileEntityList);
    }


    public List<TileDTO> getTiles(Long groupId,String order) {
        List<TileEntity> tileEntityList = new ArrayList<>();
        GroupDataEntity groupDataEntity = new GroupDataEntity();
        String sortOrder = "";
        if (order.equals("asc")) {
            sortOrder = "ASC";
        } else if (order.equals("desc")) {
            sortOrder = "DESC";
        } else {
            throw new InvalidPathVariableExpection("Invalid search type, should be \"asc\" or \"desc\".");
        }
        if (groupId > 0) {
            groupDataEntity = groupDataService.findById(groupId);
            tileEntityList = tileDAO.getTilesByGroupDataEnityWithSort(groupDataEntity, Sort.by(Sort.Direction.valueOf(sortOrder), "createdAt"));
        } else {
            tileEntityList = tileDAO.getTilesWithSort(Sort.by(Sort.Direction.valueOf(sortOrder), "createdAt"));
        }
        return mapToTileDTOList(tileEntityList);
    }



    public void createTile(TileCreateDTO tileCreateDTO,Long groupId){
        GroupDataEntity groupDataEntity = groupDataService.findById(groupId);
        UserEntity userEntity = userService.getUserEntityById(tileCreateDTO.getOwnerUserId());
        TileEntity tileEntity = TileMapper.mapCreateToTileEntity(tileCreateDTO, groupDataEntity, userEntity);
        List<TagEntity> tagEntityList = tagService.getTagsEntityListByTagCreateDTOList(tileCreateDTO.getTagCreateDTOList());
        for(TagEntity tagEntity : tagEntityList){
            tileEntity.addTag(tagEntity);
        }
        tileRepository.save(tileEntity);
    }

    public void editTile(TileUpdateDTO tileUpdateDTO){
        Long id = tileUpdateDTO.getTileId();
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        tileRepository.save(TileMapper.mapUpdateToTileEntity(tileUpdateDTO,tileEntity));
    }

    public void deleteTile(Long id){
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        List<TagEntity> tagEntityList = tagService.getTagEntityListByTile(tileEntity);
        if(tagEntityList.isEmpty() == false) {
            for (TagEntity tagEntity : tagEntityList) {
                tileEntity.removeTag(tagEntity);
            }
        }
        tileRepository.save(tileEntity);
        tileRepository.delete(tileEntity);
    }

    public List<TagDTO> getTagsForTile(Long id){
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        return tagService.getTagsForTile(tileEntity);
    }

    public List<TagEntity> getTagEntitiesForTile(Long id){
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        return tagService.getTagEntityListByTile(tileEntity);
    }

    public void addTagToTile(Long tileId, TagCreateDTO tagCreateDTO){
        TileEntity tileEntity = tileDAO.getTileEntity(tileId);
        TagEntity tagEntity = tagService.findOrCreateTag(tagCreateDTO);
        tileEntity.addTag(tagEntity);
        tileRepository.save(tileEntity);
    }

    public void deleteTagFromTile(Long tileId, Long tagId){
        TileEntity tileEntity = tileDAO.getTileEntity(tileId);
        TagEntity tagEntity = tagService.getTagEntityById(tagId);
        tileEntity.removeTag(tagEntity);
        tileRepository.save(tileEntity);
    }


    private List<TileDTO> mapToTileDTOList(List<TileEntity> tileEntityList){
        List<TileDTO> tileDTOList = new ArrayList<>();
        for(TileEntity tileEntity : tileEntityList){
            GroupDataDTO groupDataDTO = groupDataService.getGroupDataDTOByTileEntity(tileEntity);
            Map<String, String> tags = tagService.getTagsMapForTile(tileEntity);
            tileDTOList.add(TileMapper.mapToTileDTO(tileEntity, groupDataDTO.getGroupId(),tags));
        }
        return tileDTOList;
    }

//    private TileEntity mapCreateToTileEntity(TileCreateDTO tileCreateDTO){
//        TileEntity tileEntity = new TileEntity();
//        tileEntity.setTileTitle(tileCreateDTO.getTileTitle());
//        tileEntity.setTileData(tileCreateDTO.getTileData());
//        tileEntity.setCreatedAt(LocalDateTime.now());
//        tileEntity.setUpdatedAt(LocalDateTime.now());
//        tileEntity.setOwnerUserId(tileCreateDTO.getOwnerUserId());
//        tileEntity.setGroupDataEntity(groupDataService.findById(tileCreateDTO.getGroupDataDTO().getGroupId()));
//        return tileEntity;
//    }

//    private TileEntity mapToTileEntity(TileDTO tileDTO,TileEntity tileEntity){
//        tileEntity.setTileTitle(tileDTO.getTileTitle());
//        tileEntity.setTileData(tileDTO.getTileData());
//        tileEntity.setOwnerUserId(tileDTO.getOwnerUserId());
//        tileEntity.setGroupDataEntity(groupDataService.findById(tileDTO.getGroupDataDTO().getGroupId()));
//        return tileEntity;
//    }

//    private GroupDataDTO mapToGroupDataDTO(GroupDataEntity groupDataEntity){
//        GroupDataDTO groupDataDTO = new GroupDataDTO();
//        groupDataDTO.setGroupId(groupDataEntity.getGroupId());
//        groupDataDTO.setGroupName(groupDataEntity.getGroupName());
//        groupDataDTO.setGroupType(groupDataEntity.getGroupType());
//        return groupDataDTO;
//    }


//    private TagDTO mapToTagDTO(TagEntity tagEntity){
//        TagDTO tagDTO = new TagDTO();
//        tagDTO.setTagId(tagEntity.getTagId());
//        tagDTO.setTagName(tagEntity.getTagName());
//        tagDTO.setTagColor(tagEntity.getTagColor());
//        return tagDTO;
//    }
}

