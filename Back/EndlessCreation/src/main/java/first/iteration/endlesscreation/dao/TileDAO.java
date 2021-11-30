package first.iteration.endlesscreation.dao;

import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.TileRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TileDAO {
    private final TileRepository tileRepository;

    public TileDAO(TileRepository tileRepository) {
        this.tileRepository = tileRepository;
    }

    public TileEntity getTileEntity(Long id){
        return  tileRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Cannot find specified tile"));
    }

}
