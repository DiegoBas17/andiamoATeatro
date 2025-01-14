package DTO;

import validator.Validator;

public record SalaRequest(
        int numeroPosti,
        String nome,
        int sede_id
) {
    public SalaRequest(int numeroPosti, String nome, int sede_id) {
        this.numeroPosti = Validator.requirePositiveInt(numeroPosti);
        this.nome = Validator.requireNotBlank(nome);
        this.sede_id = (int) Validator.requireNotNull(sede_id);
    }
}
