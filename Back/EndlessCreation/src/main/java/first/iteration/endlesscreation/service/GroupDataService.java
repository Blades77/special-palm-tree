package first.iteration.endlesscreation.service;


import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.dao.GroupDataDAO;
import first.iteration.endlesscreation.dto.GroupDataDTO;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.mapper.GroupDataMapper;
import first.iteration.endlesscreation.repository.GroupDataRepository;
import org.springframework.stereotype.Service;
import first.iteration.endlesscreation.Model.GroupDataEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupDataService {

    private final GroupDataRepository groupDataRepository;
    private final GroupDataDAO groupDataDAO;


    public GroupDataService(GroupDataRepository groupDataRepository, GroupDataDAO groupDataDAO) {

        this.groupDataRepository = groupDataRepository;
        this.groupDataDAO = groupDataDAO;
    }

    public GroupDataEntity findById(Long id){
        return groupDataDAO.findById(id);

    }

    public GroupDataDTO getGroupDataDTOByTileEntity(TileEntity tileEntity){
        GroupDataEntity groupDataEntity = groupDataDAO.findByTileEntity(tileEntity);
        return GroupDataMapper.mapToGroupDataDTO(groupDataEntity);
    }

    public List<GroupDataDTO> getGroups(){
         List<GroupDataEntity> groupDataEntityList = groupDataDAO.getGroups();
         List<GroupDataDTO> groupDataDTOList = new ArrayList<>();
         for(GroupDataEntity groupDataEntity : groupDataEntityList){
             groupDataDTOList.add(GroupDataMapper.mapToGroupDataDTO(groupDataEntity));
         }
         return groupDataDTOList;
    }

}
