package service;

import DTO.SalaRequest;
import entities.Sala;
import repository.SalaRepository;

import java.sql.SQLException;
import java.util.List;

public class SalaService {


    public Sala getSalaById(int id) throws SQLException {
        return SalaRepository.getById(id);
    }

    public List<Sala> getAllSale() throws SQLException {
        return SalaRepository.getAllSala();
    }

    public void insertSala(SalaRequest request) throws SQLException {
        SalaRepository.insertSala(request);
    }

    public void updateSala(int id, SalaRequest request) throws SQLException {
        SalaRepository.updateSala(id, request);
    }

    public void deleteSalaById(int id) throws SQLException {
        SalaRepository.deleteById(id);
    }

}
