package first.iteration.endlesscreation.service;

import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.ColorEntity;
import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.dao.ColorDAO;
import first.iteration.endlesscreation.dao.TagDAO;
import first.iteration.endlesscreation.dto.ColorDTO;
import first.iteration.endlesscreation.dto.TagDTO;
import first.iteration.endlesscreation.dto.create.TagCreateDTO;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.mapper.TagMapper;
import first.iteration.endlesscreation.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TagService {

    private final TagRepository tagRepository;
    private final ColorService colorService;
    private final ColorDAO colorDAO;
    private final TagDAO tagDAO;

    public TagService(TagRepository tagRepository,ColorDAO colorDAO,TagDAO tagDAO, ColorService colorService)
    {
        this.tagRepository = tagRepository;
        this.colorService = colorService;
        this.colorDAO = colorDAO;
        this.tagDAO = tagDAO;
    }

    public TagEntity getTagEntityById(Long id){
        return tagDAO.getTagEntityById(id);
    }

    public TagEntity findOrCreateTag(TagCreateDTO tagCreateDTO) {
        ColorEntity colorEntity = colorService.findOrCreateColor(tagCreateDTO.getColorCreateDTO());
        TagEntity tagEntity = new TagEntity();
        try {
            tagEntity = tagDAO.getTagEntityByTagNameAndColorEntity(tagCreateDTO.getTagName(),colorEntity);
        } catch (ResourceNotFoundException e) {
            tagRepository.save(mapToTagEntity(tagCreateDTO,colorEntity));
            tagEntity = tagDAO.getTagEntityByTagNameAndColorEntity(tagCreateDTO.getTagName(),colorEntity);
        }
        return tagEntity;
    }

    public List<Long> getTagIdListByTagNameList(List <String> tagNameList){
        return tagDAO.getTagIdListByTagNameList(tagNameList);
    }

    public List<TagEntity> getTagsEntityListByTagCreateDTOList(List<TagCreateDTO> tagCreateDTOList){
        List<TagEntity> tagEntityList = new ArrayList<>();
        for(TagCreateDTO tagCreateDTO: tagCreateDTOList){
            tagEntityList.add(findOrCreateTag(tagCreateDTO));
        }
        return tagEntityList;
    }

    public List<TagEntity> getTagEntityListByTile(TileEntity tileEntity){
        return  tagDAO.getTagEntityListByTile(tileEntity);
    }

    public List<TagEntity> getTagEntityListByBook(BookEntity bookEntity){
        return  tagDAO.getTagEntityListByBook(bookEntity);
    }
    public List<TagDTO>getTagsForTile(TileEntity tileEntity){
        List<TagEntity> tagEntityList = getTagEntityListByTile(tileEntity);
        List<TagDTO> tagDTOList = new ArrayList<>();
        for(TagEntity tagEntity : tagEntityList){
            ColorDTO colorDTO = colorService.findById(tagEntity.getColorEntity().getColorId());
            tagDTOList.add(TagMapper.mapToTagDTO(tagEntity,colorDTO));

        }
        return tagDTOList;
    }

    public List<TagDTO>getTagsForBook(BookEntity bookEntity){
        List<TagEntity> tagEntityList = getTagEntityListByBook(bookEntity);
        List<TagDTO> tagDTOList = new ArrayList<>();
        for(TagEntity tagEntity : tagEntityList){
            ColorDTO colorDTO = colorService.findById(tagEntity.getColorEntity().getColorId());
            tagDTOList.add(TagMapper.mapToTagDTO(tagEntity,colorDTO));

        }
        return tagDTOList;
    }

    public Map<String,String> getTagsMapForTile(TileEntity tileEntity){
        List<TagEntity> tagEntityList = getTagEntityListByTile(tileEntity);
        Map<String, String> tags = new HashMap<>();
        for(TagEntity tagEntity : tagEntityList){
            tags.put(tagEntity.getTagName(),tagEntity.getColorEntity().getColorValueHex());
        }
        return  tags;
    }

//
//        List<TagEntity> tagEntityList = tagRepository.getTagEntityByColorEntity(colorEntity);
//        if(tagEntityList.isEmpty() == true){
//            tagRepository.save(mapToTagEntity(tagCreateDTO,colorEntity));
//
//        }
//
//        else{
//            for(TagEntity tagEntity : tagEntityList) {
//                if(tagCreateDTO.getTagName().equals(tagEntity.getTagName())){
//                    isTagInBase = true;
//                }
//            }
//            if(isTagInBase == false){
//                tagRepository.save(mapToTagEntity(tagCreateDTO,colorEntity));
//            }
//        }
//
//        return mapToTagEntity(tagCreateDTO,colorEntity);

//    }

//    public List<TagEntity> getTagsEntityListByTagCreateDTOList(List<TagCreateDTO> tagCreateDTOList){
//        List<TagEntity> tagEntityList = new ArrayList<>();
//        for(TagCreateDTO tagCreateDTO : tagCreateDTOList){
//                tagEntityList.add(checkIfExistOrCreateTag(tagCreateDTO));
//        }
//        return  tagEntityList;
//    }

    public List<TagDTO> getAllTags(){

        List<TagEntity> tagEntityList = tagRepository.findAll();
        List<TagDTO> tagDTOList = new ArrayList<>();
        for(TagEntity tagEntity : tagEntityList){
            TagDTO tagDTO = new TagDTO();
            tagDTO.setTagId(tagEntity.getTagId());
//            tagDTO.setTagColor(tagEntity.getTagColor());
//            tagDTO.setTagName(tagEntity.getTagColor());
            tagDTOList.add(tagDTO);
        }
        return tagDTOList;

    }

//    public TagDTO getTagsByName(){}
//    public void createTag(){}

    public void deleTag(Long id){
        TagEntity tagEntity = tagRepository.getById(id);
        tagRepository.delete(tagEntity);

    }

    public void editTag(Long id){
        TagEntity tagEntity = tagRepository.getById(id);
//        tagEntity.setTagColor("Purple");
        tagRepository.save(tagEntity);
    }



    private TagEntity mapToTagEntity(TagCreateDTO tagCreateDTO, ColorEntity colorEntity){
        TagEntity tagEntity = new TagEntity();
        tagEntity.setTagName(tagCreateDTO.getTagName());
        tagEntity.setTagName(tagCreateDTO.getTagName());
        tagEntity.setColorEntity(colorEntity);
        return tagEntity;

    }
}
