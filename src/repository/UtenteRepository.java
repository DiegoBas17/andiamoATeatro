package repository;

import DBConfig.DBConnection;
import DTO.UtenteRequest;
import entities.Utente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UtenteRepository {

    private static final Connection connection;

    static {
        try {
            connection = DBConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Utente mapResultSetToUtente(ResultSet resultSet) {
        Utente utente = new Utente();
        try {
            utente.setId(resultSet.getInt("id"));
            utente.setNome(resultSet.getString("nome"));
            utente.setCognome(resultSet.getString("cognome"));
            utente.setEmail(resultSet.getString("email"));
            utente.setIndirizzo(resultSet.getString("indirizzo"));
            utente.setCellulare(resultSet.getString("cellulare"));
            return utente;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Utente getById(int id) throws SQLException {
        String query = "SELECT * FROM utente WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return mapResultSetToUtente(resultSet);
        } else
            throw new IllegalArgumentException("Utente con id " + id + " non presente");
    }

    public static List<Utente> getAllUtenti() throws SQLException {
        String query = "SELECT * FROM utente";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        List<Utente> utenti = new ArrayList<>();
        while (resultSet.next()) {
            utenti.add(mapResultSetToUtente(resultSet));
        }
        return utenti;
    }

    public static void insertUtenti(UtenteRequest request) throws SQLException {
        String query = "INSERT INTO utente (nome,cognome,indirizzo,email,cellulare)" +
                "VALUES (?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.nome());
        statement.setString(2, request.cognome());
        statement.setString(3, request.indirizzo());
        statement.setString(4, request.email());
        statement.setString(5, request.cellulare());
        statement.executeUpdate();
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
        statement.executeUpdate();
    }


    public static boolean aquistareTicket(int utenteId, int spettacoloId) throws SQLException {
        String query = "SELECT COUNT(*) FROM prenotazione WHERE utente_id = ? AND spettacolo_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, utenteId);
        statement.setInt(2, spettacoloId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            int ticketsCount = resultSet.getInt(1);
            return ticketsCount < 4;
        }
        return false;
    }

    public static boolean Bigliettoaquistato(int utenteId, int spettacoloId) throws SQLException {
        String query = "SELECT orario FROM spettacolo WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, spettacoloId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Date spettacoloDate = resultSet.getDate("orario");
            LocalDate spettacoloLocalDateTime = spettacoloDate.toLocalDate();
            String checkQuery = "SELECT COUNT(*) FROM prenotazione p INNER JOIN spettacolo s ON p.spettacolo_id = s.id " +
                    "WHERE p.utente_id = ? AND s.orario >= ? AND s.orario < ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, utenteId);
            checkStatement.setDate(2, Date.valueOf(spettacoloLocalDateTime));
            checkStatement.setDate(3, Date.valueOf(spettacoloLocalDateTime.plusDays(1)));
            ResultSet checkResult = checkStatement.executeQuery();
            if (checkResult.next()) {
                int ticketsToday = checkResult.getInt(1);
                return ticketsToday == 0;
            }
        }
        return true;
    }

}
