package repository;

import DBConfig.DBConnection;
import DTO.SedeRequest;
import entities.Sede;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SedeRepository {
    private static final Connection connection;

    static {
        try {
            connection = DBConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Sede mapResultSetToSede(ResultSet resultSet) throws SQLException {
        Sede sede = new Sede();
        sede.setId(resultSet.getInt("id"));
        sede.setComune(resultSet.getString("comune"));
        sede.setIndirizzo(resultSet.getString("indirizzo"));
        sede.setNomeSpettacolo(resultSet.getString("nome_spettacolo"));
        sede.setCoperto(resultSet.getBoolean("is_coperto"));
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
        String query = "INSERT INTO SEDE (comune,indirizzo,nome_spettacolo,is_coperto)" +
                "VALUES (?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.comune());
        statement.setString(2, request.indirizzo());
        statement.setString(3, request.nomeSpettacolo());
        statement.setBoolean(4, request.isCoperto());
        statement.executeUpdate();
    }

    public static void updateSede(int id, SedeRequest request) throws SQLException {
        String query = "UPDATE sede SET  comune = ?, indirizzo = ?, nome_spettacolo = ?, is_coperto = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.comune());
        statement.setString(2, request.indirizzo());
        statement.setString(3, request.nomeSpettacolo());
        statement.setBoolean(4, request.isCoperto());
        statement.setInt(5, id);
        statement.executeQuery();
    }

    public static void deleteById(int id) throws SQLException {
        String query = "DELETE FROM sede WHERE id =?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeQuery();
    }
}
