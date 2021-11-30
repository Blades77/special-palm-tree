package first.iteration.endlesscreation.dto;

public class TagDTO {

    private Long tagId;
    private String tagName;
    private String tagColor;

    public TagDTO(Long tagId, String tagName, String tagColor) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.tagColor = tagColor;
    }

    public TagDTO(){}

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagColor() {
        return tagColor;
    }

    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }

    @Override
    public String toString() {
        return "TagDTO{" +
                "tag_id=" + tagId +
                ", tag_name='" + tagName + '\'' +
                ", tag_color='" + tagColor + '\'' +
                '}';
    }
}
