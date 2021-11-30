package first.iteration.endlesscreation.dto.create;

public class TagCreateDTO {

    private String tagName;
    private String tagColor;

    public TagCreateDTO(String tag_name, String tag_color) {
        this.tagName = tag_name;
        this.tagColor = tag_color;
    }

    public TagCreateDTO(){}

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
        return "TagCreateDTO{" +
                ", tag_name='" + tagName + '\'' +
                ", tag_color='" + tagColor + '\'' +
                '}';
    }
}
