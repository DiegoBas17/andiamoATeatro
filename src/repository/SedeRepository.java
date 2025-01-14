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

    private static Sede mapResultSetToSede(ResultSet resultSet) throws SQLException {
        Sede sede = new Sede();

        sede.setId(resultSet.getInt("id"));
        sede.setComune(resultSet.getString("comune"));
        sede.setIndirizzo(resultSet.getString("indirizzo"));
        sede.setNomeSpettacolo(resultSet.getString("nomeSpettacolo"));
        sede.setCoperto(resultSet.getBoolean("isCoperto"));
        return sede;
    }


    private static Sede getById(int id) throws SQLException {
        String query = "SELECT * FROM sede WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return mapResultSetToSede(resultSet);
        } else
            throw new IllegalArgumentException("Sede con id " + id + " non presente");
    }

    private static List<Sede> findAll() throws SQLException {
        String query = "SELECT * FROM sede";

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Sede> sedi = new ArrayList<>();
        while (resultSet.next()) {
            sedi.add(mapResultSetToSede(resultSet));
        }
        return sedi;
    }

    private static void insertSede(SedeRequest request) {
//CIAO
    }
}
