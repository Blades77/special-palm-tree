package first.iteration.endlesscreation.Model;


import javax.persistence.*;

@Entity
@Table(name = "comment_tile")
public class CommentTileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String commentContent;
    private Long aboveCommentId;

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
}
