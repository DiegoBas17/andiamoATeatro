package repository;

import DTO.PostoRequest;
import configuration.DBConnection;
import entities.Posto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostoRepository {
    private static final Connection connection;

    static {
        connection = DBConnection.getConnection();
    }

    private static Posto mapResultSetToPosto(ResultSet resultSet) {
        Posto posto = new Posto();
        try {
            posto.setId(resultSet.getInt("id"));
            posto.setFila(resultSet.getString("fila"));
            posto.setNumeroPosto(resultSet.getString("numeroPosto"));
            posto.setSala_id(resultSet.getInt("sala_id"));
            return posto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Posto getById (int id) throws SQLException {
        String query = "SELECT * FROM posto WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
    if (resultSet.next()) {
            return mapResultSetToPosto(resultSet);
        } else {
            throw  new IllegalArgumentException("Posto con id " + id + " non presente.");
    }

    }
    public static List<Posto> getAllSala() throws SQLException {
        String query = "SELECT * FROM posto";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Posto> posto = new ArrayList<>();
        while (resultSet.next()) {
            posto.add(mapResultSetToPosto(resultSet));
        }
        return posto;
    }

    public static void insertPosto(PostoRequest request) throws SQLException {
        String query = "INSERT INTO posto (fila,numeroPosto,sala_id)" +
                "VALUES (?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.fila());
        statement.setString(2, request.numeroPosto());
        statement.setInt(3, request.sala_id());

    }

    public static void updateSala(int id, PostoRequest request) throws SQLException {
        String query = "UPDATE posto SET fila = ?, numeroPosto = ? WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.fila());
        statement.setString(2, request.numeroPosto());
        statement.executeQuery();
    }

    public static void deleteById(int id) throws SQLException {
        String query = "DELETE FROM posto WHERE id =?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeQuery();
    }
}
