package first.iteration.endlesscreation.service;


import first.iteration.endlesscreation.Model.ColorEntity;
import first.iteration.endlesscreation.dao.ColorDAO;
import first.iteration.endlesscreation.dto.ColorDTO;
import first.iteration.endlesscreation.dto.create.ColorCreateDTO;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.mapper.ColorMapper;
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
        return ColorMapper.mapToColorDTO(colorEntity);
    }

    public ColorEntity findOrCreateColor(ColorCreateDTO colorCreateDTO){
        ColorEntity colorEntity = new ColorEntity();
        try{
            colorEntity = findEntityByHex(colorCreateDTO.getColorValueHex());
        }catch(ResourceNotFoundException e){
            colorRepository.save(mapCreateToColorEntity(colorCreateDTO));
            colorEntity = findEntityByHex(colorCreateDTO.getColorValueHex());
        }

        return  colorEntity;

    }
    public ColorEntity findEntityByHex(String hex){
        ColorEntity colorEntity = colorDAO.getColorEntityByHexValue(hex);
        return  colorEntity;
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
            colorDTOList.add(ColorMapper.mapToColorDTO(colorEntity));
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



    private ColorEntity mapCreateToColorEntity(ColorCreateDTO colorCreateDTO){
        ColorEntity colorEntity = new ColorEntity();
        colorEntity.setColorValueHex(colorCreateDTO.getColorValueHex());
        colorEntity.setColorDesc(colorCreateDTO.getColorDesc());
        return colorEntity;
    }
}
