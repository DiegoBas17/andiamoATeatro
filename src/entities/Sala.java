package entities;

public class Sala {
    private int id;
    private int numeroPosti;
    private String nome;
    private int sede_id;

    public Sala(int id, int numeroPosti, String nome, int sede_id) {
        this.id = id;
        this.numeroPosti = numeroPosti;
        this.nome = nome;
        this.sede_id = sede_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroPosti() {
        return numeroPosti;
    }

    public void setNumeroPosti(int numeroPosti) {
        this.numeroPosti = numeroPosti;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSede_id() {
        return sede_id;
    }

    public void setSede_id(int sede_id) {
        this.sede_id = sede_id;
    }
}
