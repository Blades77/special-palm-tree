package first.iteration.endlesscreation.dao;


import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.ColorEntity;
import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.TagRepository;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public TagEntity getTagEntityByTagNameAndColorEntity(String tagName, ColorEntity colorEntity){
        return tagRepository.getTagEntityByTagNameAndColorEntity(tagName,colorEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find specified tag"));
    }

    public List<TagEntity> getTagEntityListByTile(TileEntity tileEntity){
        return  tagRepository.getTagEntityByTiles(tileEntity);
    }

    public List<TagEntity> getTagEntityListByBook(BookEntity bookEntity){
        return  tagRepository.getTagEntityByBooks(bookEntity);
    }
}
