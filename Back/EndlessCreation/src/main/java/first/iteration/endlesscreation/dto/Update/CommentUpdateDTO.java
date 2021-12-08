package first.iteration.endlesscreation.dto.Update;


public class CommentUpdateDTO {

    private Long commentId;
    private String commentContent;

    public CommentUpdateDTO(String commentContent,Long commentId) {
        this.commentContent = commentContent;
        this.commentId = commentId;

    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
}