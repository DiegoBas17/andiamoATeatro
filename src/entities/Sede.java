package entities;

public class Sede {
    private int id;
    private TipoSede tipoSede;
    private String indirizzo;
    private String nomeSpettacolo;
    private String comune;
    private Boolean isCoperto;

    public Sede(int id, TipoSede tipoSede, String indirizzo, String nomeSpettacolo, String comune, Boolean isCoperto) {
        this.id = id;
        this.tipoSede = tipoSede;
        this.indirizzo = indirizzo;
        this.nomeSpettacolo = nomeSpettacolo;
        this.comune = comune;
        this.isCoperto = isCoperto;
    }

    public TipoSede getTipoSede() {
        return tipoSede;
    }

    public void setTipoSede(TipoSede tipoSede) {
        this.tipoSede = tipoSede;
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
