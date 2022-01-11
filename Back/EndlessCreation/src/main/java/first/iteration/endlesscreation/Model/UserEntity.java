package first.iteration.endlesscreation.Model;


import org.apache.catalina.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appUserId;
    private String appUserName;
    private String appUserPassword;
    private String appUserEmail;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "app_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "app_user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<GroupDataEntity> groups = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<TileEntity> tiles = new HashSet<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Set<TileEntity> likedTiles = new HashSet<>();

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Set<CommentEntity> tileComments = new HashSet<>();

    public Set<CommentEntity> getTileComments() {
        return tileComments;
    }

    public void setTileComments(Set<CommentEntity> tileComments) {
        this.tileComments = tileComments;
    }

    public Set<TileEntity> getLikedTiles() {
        return likedTiles;
    }

    public void setLikedTiles(Set<TileEntity> likedTiles) {
        this.likedTiles = likedTiles;
    }

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public String getAppUserName() {
        return appUserName;
    }

    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    public String getAppUserPassword() {
        return appUserPassword;
    }

    public void setAppUserPassword(String appUserPassword) {
        this.appUserPassword = appUserPassword;
    }

    public String getAppUserEmail() {
        return appUserEmail;
    }

    public void setAppUserEmail(String appUserEmail) {
        this.appUserEmail = appUserEmail;
    }

    public Set<TileEntity> getTiles() {
        return tiles;
    }

    public void setTiles(Set<TileEntity> tiles) {
        this.tiles = tiles;
    }

    public Set<GroupDataEntity> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupDataEntity> groups) {
        this.groups = groups;
    }

    public void addRole(RoleEntity role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(RoleEntity role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public void addGroup(GroupDataEntity group) {
        this.groups.add(group);
        group.getUsers().add(this);
    }

    public void removeGroup(GroupDataEntity group) {
        this.groups.remove(group);
        group.getUsers().remove(this);
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
