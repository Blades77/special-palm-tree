package first.iteration.endlesscreation.dto;

public class ksiazkaDTO {
    private Long ksiazkaID;
    private String tytul;
    private int liczbaStron;

    public ksiazkaDTO(Long ksiazkaID, String tytul, int liczbaStron){
        this.ksiazkaID = ksiazkaID;
        this.tytul = tytul;
        this.liczbaStron = liczbaStron;
    }

    public ksiazkaDTO(){}

    public Long getKsiazkaID() {
        return ksiazkaID;
    }

    public void setKsiazkaID(Long ksiazkaID) {
        this.ksiazkaID = ksiazkaID;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public int getLiczbaStron() {
        return liczbaStron;
    }

    public void setLiczbaStron(int liczbaStron) {
        this.liczbaStron = liczbaStron;
    }

    @Override
    public String toString() {
        return "ksiazkaDTO{" +
                "ksiazkaID=" + ksiazkaID +
                ", tytul='" + tytul + '\'' +
                ", liczbaStron=" + liczbaStron +
                '}';
    }
}
