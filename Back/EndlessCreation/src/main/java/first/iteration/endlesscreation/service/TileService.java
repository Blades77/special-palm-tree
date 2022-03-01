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
import first.iteration.endlesscreation.exception.DefaultExpection;
import first.iteration.endlesscreation.mapper.TileMapper;
import first.iteration.endlesscreation.repository.CommentRepository;
import first.iteration.endlesscreation.repository.TagRepository;
import first.iteration.endlesscreation.repository.TileRepository;
import first.iteration.endlesscreation.repository.GroupDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    public TileService(TileRepository tileRepository, GroupDataRepository groupDataRepository, GroupDataService groupDataService, TagRepository tagRepository, TileDAO tileDAO, TagDAO tagDAO, TagService tagService, CommentRepository commentRepository, UserService userService) {
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

    public Integer getLikesForTile(Long tileId) {
        return tileDAO.getLikesForTile(tileId);
    }

    public Integer getCommentsCountForTile(Long tileId) {
        return tileDAO.getCommentsCountForTile(tileId);
    }

    public boolean doSaveTile(Long tileId) {
        String userName = LoggedUserGetter.getUsser();
        if (userName.equals("anonymousUser")) {
            throw new DefaultExpection("Access Denied");
        }
        if (tileDAO.isUserSavedTile(tileId, userName)) {
            tileDAO.unsaveTileFromTileSave(tileId, userName);
            return true;
        } else {
            tileDAO.saveTileToTileSave(tileId, userName);
            return true;
        }

    }

//    public void deleteLikeForTile(Long tileId){
//        String userName = LoggedUserGetter.getUsser();
//        if(userName.equals("anonymousUser")){
//            throw new ResourceNotFoundException("Nie masz permisionów");
//        }
//        tileDAO.deleteLikeFromTile(tileId,userName);
//    }
//
//    public void addLikeToTile(Long tileId){
//        String userName = LoggedUserGetter.getUsser();
//        if(userName.equals("anonymousUser")){
//            throw new ResourceNotFoundException("Nie masz permisionów");
//        }
//        tileDAO.addLikeToTile(tileId,userName);
//    }

    public boolean doLike(Long tileId) {
        String userName = LoggedUserGetter.getUsser();
        if (userName.equals("anonymousUser")) {
            throw new DefaultExpection("Access Denied");
        }
        if (tileDAO.isUserLikedTile(tileId, userName)) {
            tileDAO.deleteLikeFromTile(tileId, userName);
            return true;
        } else {
            tileDAO.addLikeToTile(tileId, userName);
            return true;
        }

    }

    public TileEntity getTileEntityById(Long id) {
        return tileDAO.getTileEntity(id);
    }

    public TileDTO getFullTileById(Long id) {
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        String userName = LoggedUserGetter.getUsser();
        GroupDataDTO groupDataDTO = groupDataService.getGroupDataDTOByTileEntity(tileEntity);
        Map<String, String> tags = tagService.getTagsMapForTile(tileEntity);
        Boolean isTagsNotEmpty = tags.isEmpty();
        Integer likesCount = getLikesForTile(tileEntity.getTileId());
        Boolean isUserLikedTile = tileDAO.isUserLikedTile(tileEntity.getTileId(), userName);
        Integer commentsCount = getCommentsCountForTile(tileEntity.getTileId());
        Boolean savedTile = tileDAO.isUserSavedTile(tileEntity.getTileId(), userName);
        return TileMapper.mapToTileDTO(tileEntity, groupDataDTO.getGroupId(), tags, likesCount, isUserLikedTile, groupDataDTO, isTagsNotEmpty, commentsCount, savedTile);
    }

    private Sort checkSorting(String order, boolean isjpa) {
        Sort sortDirection;
        String sorting = "";
        if (isjpa) {
            sorting = "createdAt";
        } else {
            sorting = "created_at";
        }
        if (order.equals("asc")) {
            sortDirection = Sort.by(sorting).ascending();
        } else if (order.equals("desc")) {
            sortDirection = Sort.by(sorting).descending();
        } else {
            throw new InvalidPathVariableExpection("Invalid search type, should be \"asc\" or \"desc\".");
        }
        return sortDirection;
    }

    public LocalDateTime dateSetter(String dateWord) {
        LocalDateTime date = LocalDateTime.now();
        switch (dateWord) {
            case "today":
                date = date.minusDays(1);
                break;
            case "week":
                date = date.minusDays(7);
                break;
            case "month":
                date = date.minusDays(31);
                break;
            case "year":
                date = date.minusDays(365);
                break;
            default:
                date = LocalDateTime.of(1970, 01, 01, 01, 01, 01, 01);
                break;
        }
        return date;
    }


    public List<TileDTO> doSearchGroup(String order, Integer page, String sortBy, Long groupId, String term) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = dateSetter(term);
        Sort sortDirection = checkSorting(order, true);
        List<TileEntity> tileEntityList = new ArrayList<>();
        if (sortBy.equals("createdAt")) {
            GroupDataEntity groupDataEntity = groupDataService.findById(groupId);
            tileEntityList = tileDAO.getTilesByGroupDataEnityWithSort(groupDataEntity, PageRequest.of(page, 3, sortDirection));
        } else if (sortBy.equals("likes")) {
            List<Long> groupIdList = new ArrayList<>();
            groupIdList.add(groupId);
            if (order.equals("asc")) {
                tileEntityList = tileDAO.getTileEntitiesByGroupIdListSortByLikeASC(groupIdList, nowDateTime, endDateTime, PageRequest.of(page, 3));
            } else if (order.equals("desc")) {
                tileEntityList = tileDAO.getTileEntitiesByGroupIdListSortByLikeDESC(groupIdList, nowDateTime, endDateTime, PageRequest.of(page, 3));
            } else {
                throw new InvalidPathVariableExpection("Invalid search type, should be \"likes\" or \"createdAt\".");
            }
        }
        return mapToTileDTOList(tileEntityList);
    }


    public void createTile(TileCreateDTO tileCreateDTO, Long groupId) {
        GroupDataEntity groupDataEntity = groupDataService.findById(groupId);
        UserEntity userEntity = userService.getUserEntityById(tileCreateDTO.getOwnerUserId());
        TileEntity tileEntity = TileMapper.mapCreateToTileEntity(tileCreateDTO, groupDataEntity, userEntity);
        List<TagEntity> tagEntityList = tagService.getTagsEntityListByTagCreateDTOList(tileCreateDTO.getTagCreateDTOList());
        for (TagEntity tagEntity : tagEntityList) {
            tileEntity.addTag(tagEntity);
        }
        tileRepository.save(tileEntity);
    }

    public void editTile(TileUpdateDTO tileUpdateDTO) {
        Long id = tileUpdateDTO.getTileId();
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        tileRepository.save(TileMapper.mapUpdateToTileEntity(tileUpdateDTO, tileEntity));
    }

    public void deleteTile(Long id) {
        TileEntity tileEntity = tileDAO.getTileEntity(id);
        List<TagEntity> tagEntityList = tagService.getTagEntityListByTile(tileEntity);
        if (tagEntityList.isEmpty() == false) {
            for (TagEntity tagEntity : tagEntityList) {
                tileEntity.removeTag(tagEntity);
            }
        }
        tileRepository.save(tileEntity);
        tileRepository.delete(tileEntity);
    }

//    public List<TagDTO> getTagsForTile(Long id) {
//        TileEntity tileEntity = tileDAO.getTileEntity(id);
//        return tagService.getTagsForTile(tileEntity);
//    }
//
//    public List<TagEntity> getTagEntitiesForTile(Long id) {
//        TileEntity tileEntity = tileDAO.getTileEntity(id);
//        return tagService.getTagEntityListByTile(tileEntity);
//    }

    public void addTagToTile(Long tileId, TagCreateDTO tagCreateDTO) {
        TileEntity tileEntity = tileDAO.getTileEntity(tileId);
        TagEntity tagEntity = tagService.findOrCreateTag(tagCreateDTO);
        tileEntity.addTag(tagEntity);
        tileRepository.save(tileEntity);
    }

    public void deleteTagFromTile(Long tileId, Long tagId) {
        TileEntity tileEntity = tileDAO.getTileEntity(tileId);
        TagEntity tagEntity = tagService.getTagEntityById(tagId);
        tileEntity.removeTag(tagEntity);
        tileRepository.save(tileEntity);
    }


    private List<TileDTO> mapToTileDTOList(List<TileEntity> tileEntityList) {
        List<TileDTO> tileDTOList = new ArrayList<>();
        String userName = LoggedUserGetter.getUsser();
        for (TileEntity tileEntity : tileEntityList) {
            GroupDataDTO groupDataDTO = groupDataService.getGroupDataDTOByTileEntity(tileEntity);
            Map<String, String> tags = tagService.getTagsMapForTile(tileEntity);
            Boolean isTagsNotEmpty = tags.isEmpty();
            Integer likesCount = getLikesForTile(tileEntity.getTileId());
            Boolean isUserLikedTile = tileDAO.isUserLikedTile(tileEntity.getTileId(), userName);
            Integer commentsCount = getCommentsCountForTile(tileEntity.getTileId());
            Boolean savedTile = tileDAO.isUserSavedTile(tileEntity.getTileId(), userName);

            tileDTOList.add(TileMapper.mapToTileDTO(tileEntity, groupDataDTO.getGroupId(), tags, likesCount, isUserLikedTile, groupDataDTO, isTagsNotEmpty, commentsCount, savedTile));
        }
        return tileDTOList;
    }


    private List<Long> scopeTileAcces(Integer groupsType) {
        List<Long> groupIdList = new ArrayList<>();
        String userName = LoggedUserGetter.getUsser();
        if (userName.equals("anonymousUser")) {
            groupIdList = groupDataService.getPublicGroupsIdList();
        } else {
            if (groupsType == 1) {
                groupIdList = groupDataService.getUserGroupsIdList(userName);
            } else {
                groupIdList = groupDataService.getPublicAndUserGroupsIdList(userName);
            }

        }
        return groupIdList;
    }


    public List<Long> processStringTagsIntoTagStringList(String stringTags) {

        List<String> tags = new ArrayList<String>(Arrays.asList(stringTags.split(",")));
        List<Long> tagIdList = tagService.getTagIdListByTagNameList(tags);
        if (tagIdList.isEmpty()) {
            tagIdList.add(99999l);
        }
        return tagIdList;
    }

    public List<TileDTO> getDashboardTilesStringSearch(String typeEnd, Integer page, String type, String search) {
        if (typeEnd.equals("nh")) {
            return getDashboardNewestHottestTiles(page, type, search, new ArrayList<>());
        }
        return null;

    }

    public List<TileDTO> getDashboardTilesTagIdListSearch(String typeEnd, Integer page, String type, List<Long> tagIdList) {
        if (typeEnd.equals("nh")) {
            return getDashboardNewestHottestTiles(page, type, "", tagIdList);
        }
        return null;
    }

    public List<TileDTO> getDashboardTiles(String typeEnd, Integer page, String type) {
        if (typeEnd.equals("nh")) {
            return getDashboardNewestHottestTiles(page, type, "", new ArrayList<>());
        }
        return null;
    }

    public List<TileDTO> getDashboardNewestHottestTiles(Integer page, String type, String search, List<Long> tagIdList) {
        List<TileEntity> tileEntityList = new ArrayList<>();
        List<Long> groupIdList = new ArrayList<>();


        if (type.equals("new")) {
            if (search.equals("") && tagIdList.isEmpty()) {
                groupIdList = scopeTileAcces(1);
                tileEntityList = tileDAO.getNewestTileEntitiesForDashboard(groupIdList, PageRequest.of(page, 5));
                System.out.println("Tu leci opcja pierwsza----------------new---------" + tagIdList.isEmpty());
            } else if (!tagIdList.isEmpty()) {
                int listLength = tagIdList.size();
                groupIdList = scopeTileAcces(0);
                tileEntityList = tileDAO.getNewestTileEntitiesForDashboardWithTags(groupIdList, tagIdList, listLength, PageRequest.of(page, 5));
                System.out.println("Tu leci opcja druga----------------new---------");
            } else if (!search.isEmpty()) {
                groupIdList = scopeTileAcces(0);
                tileEntityList = tileDAO.getNewestTileEntitiesForDashboardWithSearch(groupIdList, search, PageRequest.of(page, 5));
                System.out.println("Tu leci opcja trzecia-----------new--------------");
            }
        } else if (type.equals("hot")) {
            LocalDateTime nowDate = LocalDateTime.now();
            LocalDateTime hotDate = nowDate.minusDays(30);

            if (search.equals("") && tagIdList.isEmpty()) {
                groupIdList = scopeTileAcces(1);
                tileEntityList = tileDAO.getHottestTileEntitiesForDashboard(groupIdList, hotDate, nowDate, PageRequest.of(page, 5));
                System.out.println("Tu leci opcja pierwsza hot-------------------------");
            } else if (!tagIdList.isEmpty()) {
                int listLength = tagIdList.size();
                groupIdList = scopeTileAcces(0);
                tileEntityList = tileDAO.getHottestTileEntitiesForDashboardWithTags(groupIdList, hotDate, nowDate, tagIdList, listLength, PageRequest.of(page, 5));
                System.out.println("Tu leci opcja druga-------hot------------------");
            } else if (!search.isEmpty()) {
                groupIdList = scopeTileAcces(0);
                tileEntityList = tileDAO.getHottestTileEntitiesForDashboardWithSearch(groupIdList, hotDate, nowDate, search, PageRequest.of(page, 5));
                System.out.println("Tu leci opcja trzecia--------hot-----------------");
            }
        } else {
            throw new InvalidPathVariableExpection("Invalid search type, should be \"new\" or \"hot\".");
        }
        return mapToTileDTOList(tileEntityList);
    }

    public List<TileDTO> getDashboardLikesTilesStringSearch(Integer page, String term, String order, String search) {
        return getDashboardTileForLikes(page, term, order, search, new ArrayList<>());

    }

    public List<TileDTO> getDashboardLikesTilesTagIdListSearch(Integer page, String term, String order, List<Long> tagIdList) {
        return getDashboardTileForLikes(page, term, order, "", tagIdList);
    }

    public List<TileDTO> getDashboardLikesTiles(Integer page, String term, String order) {
        return getDashboardTileForLikes(page, term, order, "", new ArrayList<>());

    }


    public List<TileDTO> getDashboardTileForLikes(Integer page, String term, String order, String search, List<Long> tagIdList) {
        List<TileEntity> tileEntityList = new ArrayList<>();
        List<Long> groupIdList = new ArrayList<>();
        LocalDateTime scopeDate = dateSetter(term);
        LocalDateTime nowDate = LocalDateTime.now();

        if (order.equals("asc")) {
            if (search.equals("") && tagIdList.isEmpty()) {
                groupIdList = scopeTileAcces(1);
                tileEntityList = tileDAO.getTileEntitiesByGroupIdListSortByLikeASC(groupIdList, scopeDate, nowDate, PageRequest.of(page, 3));
                System.out.println("Tu leci opcja pierwsza----------------asc normal---------");
            } else if (!tagIdList.isEmpty()) {
                int listLength = tagIdList.size();
                groupIdList = scopeTileAcces(0);
                tileEntityList = tileDAO.getTileEntitiesByGroupIdListAndTagIdListSortByLikeASC(groupIdList, scopeDate, nowDate, tagIdList, listLength, PageRequest.of(page, 3));
                System.out.println("Tu leci opcja druga----------------asc tags---------");
            } else if (!search.isEmpty()) {
                groupIdList = scopeTileAcces(0);
                tileEntityList = tileDAO.getTileEntitiesByGroupIdListAndSearchSortByLikeASC(groupIdList, scopeDate, nowDate, search, PageRequest.of(page, 3));
                System.out.println("Tu leci opcja trzecia-----------asc search--------------");
            }
        } else if (order.equals("desc")) {
            if (search.equals("") && tagIdList.isEmpty()) {
                groupIdList = scopeTileAcces(1);
                tileEntityList = tileDAO.getTileEntitiesByGroupIdListSortByLikeDESC(groupIdList, scopeDate, nowDate, PageRequest.of(page, 3));
                System.out.println("Tu leci opcja pierwsza----------------desc normal---------");
            } else if (!tagIdList.isEmpty()) {
                int listLength = tagIdList.size();
                groupIdList = scopeTileAcces(0);
                tileEntityList = tileDAO.getTileEntitiesByGroupIdListAndTagIdListSortByLikeDESC(groupIdList, scopeDate, nowDate, tagIdList, listLength, PageRequest.of(page, 3));
                System.out.println("Tu leci opcja druga----------------desc tags---------");
            } else if (!search.isEmpty()) {
                groupIdList = scopeTileAcces(0);
                tileEntityList = tileDAO.getTileEntitiesByGroupIdListAndSearchSortByLikeDESC(groupIdList, scopeDate, nowDate, search, PageRequest.of(page, 3));
                System.out.println("Tu leci opcja trzecia-----------desc search--------------");
            }
        } else {
            throw new InvalidPathVariableExpection("Invalid search type, should be \"asc\" or \"desc\".");
        }
        return mapToTileDTOList(tileEntityList);

    }

}