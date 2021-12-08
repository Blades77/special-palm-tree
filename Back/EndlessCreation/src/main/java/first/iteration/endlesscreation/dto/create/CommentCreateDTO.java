package first.iteration.endlesscreation.dto.create;


public class CommentCreateDTO {

    private String commentContent;
    private Long aboveCommentId;
    private Long tileId;
    private Long author;

    public CommentCreateDTO(String commentContent, Long aboveCommentId, Long tileId, Long author) {
        this.commentContent = commentContent;
        this.aboveCommentId = aboveCommentId;
        this.tileId = tileId;
        this.author = author;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Long getAboveCommentId() {
        return aboveCommentId;
    }

    public void setAboveCommentId(Long aboveCommentId) {
        this.aboveCommentId = aboveCommentId;
    }

    public Long getTileId() {
        return tileId;
    }

    public void setTileId(Long tileId) {
        this.tileId = tileId;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }
}
