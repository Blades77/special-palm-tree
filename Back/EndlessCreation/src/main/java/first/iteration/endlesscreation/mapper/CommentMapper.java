package first.iteration.endlesscreation.mapper;

import first.iteration.endlesscreation.Model.CommentEntity;
import first.iteration.endlesscreation.Model.TileEntity;
import first.iteration.endlesscreation.Model.UserEntity;
import first.iteration.endlesscreation.dto.CommentDTO;
import first.iteration.endlesscreation.dto.create.CommentCreateDTO;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentMapper {

    public static List<CommentDTO> mapToCommentDTOList(List<CommentEntity> commentEntityList) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        if (commentEntityList.isEmpty() == false) {
            for (CommentEntity commentEntity : commentEntityList) {
                commentDTOList.add(mapToCommentTileDTO(commentEntity));
            }
        }
        return commentDTOList;
    }

    private static CommentDTO mapToCommentTileDTO(CommentEntity commentEntity){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentId(commentEntity.getCommentId());
        commentDTO.setAboveCommentId(commentEntity.getParentCommentId());
        commentDTO.setCommentContent(commentEntity.getCommentContent());
        commentDTO.setAuthor(commentEntity.getUserEntity().getAppUserId());
        commentDTO.setTileId(commentEntity.getTileEntity().getTileId());
        commentDTO.setUpdated_at(commentEntity.getUpdated_at());
        commentDTO.setCreatedAt(commentEntity.getCreated_at());
        return commentDTO;
    }

    public static CommentEntity mapToCommentEntity(CommentCreateDTO commentCreateDTO, TileEntity tileEntity, UserEntity userEntity){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setUserEntity(userEntity);
        commentEntity.setCreated_at(LocalDateTime.now());
        commentEntity.setUpdated_at(LocalDateTime.now());
        commentEntity.setCommentContent(commentCreateDTO.getCommentContent());
        commentEntity.setParentCommentId(commentCreateDTO.getAboveCommentId());
        commentEntity.setTileEntity(tileEntity);
        return commentEntity;

    }
}
