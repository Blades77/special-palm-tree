package first.iteration.endlesscreation.dao;


import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.TagRepository;
import org.springframework.stereotype.Component;

@Component
public class TagDAO {

    private final TagRepository tagRepository;

    public TagDAO(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public TagEntity getTagEntityById(long id){
        return tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find specified tag"));
    }

//    public TagEntity getTagEntityByName(String){
//        return tagRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Cannot find specified tag"));
//    }
}
