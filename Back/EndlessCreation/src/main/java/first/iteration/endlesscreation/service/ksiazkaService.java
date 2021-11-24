package first.iteration.endlesscreation.service;

import first.iteration.endlesscreation.Model.ksiazkaEntity;
import first.iteration.endlesscreation.repository.KsiazkaRepository;
import first.iteration.endlesscreation.dto.ksiazkaDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ksiazkaService {

    private final KsiazkaRepository ksiazkaRepository;

    public ksiazkaService(KsiazkaRepository ksiazkaRepository){
        this.ksiazkaRepository = ksiazkaRepository;
    }

    public List<ksiazkaDTO> getKsiazki() {
        List<ksiazkaEntity> ksiazkaEntityList = ksiazkaRepository.findAll();
        List<ksiazkaDTO> ksiazkiDTO = new ArrayList<>();
        for(ksiazkaEntity ksiazkaEntity : ksiazkaEntityList){
            ksiazkaDTO ksiazkaDTO = new ksiazkaDTO();
            ksiazkaDTO.setKsiazkaID(ksiazkaEntity.getKsiazkaID());
            ksiazkaDTO.setTytul(ksiazkaEntity.getTytul());
            ksiazkaDTO.setLiczbaStron(ksiazkaEntity.getLiczbaStron());
            ksiazkiDTO.add(ksiazkaDTO);

        }
        return ksiazkiDTO;

    }


}
