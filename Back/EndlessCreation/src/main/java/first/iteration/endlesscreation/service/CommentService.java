package first.iteration.endlesscreation.service;


import first.iteration.endlesscreation.Model.CommentEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.dao.CommentDAO;
import first.iteration.endlesscreation.dto.CommentDTO;
import first.iteration.endlesscreation.dto.Update.CommentUpdateDTO;
import first.iteration.endlesscreation.dto.create.CommentCreateDTO;
import first.iteration.endlesscreation.mapper.CommentMapper;
import first.iteration.endlesscreation.repository.CommentRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TileService tileService;
    private final CommentDAO commentDAO;

    public CommentService(CommentRepository commentRepository, TileService tileService,CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
        this.commentRepository = commentRepository;
        this.tileService = tileService;
    }

    public CommentEntity getCommentByEntityId(Long commentId){
        CommentEntity commentEntity = commentDAO.getCommentEntity(commentId);
        return commentEntity;
    }

    public List<CommentDTO> getCommentsByParentCommentId(Long commentId){
        List<CommentEntity> commentEntityList = commentRepository.findCommentTileEntityByParentCommentId(commentId);
        return  CommentMapper.mapToCommentDTOList(commentEntityList);
    }

    public List<CommentDTO> getCommentsForTile(Long tileId){
        TileEntity tileEntity = tileService.getTileEntityById(tileId);
        List<CommentEntity> commentEntityList = commentDAO.getCommentsForTile(tileEntity);
        return CommentMapper.mapToCommentDTOList(commentEntityList);

    }

    public void createComment (CommentCreateDTO commentCreateDTO){
        TileEntity tileEntity = tileService.getTileEntityById(commentCreateDTO.getTileId());
        commentRepository.save(CommentMapper.mapToCommentEntity(commentCreateDTO,tileEntity));
    }

    public void editCommentTile (CommentUpdateDTO commentUpdateDTO){
        CommentEntity commentEntity = getCommentByEntityId(commentUpdateDTO.getCommentId());
        commentEntity.setCommentContent(commentUpdateDTO.getCommentContent());
        commentRepository.save(commentEntity);

    }
    @Modifying
    @Transactional
    public void deleteCommentsCascade(Long commentId){
        CommentEntity commentEntity = getCommentByEntityId(commentId);
        commentRepository.deleteEntitiesByParentCommentId(commentId);
        commentRepository.delete(commentEntity);
    }

}
