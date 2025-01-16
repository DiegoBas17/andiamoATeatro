import DTO.UtenteRequest;
import service.UtenteService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        inizialiazzazioneDB();
        eliminaUtente();
    }

    private static void inizialiazzazioneDB() {
        UtenteRequest nuovoUtente = new UtenteRequest("Diego", "Basili", "diegoClown@gmail.com",
                "via nel culo, 23", "1234569999");
        try {
            UtenteService.insertUtente(nuovoUtente);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void eliminaUtente() throws SQLException {
        UtenteService.deleteUtenteById(5);
    }
}