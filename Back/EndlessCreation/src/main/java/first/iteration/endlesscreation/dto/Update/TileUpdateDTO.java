package first.iteration.endlesscreation.dto.Update;


public class TileUpdateDTO {

    private long tileId;
    private String tileTitle;
    private  String tileData;

    public TileUpdateDTO(long tileId, String tileTitle, String tileData) {
        this.tileId = tileId;
        this.tileTitle = tileTitle;
        this.tileData = tileData;
    }

    public long getTileId() {
        return tileId;
    }

    public void setTileId(long tileId) {
        this.tileId = tileId;
    }

    public String getTileTitle() {
        return tileTitle;
    }

    public void setTileTitle(String tileTitle) {
        this.tileTitle = tileTitle;
    }

    public String getTileData() {
        return tileData;
    }

    public void setTileData(String tileData) {
        this.tileData = tileData;
    }
}
