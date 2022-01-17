package first.iteration.endlesscreation.service;

import first.iteration.endlesscreation.Model.*;
import first.iteration.endlesscreation.configuration.LoggedUserGetter;
import first.iteration.endlesscreation.dao.TagDAO;
import first.iteration.endlesscreation.dao.TileDAO;
import first.iteration.endlesscreation.dto.*;
import first.iteration.endlesscreation.dto.Update.TileUpdateDTO;
import first.iteration.endlesscreation.dto.create.TagCreateDTO;
import first.iteration.endlesscreation.dto.create.TileCreateDTO;
import first.iteration.endlesscreation.exception.InvalidPathVariableExpection;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.mapper.TileMapper;
import first.iteration.endlesscreation.repository.CommentRepository;
import first.iteration.endlesscreation.repository.TagRepository;
import first.iteration.endlesscreation.repository.TileRepository;
import first.iteration.endlesscreation.repository.GroupDataRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
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

    public Integer getLikesForTile(Long tileId){
        return tileDAO.getLikesForTile(tileId);
    }

    public void deleteLikeForTile(Long tileId){
        String userName = LoggedUserGetter.getUsser();
        if(userName.equals("anonymousUser")){
            throw new ResourceNotFoundException("Nie masz permisionów");
        }
        tileDAO.deleteLikeFromTile(tileId,userName);
    }

    public void addLikeToTile(Long tileId){
        String userName = LoggedUserGetter.getUsser();
        if(userName.equals("anonymousUser")){
            throw new ResourceNotFoundException("Nie masz permisionów");
        }
        tileDAO.addLikeToTile(tileId,userName);
    }



    public TileEntity getTileEntityById(Long id){
       return tileDAO.getTileEntity(id);
    }

    public TileDTO getFullTileById(Long id){
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        String userName = LoggedUserGetter.getUsser();
        GroupDataDTO groupDataDTO = groupDataService.getGroupDataDTOByTileEntity(tileEntity);
        Map<String, String> tags = tagService.getTagsMapForTile(tileEntity);
        Integer likesCount = getLikesForTile(tileEntity.getTileId());
        Boolean isUserLikedTile = tileDAO.isUserLikedTile(tileEntity.getTileId(),userName);
        return TileMapper.mapToTileDTO(tileEntity, groupDataDTO.getGroupId() ,tags, likesCount,isUserLikedTile);
    }

    public List<TileDTO> getTilesByGroupId() { //tu będzie paginacja raczej
        List<TileEntity> tileEntityList = tileRepository.findAll();
        return mapToTileDTOList(tileEntityList);
    }

    public List<TileDTO> getTilesByGroupId(Long id) {
        GroupDataEntity groupDataEntity = groupDataService.findById(id);
        List<TileEntity> tileEntityList = tileDAO.getTileEntityByGroupDataEntity(groupDataEntity);
        return mapToTileDTOList(tileEntityList);
    }

    public List<TileDTO> getTilesIncludingAllTagIdList(Long groupId,String searchType,String order,List<Long> tagIdList){
        List<TileEntity> tileEntityList = new ArrayList<>();
        String sortOrder = "";
        if (order.equals("asc")) {
            sortOrder = "ASC";
        } else if (order.equals("desc")) {
            sortOrder = "DESC";
        } else {
            throw new InvalidPathVariableExpection("Invalid search type, should be \"asc\" or \"desc\".");
        }
        if(groupId > 0){
            if(searchType.equals("all")) {
                int listLength = tagIdList.size();
                tileEntityList = tileDAO.getTileEntityByTagIdList(tagIdList, listLength,Sort.by(Sort.Direction.valueOf(sortOrder), "createdAt"),groupId);
            }
            else if(searchType.equals("one")){
                tileEntityList = tileDAO.getTileEntityByAtLeastOneTagIdList(tagIdList,Sort.by(Sort.Direction.valueOf(sortOrder), "createdAt"),groupId);
            }
            else{
                throw new InvalidPathVariableExpection("Invalid search type, should be \"all\" or \"one\".");
            }
        }else{
            List<Long> groupIdList = accesChecker();
            if(searchType.equals("all")) {
                int listLength = tagIdList.size();
                tileEntityList = tileDAO.getTileEntityByTagIdListAndGroupIdList(tagIdList, listLength,Sort.by(Sort.Direction.valueOf(sortOrder), "createdAt"),groupIdList);
            }
            else if(searchType.equals("one")){
                tileEntityList = tileDAO.getTileEntityByAtLeastOneTagIdListAndGroupIdList(tagIdList,Sort.by(Sort.Direction.valueOf(sortOrder), "createdAt"),groupIdList);
            }
            else{
                throw new InvalidPathVariableExpection("Invalid search type, should be \"all\" or \"one\".");
            }
        }

        return mapToTileDTOList(tileEntityList);
        }

    public List<TileDTO> getTilesBySearchTileTitle(Long groupId,String tileTitleSearch){
        List<TileEntity> tileEntityList = new ArrayList<>();
        if(groupId > 0){
            tileEntityList = tileDAO.searchTileEntitiesByGroupDataId(tileTitleSearch,groupId);
        }else{
            List<Long> groupIdList = accesChecker();
            tileEntityList = tileDAO.searchTileTitleByParam(tileTitleSearch,groupIdList);
        }
        return mapToTileDTOList(tileEntityList);
    }


    public List<TileDTO> getTilesByGroupId(Long groupId, String order) {
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

            List<Long> groupIdList = accesChecker();

            tileEntityList = tileDAO.getTileEntitiesByGroupDataIdList(groupIdList);
        }
        return mapToTileDTOList(tileEntityList);
    }

    private List<Long> accesChecker(){
        String userName = LoggedUserGetter.getUsser();
        List<Long> groupIdList = new ArrayList<>();
        if(userName.equals("anonymousUser")){
            groupIdList = groupDataService.getPublicGroupsIdList();
        }else{
            groupIdList = groupDataService.getPublicAndUserGroupsIdList(userName);
        }
        return groupIdList;
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
        String userName = LoggedUserGetter.getUsser();
        for(TileEntity tileEntity : tileEntityList){
            GroupDataDTO groupDataDTO = groupDataService.getGroupDataDTOByTileEntity(tileEntity);
            Map<String, String> tags = tagService.getTagsMapForTile(tileEntity);
            Integer likesCount = getLikesForTile(tileEntity.getTileId());
            Boolean isUserLikedTile = tileDAO.isUserLikedTile(tileEntity.getTileId(),userName);

            tileDTOList.add(TileMapper.mapToTileDTO(tileEntity, groupDataDTO.getGroupId(),tags,likesCount,isUserLikedTile));
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

