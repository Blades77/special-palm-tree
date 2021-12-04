package first.iteration.endlesscreation.repository;


import first.iteration.endlesscreation.Model.ColorEntity;
import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.dto.TagDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long>{
    List<TagEntity> getTagEntityByTiles(TileEntity tileEntity);
//    List<TagDTO> getTagEntityByTagName(String TagName);
//    TagDTO getTagEntityByTagNameAndTagColor(String TagName,String TagColor);
    List<TagEntity> getTagEntityByColorEntity(ColorEntity colorEntity);
}
