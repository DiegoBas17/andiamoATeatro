package service;

import DTO.PostoRequest;
import entities.Posto;
import repository.PostoRepository;

import java.sql.SQLException;
import java.util.List;

public class PostoService {

    public Posto getSalaById(int id) throws SQLException {
        return PostoRepository.getById(id);
    }

    public List<Posto> getAllSale() throws SQLException {
        return PostoRepository.getAllPosto();
    }

    public void insertSala(PostoRequest request) throws SQLException {
        PostoRepository.insertPosto(request);
    }

    public void updateSala(int id, PostoRequest request) throws SQLException {
        PostoRepository.updatePosto(id, request);
    }

    public void deleteSalaById(int id) throws SQLException {
        PostoRepository.deleteById(id);
    }
}
