package repository;


import DBConfig.DBConnection;
import DTO.SpettacoloRequest;
import entities.Spettacolo;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

public class SpettacoloRepository {
    private static final Connection connection;

    static {
        try {
            connection = DBConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Spettacolo mapResultSetToSpettacolo(ResultSet resultSet) {
        Spettacolo spettacolo = new Spettacolo();
        try {
            spettacolo.setId(resultSet.getInt("id"));
            spettacolo.setOrario(LocalDateTime.from((TemporalAccessor) resultSet.getDate("orario")));
            spettacolo.setPrezzo(resultSet.getDouble("prezzo"));
            spettacolo.setDurataInMinuti(resultSet.getInt("durata_in_minuti"));
            spettacolo.setGenere(resultSet.getString("genere"));
            spettacolo.setSala_id(resultSet.getInt("sala_id"));
            return spettacolo;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Spettacolo getById(int id) throws SQLException {
        String query = "SELECT * FROM spettacolo WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return mapResultSetToSpettacolo(resultSet);
        } else {
            throw new IllegalArgumentException("Spettacolo con id " + id + " non presente.");
        }

    }

    public static List<Spettacolo> getAllSpettacolo() throws SQLException {
        String query = "SELECT * FROM spettacolo";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Spettacolo> spettacolo = new ArrayList<>();
        while (resultSet.next()) {
            spettacolo.add(mapResultSetToSpettacolo(resultSet));
        }
        return spettacolo;
    }

    public static void insertSpettacolo(SpettacoloRequest request) throws SQLException {
        String query = "INSERT INTO spettacolo (orario,prezzo,durataInMinuti,genere,sala_id)" +
                "VALUES (?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDate(1, convertToSqlDate(request.orario()));
        statement.setDouble(2, request.prezzo());
        statement.setInt(3, request.durataInMinuti());
        statement.setString(3, request.genere());
        statement.setInt(3, request.sala_id());

    }

    public static void updateSpettacolo(int id, SpettacoloRequest request) throws SQLException {
        String query = "UPDATE spettacolo SET orario = ?, prezzo = ?, durataInMinuti =?, genere =?, sala_id =? WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDate(1, convertToSqlDate(request.orario()));
        statement.setDouble(2, request.prezzo());
        statement.setInt(2, request.durataInMinuti());
        statement.setString(2, request.genere());
        statement.setInt(2, request.sala_id());
        statement.executeQuery();
    }

    public static void deleteById(int id) throws SQLException {
        String query = "DELETE FROM spettacolo WHERE id =?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeQuery();
    }

    private static java.sql.Date convertToSqlDate(LocalDateTime dateTime) {
        return java.sql.Date.valueOf(dateTime.toLocalDate());
    }


}
