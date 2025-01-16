import DTO.UtenteRequest;
import entities.Utente;
import service.UtenteService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        inizialiazzazioneDB();
    }

    private static void inizialiazzazioneDB() {
        List<Utente> utenti = new ArrayList<>();
        try {
            utenti = UtenteService.getAllUtenti();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (utenti.isEmpty()) {
            System.out.println("aggiungo Nanda");
            UtenteRequest nuovoUtente = new UtenteRequest("nanda", "Pellegrini", "nandaClown@gmail.com",
                    "via le mandi la culo, 69", "1234567890");
            try {
                UtenteService.insertUtente(nuovoUtente);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("C'è gia nanda nel DB");
        }
    }
}