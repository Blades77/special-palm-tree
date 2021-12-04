package first.iteration.endlesscreation.Model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "color")
public class ColorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long colorId;
    private String colorValueHex;
    private String colorDesc;

    @OneToMany(mappedBy = "colorEntity", fetch = FetchType.LAZY)
    private Set<TagEntity> tags = new HashSet<>();

    public long getColorId() {
        return colorId;
    }

    public void setColorId(long colorId) {
        this.colorId = colorId;
    }

    public String getColorValueHex() {
        return colorValueHex;
    }

    public void setColorValueHex(String colorValueHex) {
        this.colorValueHex = colorValueHex;
    }

    public String getColorDesc() {
        return colorDesc;
    }

    public void setColorDesc(String colorDesc) {
        this.colorDesc = colorDesc;
    }
}
