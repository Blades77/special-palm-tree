package first.iteration.endlesscreation.service;

import first.iteration.endlesscreation.Model.CommentEntity;
import first.iteration.endlesscreation.Model.GroupDataEntity;
import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.dao.TagDAO;
import first.iteration.endlesscreation.dao.TileDAO;
import first.iteration.endlesscreation.dto.CommentDTO;
import first.iteration.endlesscreation.dto.GroupDataDTO;
import first.iteration.endlesscreation.dto.TagDTO;
import first.iteration.endlesscreation.dto.TileDTO;
import first.iteration.endlesscreation.dto.Update.CommentUpdateDTO;
import first.iteration.endlesscreation.dto.Update.TileUpdateDTO;
import first.iteration.endlesscreation.dto.create.CommentCreateDTO;
import first.iteration.endlesscreation.dto.create.TagCreateDTO;
import first.iteration.endlesscreation.dto.create.TileCreateDTO;
import first.iteration.endlesscreation.exception.InvalidPathVariableExpection;
import first.iteration.endlesscreation.mapper.TileMapper;
import first.iteration.endlesscreation.repository.CommentRepository;
import first.iteration.endlesscreation.repository.TagRepository;
import first.iteration.endlesscreation.repository.TileRepository;
import first.iteration.endlesscreation.repository.GroupDataRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TileService {

    private final TileRepository tileRepository;
    private final TagRepository tagRepository;
    private final GroupDataRepository groupDataRepository;
    private final CommentRepository commentRepository;
    private final GroupDataService groupDataService;
    private final TagService tagService;
    private final TileDAO tileDAO;
    private final TagDAO tagDAO;

    public TileService(TileRepository tileRepository, GroupDataRepository groupDataRepository, GroupDataService groupDataService, TagRepository tagRepository, TileDAO tileDAO, TagDAO tagDAO,TagService tagService, CommentRepository commentRepository) {
        this.tileRepository = tileRepository;
        this.groupDataRepository = groupDataRepository;
        this.commentRepository = commentRepository;
        this.groupDataService = groupDataService;
        this.tagRepository = tagRepository;
        this.tagService = tagService;
        this.tileDAO = tileDAO;
        this.tagDAO = tagDAO;

    }

    public TileEntity getTileEntityById(Long id){
       return tileDAO.getTileEntity(id);
    }

    public TileDTO getFullTileById(Long id){
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        GroupDataDTO groupDataDTO = groupDataService.getGroupDataDTOByTileEntity(tileEntity);
        List<TagDTO> tagDTOList = getTagsForTile(tileEntity.getTileId());
        return TileMapper.mapToTileDTO(tileEntity, groupDataDTO ,tagDTOList);
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

    public List<TileDTO> getTilesBySearchTileTitle(String tileTitleSearch){
        List<TileEntity> tileEntityList = tileDAO.searchTileTitleByParam(tileTitleSearch);
        return mapToTileDTOList(tileEntityList);
    }

    public void createTile(TileCreateDTO tileCreateDTO){
        GroupDataEntity groupDataEntity = groupDataService.findById(tileCreateDTO.getGroupDataDTO().getGroupId());
        TileEntity tileEntity = TileMapper.mapCreateToTileEntity(tileCreateDTO, groupDataEntity);
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


    private List<TileDTO> mapToTileDTOList(List<TileEntity> tileEntityList){ /// pomyślec nad tym
        List<TileDTO> tileDTOList = new ArrayList<>();
        for(TileEntity tileEntity : tileEntityList){
            GroupDataDTO groupDataDTO = groupDataService.getGroupDataDTOByTileEntity(tileEntity);
            List<TagDTO> tagDTOList = getTagsForTile(tileEntity.getTileId());
            tileDTOList.add(TileMapper.mapToTileDTO(tileEntity, groupDataDTO,tagDTOList));
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

