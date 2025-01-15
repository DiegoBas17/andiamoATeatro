package entities;

import java.time.LocalDateTime;

public class Spettacolo {
    private int id;
    private LocalDateTime orario;
    private double prezzo;
    private int durataInMinuti;
    private String genere;
    private int sala_id;

    public Spettacolo(int id, LocalDateTime orario, double prezzo, int durataInMinuti, String genere, int sala_id) {
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

    public LocalDateTime getOrario() {
        return orario;
    }

    public void setOrario(LocalDateTime orario) {
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

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public int getSala_id() {
        return sala_id;
    }

    public void setSala_id(int sala_id) {
        this.sala_id = sala_id;
    }
}
