package repository;

import DBConfig.DBConnection;
import DTO.PostoRequest;
import entities.Posto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostoRepository {
    private static final Connection connection;

    static {
        try {
            connection = DBConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Posto mapResultSetToPosto(ResultSet resultSet) {
        Posto posto = new Posto();
        try {
            posto.setId(resultSet.getInt("id"));
            posto.setFila(resultSet.getString("fila"));
            posto.setNumeroPosto(resultSet.getString("numero_posto"));
            posto.setSala_id(resultSet.getInt("sala_id"));
            return posto;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Posto getById(int id) throws SQLException {
        String query = "SELECT * FROM posto WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return mapResultSetToPosto(resultSet);
        } else {
            throw new IllegalArgumentException("Posto con id " + id + " non presente.");
        }
    }

    public static List<Posto> getAllPosto() throws SQLException {
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
        String query = "INSERT INTO posto (fila,numero_posto,sala_id)" +
                "VALUES (?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.fila());
        statement.setString(2, request.numeroPosto());
        statement.setInt(3, request.sala_id());
        statement.executeUpdate();
    }

    public static void updatePosto(int id, PostoRequest request) throws SQLException {
        String query = "UPDATE posto SET fila = ?, numero_posto = ? WHERE id = ?";
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

    public static Double isSeatAvailableAndBookAndPrice(int spettacoloId, int postoId, int utenteId) throws SQLException {
        int currentBookings = 0;
        String countQuery = "SELECT COUNT(*) FROM prenotazione WHERE utente_id = ? AND spettacolo_id = ?";
        try (PreparedStatement countStatement = connection.prepareStatement(countQuery)) {
            countStatement.setInt(1, utenteId);
            countStatement.setInt(2, spettacoloId);
            ResultSet countResultSet = countStatement.executeQuery();
            if (countResultSet.next()) {
                currentBookings = countResultSet.getInt(1);
                if (currentBookings >= 4) {
                    System.out.println("Hai già prenotato il numero massimo di 4 posti per questo spettacolo.");
                    return null;
                }
            }
        }
        String query = "SELECT COUNT(*) FROM prenotazione p " +
                "INNER JOIN posto po ON p.posto_id = po.id " +
                "INNER JOIN spettacolo s ON p.spettacolo_id = s.id " +
                "WHERE s.id = ? AND po.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, spettacoloId);
            statement.setInt(2, postoId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                String bookQuery = "INSERT INTO prenotazione (utente_id, spettacolo_id, posto_id) VALUES (?, ?, ?)";
                try (PreparedStatement bookStatement = connection.prepareStatement(bookQuery)) {
                    double prezzoBiglietto = SpettacoloRepository.getTicketPrice(spettacoloId);
                    bookStatement.setInt(1, utenteId);
                    bookStatement.setInt(2, spettacoloId);
                    bookStatement.setInt(3, postoId);
                    bookStatement.executeUpdate();
                    return (currentBookings + 1) * prezzoBiglietto;
                }
            }
        }
        System.out.println("Il posto è già occupato, riprova la prenotazione.");
        return null;
    }


    public static Double AreSeatAvailableAndBookAndPrice(int spettacoloId, int postoId, int utenteId) throws SQLException {
        int currentBookings = 0;
        String countQuery = "SELECT COUNT(*) FROM prenotazione WHERE utente_id = ? AND spettacolo_id = ?";
        try (PreparedStatement countStatement = connection.prepareStatement(countQuery)) {
            countStatement.setInt(1, utenteId);
            countStatement.setInt(2, spettacoloId);
            ResultSet countResultSet = countStatement.executeQuery();
            if (countResultSet.next()) {
                currentBookings = countResultSet.getInt(1);
                if (currentBookings >= 4) {
                    System.out.println("Hai già prenotato il numero massimo di 4 posti per questo spettacolo.");
                    return null;
                }
            }
        }
        String query = "SELECT COUNT(*) FROM prenotazione p " +
                "INNER JOIN posto po ON p.posto_id = po.id " +
                "INNER JOIN spettacolo s ON p.spettacolo_id = s.id " +
                "WHERE s.id = ? AND po.id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, spettacoloId);
            statement.setInt(2, postoId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                String bookQuery = "INSERT INTO prenotazione (utente_id, spettacolo_id, posto_id) VALUES (?, ?, ?)";
                try (PreparedStatement bookStatement = connection.prepareStatement(bookQuery)) {
                    double prezzoBiglietto = SpettacoloRepository.getTicketPrice(spettacoloId);
                    bookStatement.setInt(1, utenteId);
                    bookStatement.setInt(2, spettacoloId);
                    bookStatement.setInt(3, postoId);
                    bookStatement.executeUpdate();
                    return (currentBookings + 1) * prezzoBiglietto;
                }
            }
        }
        System.out.println("Il posto è già occupato, riprova la prenotazione.");
        return null;
    }
}

