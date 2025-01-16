package repository;

import DBConfig.DBConnection;
import DTO.SalaRequest;
import entities.Sala;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaRepository {

    private static final Connection connection;

    static {
        try {
            connection = DBConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Sala mapResultSetToSala(ResultSet resultSet) {
        Sala sala = new Sala();
        try {
            sala.setId(resultSet.getInt("id"));
            sala.setNome(resultSet.getString("nome"));
            sala.setNumeroPosti(resultSet.getInt("numero_posti"));
            sala.setSede_id(resultSet.getInt("sede_id"));
            return sala;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Sala getById(int id) throws SQLException {
        String query = "SELECT * FROM sala WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return mapResultSetToSala(resultSet);
        } else
            throw new IllegalArgumentException("Sala con id " + id + " non presente");
    }

    public static List<Sala> getAllSala() throws SQLException {
        String query = "SELECT * FROM sala";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Sala> sala = new ArrayList<>();
        while (resultSet.next()) {
            sala.add(mapResultSetToSala(resultSet));
        }
        return sala;
    }

    public static void insertSala(SalaRequest request) throws SQLException {
        String query = "INSERT INTO sala (nome,numeroPosti,sede_id)" +
                "VALUES (?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.nome());
        statement.setInt(2, request.numeroPosti());
        statement.setInt(3, request.sede_id());

    }


    public static void updateSala(int id, SalaRequest request) throws SQLException {
        String query = "UPDATE sala SET nome = ?, cognome = ?, indirizzo = ?, email = ?, cellulare = ? WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.nome());
        statement.setInt(2, request.numeroPosti());
        statement.setInt(3, request.sede_id());
        statement.executeQuery();
    }

    public static void deleteById(int id) throws SQLException {
        String query = "DELETE FROM sala WHERE id =?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeQuery();
    }
}
