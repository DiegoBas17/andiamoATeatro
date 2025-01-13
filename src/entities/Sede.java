package entities;

public class Sede {
    private int id;
    private String indirizzo;
    private String nomeSpettacolo;
    private String comune;
    private Boolean isCoperto;

    public Sede(int id, String indirizzo, String nomeSpettacolo, String comune, Boolean isCoperto) {
        this.id = id;
        this.indirizzo = indirizzo;
        this.nomeSpettacolo = nomeSpettacolo;
        this.comune = comune;
        this.isCoperto = isCoperto;
    }

    public Sede() {
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getNomeSpettacolo() {
        return nomeSpettacolo;
    }

    public void setNomeSpettacolo(String nomeSpettacolo) {
        this.nomeSpettacolo = nomeSpettacolo;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public Boolean getCoperto() {
        return isCoperto;
    }

    public void setCoperto(Boolean coperto) {
        isCoperto = coperto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
