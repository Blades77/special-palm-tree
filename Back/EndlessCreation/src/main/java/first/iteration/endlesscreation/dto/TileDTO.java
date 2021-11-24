package first.iteration.endlesscreation.dto;

public class TileDTO {
    private Long tile_id;
    private String tile_title;
    private String tile_data;
    private Long owner_user_id;
    private Long group_id;

    public TileDTO(){}

    public TileDTO(Long tile_id,String tile_title,String tile_data,Long owner_user_id,Long group_id){
        this.tile_id = tile_id;
        this.tile_title = tile_title;
        this.tile_data = tile_data;
        this.owner_user_id = owner_user_id;
        this.group_id = group_id;
    }

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

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }
}
