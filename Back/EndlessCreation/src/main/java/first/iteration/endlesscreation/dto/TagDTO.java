package first.iteration.endlesscreation.dto;

public class TagDTO {

    private Long tagId;
    private String tagName;
    private ColorDTO colorDTO;


    public TagDTO(Long tagId, String tagName, ColorDTO colorDTO) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.colorDTO = colorDTO;

    }

    public ColorDTO getColorDTO() {
        return colorDTO;
    }

    public void setColorDTO(ColorDTO colorDTO) {
        this.colorDTO = colorDTO;
    }

    public TagDTO() {
    }

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


}
