package entities;

public class Posto {
    private int id;
    private String fila;
    private String numeroPosto;
    private int sala_id;

    public Posto(int id, String fila, String numeroPosto, int sala_id) {
        this.id = id;
        this.fila = fila;
        this.numeroPosto = numeroPosto;
        this.sala_id = sala_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public String getNumeroPosto() {
        return numeroPosto;
    }

    public void setNumeroPosto(String numeroPosto) {
        this.numeroPosto = numeroPosto;
    }

    public int getSala_id() {
        return sala_id;
    }

    public void setSala_id(int sala_id) {
        this.sala_id = sala_id;
    }
}
