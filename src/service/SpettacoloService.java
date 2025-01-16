package service;

import DTO.SpettacoloRequest;
import entities.Spettacolo;
import repository.SpettacoloRepository;

import java.sql.SQLException;
import java.util.List;

public class SpettacoloService {
    public static List<Spettacolo> getAllSpettacolo() throws SQLException {
        return SpettacoloRepository.getAllSpettacolo();
    }

    public static void insertSpettacolo(SpettacoloRequest request) throws SQLException {
        SpettacoloRepository.insertSpettacolo(request);
    }

    public Spettacolo getSpettacoloById(int id) throws SQLException {
        return SpettacoloRepository.getById(id);
    }

    public void updateSpettacolo(int id, SpettacoloRequest request) throws SQLException {
        SpettacoloRepository.updateSpettacolo(id, request);
    }

    public void deleteSpettacoloById(int id) throws SQLException {
        SpettacoloRepository.deleteById(id);
    }
}
