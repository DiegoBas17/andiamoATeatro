package DTO;

import validator.Validator;

import java.time.LocalDateTime;

public record SpettacoloRequest(
        LocalDateTime orario,
        double prezzo,
        int durataInMinuti,
        String genere,
        int sala_id
) {
    public SpettacoloRequest(LocalDateTime orario, double prezzo, int durataInMinuti, String genere, int sala_id) {
        this.orario = Validator.requireDateBefore(orario, LocalDateTime.now());
        this.prezzo = Validator.requirePositive(prezzo);
        this.durataInMinuti = Validator.requirePositiveInt(durataInMinuti);
        this.genere = Validator.requireNotBlank(genere);
        this.sala_id = (int) Validator.requireNotNull(sala_id);
    }
}
