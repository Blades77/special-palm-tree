package first.iteration.endlesscreation.dao;


import first.iteration.endlesscreation.Model.CommentEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.exception.ResourceNotFoundException;
import first.iteration.endlesscreation.repository.CommentRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentDAO {

    private final CommentRepository commentRepository;

    public CommentDAO(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<CommentEntity> getCommentsForTile(TileEntity tileEntity){
        return commentRepository.findCommentTileEntityByTileEntity(tileEntity);
    }

    public CommentEntity getCommentEntity(Long id){
        return  commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find specified comment"));
    }
}
