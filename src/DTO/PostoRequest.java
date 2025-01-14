package DTO;

import validator.Validator;

public record PostoRequest (
        String fila,
        String numeroPosto,
        int sala_id
) {
    public PostoRequest(String fila, String numeroPosto, int sala_id) {
        this.fila = Validator.requireNotBlank(fila);
        this.numeroPosto = Validator.requireNotBlank(numeroPosto);
        this.sala_id = (int) Validator.requireNotNull(sala_id);
    }
}
