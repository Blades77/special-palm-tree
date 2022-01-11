package first.iteration.endlesscreation.Model;

import org.springframework.security.core.userdetails.User;

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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "tile_tag",
            joinColumns = @JoinColumn(name = "tile_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagEntity> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "tile_like",
            joinColumns = @JoinColumn(name = "tile_id"),
            inverseJoinColumns = @JoinColumn(name = "app_user_id"))
    private Set<UserEntity> users = new HashSet<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_id")
    private GroupDataEntity groupDataEntity;

    @OneToMany(mappedBy = "tileEntity", cascade = CascadeType.ALL)
    private Set<CommentEntity> tileComments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_user_id")
    private UserEntity userEntity;

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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
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

    public void addUser(UserEntity user) {
        this.users.add(user);
        user.getLikedTiles().add(this);
    }

    public void removeUser(UserEntity user) {
        this.users.remove(user);
        user.getLikedTiles().remove(this);
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }
}
