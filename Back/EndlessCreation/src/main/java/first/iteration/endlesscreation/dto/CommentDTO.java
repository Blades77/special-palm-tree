package first.iteration.endlesscreation.dto;


import java.time.LocalDateTime;

public class CommentDTO {

    private Long commentId;
    private String commentContent;
    private Long aboveCommentId;
    private Long tileId;
    private Long author;
    private LocalDateTime createdAt;
    private LocalDateTime updated_at;

    public CommentDTO(Long commentId, String commentContent, Long aboveCommentId, Long tileId, Long author, LocalDateTime createdAt, LocalDateTime updated_at) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.aboveCommentId = aboveCommentId;
        this.tileId = tileId;
        this.author = author;
        this.createdAt = createdAt;
        this.updated_at = updated_at;
    }

    public CommentDTO(){}

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
