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

    @ManyToMany(mappedBy = "tags")
    private Set<TileEntity> tiles = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="color_id")
    private ColorEntity colorEntity;

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

    public Set<TileEntity> getTiles() {
        return tiles;
    }

    public void setTiles(Set<TileEntity> tiles) {
        this.tiles = tiles;
    }

    public ColorEntity getColorEntity() {
        return colorEntity;
    }

    public void setColorEntity(ColorEntity colorEntity) {
        this.colorEntity = colorEntity;
    }
}


