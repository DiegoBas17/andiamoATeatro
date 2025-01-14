package service;

import DTO.UtenteRequest;
import entities.Utente;
import repository.UtenteRepository;

import java.sql.SQLException;
import java.util.List;

public class UtenteService {


    public Utente getUtenteById(int id) throws SQLException {
        return UtenteRepository.getById(id);
    }

    public List<Utente> getAllUtenti() throws SQLException {
        return UtenteRepository.getAllUtenti();
    }

    public void insertUtente(UtenteRequest request) throws SQLException {
        UtenteRepository.insertUtenti(request);
    }

    public void updateUtente(int id, UtenteRequest request) throws SQLException {
        UtenteRepository.updateUtenti(id, request);
    }

    public void deleteUtenteById(int id) throws SQLException {
        UtenteRepository.deleteById(id);
    }
}
