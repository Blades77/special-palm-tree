package first.iteration.endlesscreation.service;


import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.GroupDataRepository;
import org.springframework.stereotype.Service;
import first.iteration.endlesscreation.Model.Group_dataEntity;

@Service
public class GroupDataService {

    private final GroupDataRepository groupDataRepository;

    public GroupDataService(GroupDataRepository groupDataRepository) {
        this.groupDataRepository = groupDataRepository;
    }

    public Group_dataEntity findById(Long id){
        return groupDataRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find specified group"));

    }
}
