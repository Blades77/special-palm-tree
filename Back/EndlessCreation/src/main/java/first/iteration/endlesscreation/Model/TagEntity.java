package first.iteration.endlesscreation.Model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name ="tag")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;
    private String tagName;
    private String tagColor;

    @ManyToMany(mappedBy = "tags")
    private Set<TileEntity> tiles = new HashSet<>();

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tag_id) {
        this.tagId = tag_id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tag_name) {
        this.tagName = tag_name;
    }

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tag_color) {
        this.tagColor = tag_color;
    }

    public Set<TileEntity> getTiles() {
        return tiles;
    }

    public void setTiles(Set<TileEntity> tiles) {
        this.tiles = tiles;
    }
}


