package first.iteration.endlesscreation.Model;


import javax.persistence.*;

@Entity
@Table(name="Ksiazki")
public class ksiazkaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ksiazkaID;
    private String tytul;
    private int liczbaStron;

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
}
