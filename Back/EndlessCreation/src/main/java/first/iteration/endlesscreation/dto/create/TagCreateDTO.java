package first.iteration.endlesscreation.dto.create;


public class TagCreateDTO {

    private String tagName;
    private ColorCreateDTO colorCreateDTO;

    public TagCreateDTO(String tagName, ColorCreateDTO colorCreateDTO) {
        this.tagName = tagName;
        this.colorCreateDTO = colorCreateDTO;

    }

    public TagCreateDTO(){}

    public ColorCreateDTO getColorCreateDTO() {
        return colorCreateDTO;
    }

    public void setColorCreateDTO(ColorCreateDTO colorCreateDTO) {
        this.colorCreateDTO = colorCreateDTO;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "TagCreateDTO{" +
                ", tag_name='" + tagName + '\'' +
                '}';
    }
}
