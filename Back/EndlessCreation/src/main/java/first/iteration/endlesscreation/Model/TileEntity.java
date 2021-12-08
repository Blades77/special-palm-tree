package first.iteration.endlesscreation.Model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "tile")
public class TileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tileId;
    private String tileTitle;
    private String tileData;
    private Long ownerUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "tile_tag",
            joinColumns = @JoinColumn(name = "tile_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagEntity> tags = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_id")
    private GroupDataEntity groupDataEntity;

    @OneToMany(mappedBy = "tileEntity", cascade = CascadeType.ALL)
    private Set<CommentEntity> tileComments = new HashSet<>();

    public Long getTileId() {
        return tileId;
    }

    public void setTileId(Long tile_id) {
        this.tileId = tile_id;
    }

    public String getTileTitle() {
        return tileTitle;
    }

    public void setTileTitle(String tile_title) {
        this.tileTitle = tile_title;
    }

    public String getTileData() {
        return tileData;
    }

    public void setTileData(String tile_data) {
        this.tileData = tile_data;
    }

    public Long getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Long ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.createdAt = created_at;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updated_at) {
        this.updatedAt = updated_at;
    }

    public GroupDataEntity getGroupDataEntity() {
        return groupDataEntity;
    }

    public void setGroupDataEntity(GroupDataEntity groupDataEntity) {
        this.groupDataEntity = groupDataEntity;
    }

    public Set<CommentEntity> getTileComments() {
        return tileComments;
    }

    public void setTileComments(Set<CommentEntity> tileComments) {
        this.tileComments = tileComments;
    }

    public void addTag(TagEntity tag) {
        this.tags.add(tag);
        tag.getTiles().add(this);
    }

    public void removeTag(TagEntity tag) {
        this.tags.remove(tag);
        tag.getTiles().remove(this);
    }

    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }
}
