package first.iteration.endlesscreation.mapper;

import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.dto.ColorDTO;
import first.iteration.endlesscreation.dto.TagDTO;

public class TagMapper {

    public static TagDTO mapToTagDTO(TagEntity tagEntity, ColorDTO colorDTO){
        TagDTO tagDTO = new TagDTO();
        tagDTO.setTagId(tagEntity.getTagId());
        tagDTO.setTagName(tagEntity.getTagName());
        tagDTO.setColorDTO(colorDTO);
        return  tagDTO;
    }
}
