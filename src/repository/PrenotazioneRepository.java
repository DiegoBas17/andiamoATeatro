package repository;

import DBConfig.DBConnection;
import DTO.PrenotazioneRequest;
import entities.Prenotazione;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneRepository {

    private static final Connection connection;

    static {
        try {
            connection = DBConnection.getConnection();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Prenotazione mapResultSetToPrenotazione(ResultSet resultSet) {
        Prenotazione prenotazione = new Prenotazione();
        try {
            int id = resultSet.getInt("id");
            LocalDateTime orarioAcquisto = resultSet.getTimestamp("orario_acquisto").toLocalDateTime();
            int spettacolo_id = resultSet.getInt("spettacolo_id");
            int utente_id = resultSet.getInt("utente_id");
            int posto_id = resultSet.getInt("posto_id");

            // Crea e restituisci un oggetto Prenotazione
            return new Prenotazione(id, orarioAcquisto, spettacolo_id, utente_id, posto_id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Prenotazione getById(int id) throws SQLException {
        String query = "SELECT * FROM prenotazione WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return mapResultSetToPrenotazione(resultSet);
        } else {
            throw new IllegalArgumentException("Posto con id " + id + " non presente.");
        }

    }

    public static List<Prenotazione> getAllPrenotazioni() throws SQLException {
        String query = "SELECT * FROM prenotazione";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Prenotazione> prenotazioni = new ArrayList<>();
        while (resultSet.next()) {
            prenotazioni.add(mapResultSetToPrenotazione(resultSet));
        }
        return prenotazioni;
    }

    public static void insertPrenotazione(PrenotazioneRequest request) throws SQLException {
        String query = "INSERT INTO prenotazione (spettacolo_id,posto_id,utente_id,orario_acquisto)" +
                "VALUES (?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, request.spettacolo_id());
        statement.setInt(2, request.posto_id());
        statement.setInt(3, request.utente_id());
        statement.setTimestamp(4, Timestamp.valueOf(request.orarioAcquisto()));

    }


    public static void deleteById(int id) throws SQLException {
        String query = "DELETE FROM prenotazione WHERE id =?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeQuery();
    }
}
