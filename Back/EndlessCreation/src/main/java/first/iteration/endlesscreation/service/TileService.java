package first.iteration.endlesscreation.service;

import first.iteration.endlesscreation.Model.Group_dataEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.dto.TileDTO;
import first.iteration.endlesscreation.service.GroupDataService;
import first.iteration.endlesscreation.dto.create.TileCreateDTO;
import first.iteration.endlesscreation.repository.TileRepository;
import first.iteration.endlesscreation.repository.GroupDataRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TileService {

    private final TileRepository tileRepository;
    private final GroupDataRepository groupDataRepository;
    private final GroupDataService groupDataService;

    public TileService(TileRepository tileRepository, GroupDataRepository groupDataRepository, GroupDataService groupDataService) {
        this.tileRepository = tileRepository;
        this.groupDataRepository = groupDataRepository;
        this.groupDataService = groupDataService;
    }

    public List<TileDTO> getTiles() {
        List<TileEntity> tileEntityList = tileRepository.findAll();
        List<TileDTO> tilesDTO = new ArrayList<>();
        for(TileEntity tileEntity : tileEntityList){
            TileDTO tileDTO = new TileDTO();
            tileDTO.setTile_id(tileEntity.getTile_id());
            tileDTO.setTile_title(tileEntity.getTile_title());
            tileDTO.setTile_data(tileEntity.getTile_data());
            tileDTO.setGroup_id(tileEntity.getGroup_dataEntity().getGroup_id());
            tilesDTO.add(tileDTO);

        }
        return tilesDTO;
    }

    public void createTile(TileCreateDTO tileCreateDTO){
        TileEntity tileEntity = new TileEntity();
        tileEntity.setTile_title(tileCreateDTO.getTile_title());
        tileEntity.setTile_data(tileCreateDTO.getTile_data());
        tileEntity.setOwner_user_id(tileCreateDTO.getOwner_user_id());
        tileEntity.setGroup_dataEntity(groupDataService.findById(tileCreateDTO.getGroup_id()));
        tileRepository.save(tileEntity);

    }
}

