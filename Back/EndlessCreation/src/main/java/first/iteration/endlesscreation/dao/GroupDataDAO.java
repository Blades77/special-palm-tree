package first.iteration.endlesscreation.dao;


import first.iteration.endlesscreation.Model.GroupDataEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.GroupDataRepository;
import org.springframework.stereotype.Component;

@Component
public class GroupDataDAO {
    private final GroupDataRepository groupDataRepository;

    public GroupDataDAO(GroupDataRepository groupDataRepository) {
        this.groupDataRepository = groupDataRepository;
    }
    public GroupDataEntity findById(Long id){
        return groupDataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find specified group"));

    }

    public GroupDataEntity findByTileEntity(TileEntity tileEntity){
        return groupDataRepository.getGroupDataEntityByTiles(tileEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find specified group"));

    }
}
