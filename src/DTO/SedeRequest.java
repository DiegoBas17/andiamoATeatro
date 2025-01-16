package DTO;

import validator.Validator;

public record SedeRequest(
        String indirizzo,
        String nomeSpettacolo,
        String comune,
        Boolean isCoperto
) {
    public SedeRequest(String indirizzo, String nomeSpettacolo, String comune, Boolean isCoperto) {
        this.indirizzo = Validator.requireNotBlank(indirizzo);
        this.nomeSpettacolo = Validator.requireNotBlank(nomeSpettacolo);
        this.comune = Validator.requireNotBlank(comune);
        this.isCoperto = isCoperto;
    }
}
