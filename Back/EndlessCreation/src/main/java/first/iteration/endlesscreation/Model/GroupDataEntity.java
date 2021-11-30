package first.iteration.endlesscreation.Model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "group_data")
public class GroupDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;
    private String groupName;
    private String groupType;

    @OneToMany(mappedBy = "groupDataEntity", fetch = FetchType.LAZY)
    private Set<TileEntity> tiles = new HashSet<>();


    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String group_name) {
        this.groupName = group_name;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String group_type) {
        this.groupType = group_type;
    }
}
