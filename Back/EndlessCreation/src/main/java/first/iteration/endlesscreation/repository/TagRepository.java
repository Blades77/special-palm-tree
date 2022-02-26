package first.iteration.endlesscreation.repository;


import first.iteration.endlesscreation.Model.BookEntity;
import first.iteration.endlesscreation.Model.ColorEntity;
import first.iteration.endlesscreation.Model.TagEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.dto.TagDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long>{
    List<TagEntity> getTagEntityByTiles(TileEntity tileEntity);
    List<TagEntity> getTagEntityByBooks(BookEntity bookEntity);
//    List<TagDTO> getTagEntityByTagName(String TagName);
//    TagDTO getTagEntityByTagNameAndTagColor(String TagName,String TagColor);
    List<TagEntity> getTagEntityByColorEntity(ColorEntity colorEntity);
    Optional<TagEntity> getTagEntityByTagNameAndColorEntity(String tagName, ColorEntity colorEntity);

    @Query(value=" SELECT tag_id FROM tag WHERE tag_name IN :tagNameList", nativeQuery = true)
    Optional<List<Long>>getTagIdListByTagNameList(@Param("tagNameList") List<String> tagNameList);
}
