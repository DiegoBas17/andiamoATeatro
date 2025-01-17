package repository;


import DBConfig.DBConnection;
import DTO.SpettacoloRequest;
import entities.Spettacolo;

import java.sql.*;
import java.time.LocalDateTime;
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
            spettacolo.setOrario(resultSet.getTimestamp("orario").toLocalDateTime());
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
        statement.setString(4, request.genere());
        statement.setInt(5, request.sala_id());
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

    public static Double getTicketPrice(int id) throws SQLException {
        String query = "SELECT prezzo FROM spettacolo WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("prezzo");
            } else {
                throw new SQLException("Prezzo non trovato per lo spettacolo con ID " + id);
            }
        }
    }

    public static List<Spettacolo> suggerisciSpettacoliProssimoMese(int utenteId) throws SQLException {
        String ultimiSpettacoliQuery = "SELECT s.id, s.genere, p.orario_acquisto " +
                "FROM prenotazione p " +
                "INNER JOIN spettacolo s ON p.spettacolo_id = s.id " +
                "WHERE p.utente_id = ? " +
                "ORDER BY p.orario_acquisto DESC " +
                "LIMIT 3";
        List<String> generi = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(ultimiSpettacoliQuery)) {
            stmt.setInt(1, utenteId);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("Nessun spettacolo prenotato da questo utente.");
                return new ArrayList<>();
            }
            do {
                generi.add(rs.getString("genere"));
            } while (rs.next());
        }
        if (generi.isEmpty()) {
            System.out.println("Nessun genere trovato per l'utente.");
            return new ArrayList<>();
        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime firstDayOfNextMonth = now.plusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime lastDayOfNextMonth = firstDayOfNextMonth.plusMonths(1).minusDays(1).withHour(23).withMinute(59).withSecond(59);
        String spettacoliProssimoMeseQuery = "SELECT * FROM spettacolo WHERE genere IN (?) AND orario BETWEEN ? AND ? ORDER BY orario ASC";
        List<Spettacolo> spettacoliSuggeriti = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(spettacoliProssimoMeseQuery)) {
            stmt.setString(1, String.join(",", generi));
            stmt.setTimestamp(2, Timestamp.valueOf(firstDayOfNextMonth));
            stmt.setTimestamp(3, Timestamp.valueOf(lastDayOfNextMonth));
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("Nessun spettacolo trovato per i generi selezionati nel mese prossimo.");
                return new ArrayList<>();
            }
            do {
                spettacoliSuggeriti.add(mapResultSetToSpettacolo(rs));
            } while (rs.next());
        } catch (SQLException e) {
            System.err.println("Errore durante la ricerca degli spettacoli: " + e.getMessage());
            throw new SQLException("Errore durante la ricerca degli spettacoli.", e);
        }
        return spettacoliSuggeriti;
    }
}
