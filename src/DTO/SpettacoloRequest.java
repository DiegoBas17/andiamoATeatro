package DTO;

import validator.Validator;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public record SpettacoloRequest(
        Date orario,
        double prezzo,
        int durataInMinuti,
        String genere,
        int sala_id
) {
    public SpettacoloRequest(Date orario, double prezzo, int durataInMinuti, String genere, int sala_id) {
        this.orario = Validator.requireDateBefore(orario, Date.from(Instant.from(LocalDate.now())));
        this.prezzo = Validator.requirePositive(prezzo);
        this.durataInMinuti = Validator.requirePositiveInt(durataInMinuti);
        this.genere = Validator.requireNotBlank(genere);
        this.sala_id = (int) Validator.requireNotNull(sala_id);
    }
}
