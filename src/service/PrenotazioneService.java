package service;

import DTO.PrenotazioneRequest;
import entities.Prenotazione;
import repository.PrenotazioneRepository;

import java.sql.SQLException;
import java.util.List;

public class PrenotazioneService {

    public Prenotazione getPrenotazioneById(int id) throws SQLException {
        return PrenotazioneRepository.getById(id);
    }

    public List<Prenotazione> getAllPrenotazioni() throws SQLException {
        return PrenotazioneRepository.getAllPrenotazioni();
    }

    public void insertPrenotazione(PrenotazioneRequest request) throws SQLException {
        PrenotazioneRepository.insertPrenotazione(request);
    }

    public void deletePrenotazioneById(int id) throws SQLException {
        PrenotazioneRepository.deleteById(id);
    }

}
