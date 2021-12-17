package first.iteration.endlesscreation.controller;


import first.iteration.endlesscreation.configuration.SpringFoxConfig;
import first.iteration.endlesscreation.dto.CommentDTO;
import first.iteration.endlesscreation.dto.Update.CommentUpdateDTO;
import first.iteration.endlesscreation.dto.create.CommentCreateDTO;
import first.iteration.endlesscreation.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = {SpringFoxConfig.comment})
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "Gets comments by tile id")
    @GetMapping("/comment/{id}")
    private List<CommentDTO> getCommentsForTile(@ApiParam(value = "Id of comment", example = "1", required = true) @PathVariable Long id){
        return commentService.getCommentsForTile(id);
    }

        @ApiOperation(value = "Gets comments by parentComment id")
    @GetMapping("/comment/parrent/{id}")
    private List<CommentDTO> getCommentsByParentCommentId(@ApiParam(value = "Id of parent comment", example = "1", required = true) @PathVariable Long id){
        return commentService.getCommentsByParentCommentId(id);
    }

    @ApiOperation(value = "Add comment to tile")
    @PostMapping("/comment")
    private void createComment(@RequestBody CommentCreateDTO commentCreateDTO) {
        commentService.createComment(commentCreateDTO);
    }

    @ApiOperation(value = "Eddit comment")
    @PutMapping("/comment")
    private void editComment(@RequestBody CommentUpdateDTO commentUpdateDTO) {
        commentService.editCommentTile(commentUpdateDTO);
    }

    @ApiOperation(value = "Deletes comment")
    @DeleteMapping("/comment/{id}")
    private void deleteComment(@ApiParam(value = "Id of comment", example = "1", required = true) @PathVariable Long id) {
        commentService.deleteCommentsCascade(id);
    }
}
