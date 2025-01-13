package repository;

import DTO.SedeRequest;
import configuration.DBConnection;
import entities.Sede;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SedeRepository {

    private static final Connection connection;

    static {
        connection = DBConnection.getConnection();
    }

    public static Sede mapResultSetToSede(ResultSet resultSet) throws SQLException {
        Sede sede = new Sede();

        sede.setId(resultSet.getInt("id"));
        sede.setComune(resultSet.getString("comune"));
        sede.setIndirizzo(resultSet.getString("indirizzo"));
        sede.setNomeSpettacolo(resultSet.getString("nomeSpettacolo"));
        sede.setCoperto(resultSet.getBoolean("isCoperto"));
        return sede;
    }


    public static Sede getById(int id) throws SQLException {
        String query = "SELECT * FROM sede WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return mapResultSetToSede(resultSet);
        } else
            throw new IllegalArgumentException("Sede con id " + id + " non presente");
    }

    public static List<Sede> findAll() throws SQLException {
        String query = "SELECT * FROM sede";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Sede> sedi = new ArrayList<>();
        while (resultSet.next()) {
            sedi.add(mapResultSetToSede(resultSet));
        }
        return sedi;
    }

    public static void insertSede(SedeRequest request) throws SQLException {
        String query = "INSERT INTO sede (indirizzo, nomeSpettacolo, comune, isCoperto)" +
                "VALUES (?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.indirizzo());
        statement.setString(2, request.nomeSpettacolo());
        statement.setString(3, request.comune());
        statement.setBoolean(4, request.isCoperto());
    }

    public static void updateSede(int id, SedeRequest request) throws SQLException {
        String query = "UPDATE sede SET indirizzo = ?, nomeSpettacolo = ?, comune = ?, isCoperto = ? WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.indirizzo());
        statement.setString(2, request.nomeSpettacolo());
        statement.setString(3, request.comune());
        statement.setBoolean(4, request.isCoperto());
        statement.executeQuery();
    }

    public static void deleteById(int id) throws SQLException {
        String query = "DELETE FROM sede WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeQuery();
    }

}
