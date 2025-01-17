import DTO.*;
import entities.*;
import repository.PostoRepository;
import repository.PrenotazioneRepository;
import repository.SpettacoloRepository;
import service.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        /*esercizio 1*/
        System.out.println("*************************** ESERCIZIO 1 **********************************");
        inizialiazzazioneDB();
        /*esercizio 2*/
        System.out.println("*************************** ESERCIZIO 2 **********************************");
        eseguiPrenotazione();
        /*esercizio 3*/
        System.out.println("*************************** ESERCIZIO 3 **********************************");
        System.out.println(PrenotazioneRepository.searchPrenotazioni(1, 1, LocalDateTime.of(2025, 1, 17, 10, 34, 4, 0)));
        /*esercizio 4*/
        System.out.println("*************************** ESERCIZIO 4 **********************************");
        System.out.println(SpettacoloRepository.suggerisciSpettacoliProssimoMese(1));
    }

    private static void inizialiazzazioneDB() {
        List<Utente> utenti = new ArrayList<>();
        List<Sede> sedi = new ArrayList<>();
        List<Sala> sale = new ArrayList<>();
        List<Spettacolo> spettacoli = new ArrayList<>();
        List<Posto> listaPosti = new ArrayList<>();
        try {
            utenti = UtenteService.getAllUtenti();
            sedi = SedeService.findAllSedi();
            sale = SalaService.getAllSale();
            spettacoli = SpettacoloService.getAllSpettacolo();
            listaPosti = PostoService.getAllPosto();
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
                SpettacoloRequest spettacolo = new SpettacoloRequest(LocalDateTime.now(), 20.50, 120, "Comico", sala.getId());
                SpettacoloRequest spettacolo2 = new SpettacoloRequest(LocalDateTime.now().plusDays(10), 22.30, 90, "Drammatico", sala.getId());
                SpettacoloRequest spettacolo3 = new SpettacoloRequest(LocalDateTime.now().plusDays(15), 19.00, 110, "Musicale", sala.getId());
                SpettacoloRequest spettacolo4 = new SpettacoloRequest(LocalDateTime.now().plusDays(20), 18.00, 130, "Azione", sala.getId());
                SpettacoloService.insertSpettacolo(spettacolo);
                SpettacoloService.insertSpettacolo(spettacolo2);
                SpettacoloService.insertSpettacolo(spettacolo3);
                SpettacoloService.insertSpettacolo(spettacolo4);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (listaPosti.isEmpty()) {
            System.out.println("Inizializzo i posti per la sala");
            try {
                Sala sala = SalaService.getAllSale().getFirst();
                int numeroFile = 5;
                int postiPerFila = 10;
                for (int i = 1; i <= numeroFile; i++) {
                    String fila = String.valueOf((char) ('A' + (i - 1)));
                    for (int j = 1; j <= postiPerFila; j++) {
                        String numeroPosto = String.valueOf(j);
                        PostoRequest nuovoPosto = new PostoRequest(fila, numeroPosto, sala.getId());
                        PostoService.insertPosto(nuovoPosto);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Posti già presenti nel DB!");
        }
    }

    public static void eseguiPrenotazione() {
        int utenteId = 1;
        int spettacoloId = 1;
        int postoId = 1;
        try {
            Double prezzoTotale = PostoRepository.isSeatAvailableAndBookAndPrice(spettacoloId, postoId, utenteId);
            if (prezzoTotale != null) {
                System.out.println("Prenotazione effettuata con successo!");
                System.out.println("Prezzo totale da pagare: " + prezzoTotale + "€");
            } else {
                System.out.println("Prenotazione non riuscita. Verifica i dati inseriti.");
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la prenotazione: " + e.getMessage());
        }
    }

}