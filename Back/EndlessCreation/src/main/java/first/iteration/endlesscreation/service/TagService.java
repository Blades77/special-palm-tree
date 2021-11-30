package first.iteration.endlesscreation.service;

import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.dto.TagDTO;
import first.iteration.endlesscreation.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    public List<TagDTO> getAllTags(){

        List<TagEntity> tagEntityList = tagRepository.findAll();
        List<TagDTO> tagDTOList = new ArrayList<>();
        for(TagEntity tagEntity : tagEntityList){
            TagDTO tagDTO = new TagDTO();
            tagDTO.setTagId(tagEntity.getTagId());
            tagDTO.setTagColor(tagEntity.getTagColor());
            tagDTO.setTagName(tagEntity.getTagColor());
            tagDTOList.add(tagDTO);
        }
        return tagDTOList;

    }

    public void deleTag(Long id){
        TagEntity tagEntity = tagRepository.getById(id);
        tagRepository.delete(tagEntity);

    }

    public void editTag(Long id){
        TagEntity tagEntity = tagRepository.getById(id);
        tagEntity.setTagColor("Purple");
        tagRepository.save(tagEntity);
    }
}
