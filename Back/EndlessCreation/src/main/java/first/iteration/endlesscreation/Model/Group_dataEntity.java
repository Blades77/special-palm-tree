package first.iteration.endlesscreation.Model;


import javax.persistence.*;
import javax.transaction.UserTransaction;
import java.util.Set;

@Entity
@Table(name = "group_data")
public class Group_dataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long group_id;
    private String group_name;
    private String group_type;

    @OneToMany(mappedBy = "group_dataEntity", fetch = FetchType.LAZY)
    private Set<TileEntity> tiles;

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_type() {
        return group_type;
    }

    public void setGroup_type(String group_type) {
        this.group_type = group_type;
    }
}
