package entities;

import java.time.LocalDateTime;

public class Prenotazione {
    private int id;
    private LocalDateTime orarioAcquisto;
    private int spettacolo_id;
    private int utente_id;
    private int posto_id;

    public Prenotazione(int id, LocalDateTime orarioAcquisto, int spettacolo_id, int utente_id, int posto_id) {
        this.id = id;
        this.orarioAcquisto = orarioAcquisto;
        this.spettacolo_id = spettacolo_id;
        this.utente_id = utente_id;
        this.posto_id = posto_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getOrarioAcquisto() {
        return orarioAcquisto;
    }

    public void setOrarioAcquisto(LocalDateTime orarioAcquisto) {
        this.orarioAcquisto = orarioAcquisto;
    }

    public int getSpettacolo_id() {
        return spettacolo_id;
    }

    public void setSpettacolo_id(int spettacolo_id) {
        this.spettacolo_id = spettacolo_id;
    }

    public int getUtente_id() {
        return utente_id;
    }

    public void setUtente_id(int utente_id) {
        this.utente_id = utente_id;
    }

    public int getPosto_id() {
        return posto_id;
    }

    public void setPosto_id(int posto_id) {
        this.posto_id = posto_id;
    }
}
