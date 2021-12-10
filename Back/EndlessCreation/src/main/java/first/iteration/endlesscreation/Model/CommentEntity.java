package first.iteration.endlesscreation.Model;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment_tile")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String commentContent;
    private Long aboveCommentId;
    private Long author;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tile_id")
    private TileEntity tileEntity;

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

    public TileEntity getTileEntity() {
        return tileEntity;
    }

    public void setTileEntity(TileEntity tileEntity) {
        this.tileEntity = tileEntity;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}