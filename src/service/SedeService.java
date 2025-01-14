package service;

import DTO.SedeRequest;
import entities.Sede;
import repository.SedeRepository;

import java.sql.SQLException;
import java.util.List;

public class SedeService {

    public static Sede getByIdSede(int id) throws SQLException {
        return SedeRepository.getById(id);
    }

    public static List<Sede> findAllSedi() throws SQLException {
        return SedeRepository.findAll();
    }

    public static void insertSede(SedeRequest request) throws SQLException {
        SedeRepository.insertSede(request);
    }

    public static void UpdateSede(int id, SedeRequest request) throws SQLException {
        SedeRepository.updateSede(id, request);
    }

    public static void deleteByIdSede(int id) throws SQLException {
        SedeRepository.deleteById(id);
    }
}
