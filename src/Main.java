import DTO.SalaRequest;
import DTO.SedeRequest;
import DTO.SpettacoloRequest;
import DTO.UtenteRequest;
import entities.Sala;
import entities.Sede;
import entities.Spettacolo;
import entities.Utente;
import service.SalaService;
import service.SedeService;
import service.SpettacoloService;
import service.UtenteService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        inizialiazzazioneDB();
        eliminaUtente();
    }

    private static void inizialiazzazioneDB() {
        List<Utente> utenti = new ArrayList<>();
        List<Sede> sedi = new ArrayList<>();
        List<Sala> sale = new ArrayList<>();
        List<Spettacolo> spettacoli = new ArrayList<>();
        try {
            utenti = UtenteService.getAllUtenti();
            sedi = SedeService.findAllSedi();
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
        } else System.out.println("C'è gia nanda nel DB");
        if (sedi.isEmpty()) {
            System.out.println("Inizializzo una sede");
            SedeRequest nuovaSede = new SedeRequest("angolo dei cretini 1",
                    "cosi è la vita", "roma", Boolean.FALSE);
            try {
                SedeService.insertSede(nuovaSede);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else System.out.println("Sede gia presente nel DB!");
        if (sale.isEmpty()) {
            System.out.println("Inizializzo una sala");
            try {
                Sede sede = SedeService.findAllSedi().getFirst();
                SalaRequest nuovaSala = new SalaRequest(20, "Parco", sede.getId());
                SalaService.insertSala(nuovaSala);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else System.out.println("Sala gia presente nel DB!");
        if (spettacoli.isEmpty()) {
            System.out.println("Inizializzo uno Spettacolo");
            try {
                Sala sala = SalaService.getAllSale().getFirst();
                SpettacoloRequest spettacolo = new SpettacoloRequest(LocalDateTime.now(),
                        20.50, 120, "Comico", sala.getId());
                SpettacoloService.insertSpettacolo(spettacolo);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}