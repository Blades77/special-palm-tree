package first.iteration.endlesscreation.repository;


import first.iteration.endlesscreation.Model.CommentEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
   List<CommentEntity> findCommentTileEntityByTileEntity(TileEntity tileEntity);
    List<CommentEntity> findCommentTileEntityByAboveCommentId(Long commentId);

    @Query(value="DELETE FROM comment_tile WHERE above_comment_id = :parentCommentId" ,nativeQuery = true)
    void deleteEntitiesByParentCommentId(@Param("parentCommentId") Long parentCommentId);
}
