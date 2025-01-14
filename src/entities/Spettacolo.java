package entities;

import java.util.Date;

public class Spettacolo {
    private int id;
    private Date orario;
    private double prezzo;
    private int durataInMinuti;
    private Genere genere;
    private int sala_id;

    public Spettacolo(int id, Date orario, double prezzo, int durataInMinuti, Genere genere, int sala_id) {
        this.id = id;
        this.orario = orario;
        this.prezzo = prezzo;
        this.durataInMinuti = durataInMinuti;
        this.genere = genere;
        this.sala_id = sala_id;
    }

    public Spettacolo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOrario() {
        return orario;
    }

    public void setOrario(Date orario) {
        this.orario = orario;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public int getDurataInMinuti() {
        return durataInMinuti;
    }

    public void setDurataInMinuti(int durataInMinuti) {
        this.durataInMinuti = durataInMinuti;
    }

    public Genere getGenere() {
        return genere;
    }

    public void setGenere(Genere genere) {
        this.genere = genere;
    }

    public int getSala_id() {
        return sala_id;
    }

    public void setSala_id(int sala_id) {
        this.sala_id = sala_id;
    }
}
