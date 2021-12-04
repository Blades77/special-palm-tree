package first.iteration.endlesscreation.service;

import first.iteration.endlesscreation.Model.ColorEntity;
import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.dao.ColorDAO;
import first.iteration.endlesscreation.dao.TagDAO;
import first.iteration.endlesscreation.dto.ColorDTO;
import first.iteration.endlesscreation.dto.TagDTO;
import first.iteration.endlesscreation.dto.create.TagCreateDTO;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class TagService {

    private final TagRepository tagRepository;
    private final ColorService colorService;
    private final ColorDAO colorDAO;
    private final TagDAO tagDAO;
    private boolean isTagInBase;

    public TagService(TagRepository tagRepository,ColorDAO colorDAO,TagDAO tagDAO, ColorService colorService)
    {
        this.tagRepository = tagRepository;
        this.colorService = colorService;
        this.colorDAO = colorDAO;
        this.tagDAO = tagDAO;
    }

    public void createTag(TagCreateDTO tagCreateDTO){
        isTagInBase = false;
        ColorEntity colorEntity = new ColorEntity();
        try{
            colorEntity = colorDAO.getColorEntityByHexValue(tagCreateDTO.getColorCreateDTO().getColorValueHex());
        }
        catch(ResourceNotFoundException e){
            colorService.createColor(tagCreateDTO.getColorCreateDTO());
            colorEntity = colorDAO.getColorEntityByHexValue(tagCreateDTO.getColorCreateDTO().getColorValueHex());
        }

        List<TagEntity> tagEntityList = tagRepository.getTagEntityByColorEntity(colorEntity);
        if(tagEntityList.isEmpty() == true){
            tagRepository.save(mapToTagEntity(tagCreateDTO,colorEntity));

        }

        else{
            for(TagEntity tagEntity : tagEntityList) {
                if(tagCreateDTO.getTagName().equals(tagEntity.getTagName())){
                    isTagInBase = true;
                }
            }
            if(isTagInBase == false){
                tagRepository.save(mapToTagEntity(tagCreateDTO,colorEntity));
            }
        }

    }

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

    public TagDTO mapToTagDTO(TagEntity tagEntity){
        TagDTO tagDTO = new TagDTO();
        tagDTO.setTagId(tagEntity.getTagId());
        tagDTO.setTagName(tagEntity.getTagName());
        tagDTO.setColorDTO(colorService.findById(tagEntity.getColorEntity().getColorId()));
        return  tagDTO;
    }

    private TagEntity mapToTagEntity(TagCreateDTO tagCreateDTO, ColorEntity colorEntity){
        TagEntity tagEntity = new TagEntity();
        tagEntity.setTagName(tagCreateDTO.getTagName());
        tagEntity.setTagName(tagCreateDTO.getTagName());
        tagEntity.setColorEntity(colorEntity);
        return tagEntity;

    }
}
