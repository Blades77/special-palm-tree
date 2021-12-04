package first.iteration.endlesscreation.dto.create;

public class ColorCreateDTO {

    private String colorValueHex;
    private String colorDesc;

    ColorCreateDTO(){}

    public ColorCreateDTO( String colorValueHex, String colorDesc) {
        this.colorValueHex = colorValueHex;
        this.colorDesc = colorDesc;
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
