package first.iteration.endlesscreation.dto;

public class ColorDTO {

    private long colorId;
    private String colorValueHex;
    private String colorDesc;

    public ColorDTO(){}

    public ColorDTO(long colorId, String colorValueHex, String colorDesc) {
        this.colorId = colorId;
        this.colorValueHex = colorValueHex;
        this.colorDesc = colorDesc;
    }

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
