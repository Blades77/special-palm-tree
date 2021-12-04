package first.iteration.endlesscreation.dao;


import first.iteration.endlesscreation.Model.ColorEntity;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.ColorRepository;
import org.springframework.stereotype.Component;

@Component
public class ColorDAO {

    private final ColorRepository colorRepository;

    public ColorDAO(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public ColorEntity getColorEntity(long id){
        return colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find specified color"));
    }

    public ColorEntity getColorEntityByHexValue(String hex){
        return colorRepository.findByColorValueHex(hex)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find specified color"));

    }

}
