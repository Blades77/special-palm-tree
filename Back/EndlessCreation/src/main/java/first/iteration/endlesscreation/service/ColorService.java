package first.iteration.endlesscreation.service;


import first.iteration.endlesscreation.Model.ColorEntity;
import first.iteration.endlesscreation.dao.ColorDAO;
import first.iteration.endlesscreation.dto.ColorDTO;
import first.iteration.endlesscreation.dto.create.ColorCreateDTO;
import first.iteration.endlesscreation.repository.ColorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ColorService {

    private final ColorRepository colorRepository;
    private final ColorDAO colorDAO;

    public ColorService(ColorRepository colorRepository, ColorDAO colorDAO) {
        this.colorRepository = colorRepository;
        this.colorDAO = colorDAO;

    }

    public ColorDTO findById(Long id){
        ColorEntity colorEntity = colorDAO.getColorEntity(id);
        return  mapToColorDTO(colorEntity);
    }

    public ColorDTO findByHex(String hex){
        ColorEntity colorEntity = colorDAO.getColorEntityByHexValue(hex);
        return  mapToColorDTO(colorEntity);
    }
    // to dla userów
    public void createColor(ColorCreateDTO colorCreateDTO){
        colorRepository.save(mapCreateToColorEntity(colorCreateDTO));
    }

    public void deleteColorById(Long id){
        ColorEntity colorEntity = colorDAO.getColorEntity(id);
        colorRepository.delete(colorEntity);
    }
    // to tylko dla admina
     public List<ColorDTO> getAllColors(){
        List<ColorEntity> colorEntityList = colorRepository.findAll();
        List<ColorDTO> colorDTOList = new ArrayList<>();
        for(ColorEntity colorEntity : colorEntityList){
            colorDTOList.add(mapToColorDTO(colorEntity));
        }
        return colorDTOList;
     }

     //to tylko dla admina
     public void editColorDesc(ColorDTO colorDTO){
        Long id = colorDTO.getColorId();
        ColorEntity colorEntity = colorDAO.getColorEntity(id);
        colorEntity.setColorDesc(colorDTO.getColorDesc());
        colorRepository.save(colorEntity);
     }

    public ColorDTO mapToColorDTO(ColorEntity colorEntity){
        ColorDTO colorDTO = new ColorDTO();
        colorDTO.setColorId(colorEntity.getColorId());
        colorDTO.setColorValueHex(colorEntity.getColorValueHex());
        colorDTO.setColorDesc(colorEntity.getColorDesc());
        return  colorDTO;

    }


    private ColorEntity mapCreateToColorEntity(ColorCreateDTO colorCreateDTO){
        ColorEntity colorEntity = new ColorEntity();
        colorEntity.setColorValueHex(colorCreateDTO.getColorValueHex());
        colorEntity.setColorDesc(colorCreateDTO.getColorDesc());
        return colorEntity;
    }
}
