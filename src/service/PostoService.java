package service;

import DTO.PostoRequest;
import entities.Posto;
import repository.PostoRepository;

import java.sql.SQLException;
import java.util.List;

public class PostoService {

    public Posto getPostoById(int id) throws SQLException {
        return PostoRepository.getById(id);
    }

    public List<Posto> getAllPosto() throws SQLException {
        return PostoRepository.getAllPosto();
    }

    public void insertPosto(PostoRequest request) throws SQLException {
        PostoRepository.insertPosto(request);
    }

    public void updatePosto(int id, PostoRequest request) throws SQLException {
        PostoRepository.updatePosto(id, request);
    }

    public void deletePostoById(int id) throws SQLException {
        PostoRepository.deleteById(id);
    }
}
