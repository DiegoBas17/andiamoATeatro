package service;

import DTO.PostoRequest;
import entities.Posto;
import repository.PostoRepository;

import java.sql.SQLException;
import java.util.List;

public class PostoService {

    public static Posto getPostoById(int id) throws SQLException {
        return PostoRepository.getById(id);
    }

    public static List<Posto> getAllPosto() throws SQLException {
        return PostoRepository.getAllPosto();
    }

    public static void insertPosto(PostoRequest request) throws SQLException {
        PostoRepository.insertPosto(request);
    }

    public static void updatePosto(int id, PostoRequest request) throws SQLException {
        PostoRepository.updatePosto(id, request);
    }

    public static void deletePostoById(int id) throws SQLException {
        PostoRepository.deleteById(id);
    }
}
