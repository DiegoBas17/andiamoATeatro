package repository;

import DTO.UtenteRequest;
import configuration.DBConnection;
import entities.Sala;
import entities.Utente;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaRepository
{
    private static final Connection connection;

    static {
        connection = DBConnection.getConnection();
    }

    private static Sala mapResultSetToSala(ResultSet resultSet) throws SQLException {
        Sala sala = new Sala();

        sala.setId(resultSet.getInt("id"));
        sala.setNumeroPosti(resultSet.getInt("NumeroPosti"));
        sala.setNome(resultSet.getString("nome"));
        sala.setSede_id(resultSet.getInt("sede_id"));
        return sala;
    }

    private static Sala getById(int id) throws SQLException {
        String query = "SELECT * FROM sede WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return mapResultSetToSala(resultSet);
        } else
            throw new IllegalArgumentException("Sala con id " + id + " non presente");
    }

    public static List<Sala> getAllSala() throws SQLException
    {
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
        String query = "INSERT INTO utente (nome,cognome,indirizzo,email,cellulare)" +
                "VALUES (?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.nome());
        statement.setString(2, request.cognome());
        statement.setString(3, request.indirizzo());
        statement.setString(4, request.email());
        statement.setString(5, request.cellulare());
    }


    public static void updateUtenti(int id, UtenteRequest request) throws SQLException {
        String query = "UPDATE utente SET nome = ?, cognome = ?, indirizzo = ?, email = ?, cellulare = ? WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.nome());
        statement.setString(2, request.cognome());
        statement.setString(3, request.indirizzo());
        statement.setString(4, request.email());
        statement.setString(5, request.cellulare());
        statement.executeQuery();
    }

    public static void deleteById(int id) throws SQLException {
        String query = "DELETE FROM utente WHERE id =?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeQuery();
    }

}
