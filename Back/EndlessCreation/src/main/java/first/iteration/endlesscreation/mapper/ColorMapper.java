package first.iteration.endlesscreation.mapper;

import first.iteration.endlesscreation.Model.ColorEntity;
import first.iteration.endlesscreation.dto.ColorDTO;

public class ColorMapper {


    public static ColorDTO mapToColorDTO(ColorEntity colorEntity){
        ColorDTO colorDTO = new ColorDTO();
        colorDTO.setColorId(colorEntity.getColorId());
        colorDTO.setColorValueHex(colorEntity.getColorValueHex());
        colorDTO.setColorDesc(colorEntity.getColorDesc());
        return  colorDTO;

    }
}
