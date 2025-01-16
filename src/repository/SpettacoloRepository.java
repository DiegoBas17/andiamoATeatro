package repository;


import DBConfig.DBConnection;
import DTO.SpettacoloRequest;
import entities.Spettacolo;

import java.sql.*;
import java.time.LocalDate;
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
        String query = "INSERT INTO spettacolo (orario,prezzo,durata_in_minuti,genere,sala_id)" +
                "VALUES (?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDate(1, convertToSqlDate(request.orario()));
        statement.setDouble(2, request.prezzo());
        statement.setInt(3, request.durataInMinuti());
        statement.setString(3, request.genere());
        statement.setInt(3, request.sala_id());
        statement.executeUpdate();
    }

    public static void updateSpettacolo(int id, SpettacoloRequest request) throws SQLException {
        String query = "UPDATE spettacolo SET orario = ?, prezzo = ?, durata_in_minuti =?, genere =?, sala_id =? WHERE id = ?";

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

    public static boolean aquistareTicket(int utenteId, int spettacoloId) throws SQLException {
        String query = "SELECT COUNT(*) FROM prenotazione WHERE utente_id = ? AND spettacolo_id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, utenteId);
        statement.setInt(2, spettacoloId);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int ticketsCount = resultSet.getInt(1);
            return ticketsCount < 4;  // Permetti l'acquisto solo se il numero di biglietti è inferiore a 4
        }

        return false;  // Se l'utente ha già acquistato 4 biglietti o più
    } // n

    // Verifica se un utente ha già acquistato un biglietto per lo stesso spettacolo nello stesso giorno
    public static boolean Bigliettoaquistato(int utenteId, int spettacoloId) throws SQLException {
        String query = "SELECT orario FROM spettacolo WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, spettacoloId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Date spettacoloDate = resultSet.getDate("orario");
            LocalDate spettacoloLocalDateTime = spettacoloDate.toLocalDate();

            // Verifica se l'utente ha prenotato un biglietto per lo stesso giorno
            String checkQuery = "SELECT COUNT(*) FROM prenotazione p INNER JOIN spettacolo s ON p.spettacolo_id = s.id " +
                    "WHERE p.utente_id = ? AND s.orario >= ? AND s.orario < ?";

            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, utenteId);
            checkStatement.setDate(2, Date.valueOf(spettacoloLocalDateTime));
            checkStatement.setDate(3, Date.valueOf(spettacoloLocalDateTime.plusDays(1)));

            ResultSet checkResult = checkStatement.executeQuery();
            if (checkResult.next()) {
                int ticketsToday = checkResult.getInt(1);
                return ticketsToday == 0;  // Permetti l'acquisto solo se non c'è stato alcun biglietto per oggi
            }
        }

        return true;  // Se non ci sono conflitti nella stessa giornata
    }

    // Metodo per prenotare un biglietto
    public static void prenotazioneTicket(int utenteId, int spettacoloId) throws SQLException {
        if (aquistareTicket(utenteId, spettacoloId) && Bigliettoaquistato(utenteId, spettacoloId)) {
            String insertQuery = "INSERT INTO prenotazione (utente_id, spettacolo_id) VALUES (?, ?)";

            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setInt(1, utenteId);
            statement.setInt(2, spettacoloId);
            statement.executeUpdate();

        }
    }
}
