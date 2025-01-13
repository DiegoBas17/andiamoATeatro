package DTO;

import validator.Validator;

public record SedeRequest(
        int id,
        String indirizzo,
        String nomeSpettacolo,
        String comune,
        Boolean isCoperto
) {
    public SedeRequest(int id, String indirizzo, String nomeSpettacolo, String comune, Boolean isCoperto) {
        this.id = id;
        this.indirizzo = Validator.requireNotBlank(indirizzo);
        this.nomeSpettacolo = Validator.requireNotBlank(nomeSpettacolo);
        this.comune = Validator.requireNotBlank(comune);
        this.isCoperto = isCoperto;
    }
}
