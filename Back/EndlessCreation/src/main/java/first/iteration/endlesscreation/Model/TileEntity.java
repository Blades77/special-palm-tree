package first.iteration.endlesscreation.Model;

import javax.persistence.*;

@Entity
@Table(name = "tile")
public class TileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tile_id;
    private String tile_title;
    private String tile_data;
    private Long owner_user_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="group_id")
    private Group_dataEntity group_dataEntity;

    public Long getTile_id() {
        return tile_id;
    }

    public void setTile_id(Long tile_id) {
        this.tile_id = tile_id;
    }

    public String getTile_title() {
        return tile_title;
    }

    public void setTile_title(String tile_title) {
        this.tile_title = tile_title;
    }

    public String getTile_data() {
        return tile_data;
    }

    public void setTile_data(String tile_data) {
        this.tile_data = tile_data;
    }

    public Long getOwner_user_id() {
        return owner_user_id;
    }

    public void setOwner_user_id(Long owner_user_id) {
        this.owner_user_id = owner_user_id;
    }

    public Group_dataEntity getGroup_dataEntity() {
        return group_dataEntity;
    }

    public void setGroup_dataEntity(Group_dataEntity group_dataEntity) {
        this.group_dataEntity = group_dataEntity;
    }


}
