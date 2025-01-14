package DTO;

import validator.Validator;

import java.time.LocalDateTime;

public record PrenotazioneRequest (
        LocalDateTime orarioAcquisto,
        int spettacolo_id,
        int utente_id,
        int posto_id
){
    public PrenotazioneRequest(LocalDateTime orarioAcquisto, int spettacolo_id, int utente_id, int posto_id) {
        this.orarioAcquisto = LocalDateTime.now();
        this.spettacolo_id = (int) Validator.requireNotNull(spettacolo_id);
        this.utente_id = (int) Validator.requireNotNull(utente_id);
        this.posto_id = (int) Validator.requireNotNull(posto_id);
    }
}
