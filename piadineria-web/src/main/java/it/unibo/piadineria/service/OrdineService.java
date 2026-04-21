package it.unibo.piadineria.service;

import it.unibo.piadineria.model.*;
import it.unibo.piadineria.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrdineService {

    private final ServizioRepository servizioRepo;
    private final ProdottoRepository prodottoRepo;
    private final UtenteRepository utenteRepo;
    private final FattorinoRepository fattorinoRepo;
    private final StatoServizioRepository statoRepo;
    private final TransizioneStatoRepository transizioneRepo;

    @Transactional
    public Servizio creaOrdineAsporto(Long utenteId, Map<Long, Integer> prodottiQuantita) {
        return creaOrdineGenerico(utenteId, prodottiQuantita, "ASPORTO", null);
    }

    @Transactional
    public Servizio creaOrdineDelivery(Long utenteId,
                                       Map<Long, Integer> prodottiQuantita,
                                       Long indirizzoId) {
        return creaOrdineGenerico(utenteId, prodottiQuantita, "DELIVERY", indirizzoId);
    }

    private Servizio creaOrdineGenerico(Long utenteId,
                                        Map<Long, Integer> prodottiQuantita,
                                        String tipo,
                                        Long indirizzoId) {

        Utente u = utenteRepo.findById(utenteId)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));

        Servizio s = Servizio.builder()
                .utente(u)
                .tipo(tipo)
                .dataCreazione(LocalDateTime.now())
                .build();

        List<RigaOrdine> righe = new ArrayList<>();
        double totale = 0;

        for (var entry : prodottiQuantita.entrySet()) {
            Prodotto p = prodottoRepo.findById(entry.getKey())
                    .orElseThrow(() -> new IllegalArgumentException("Prodotto non trovato"));

            int qta = entry.getValue();
            if (qta <= 0) continue;

            RigaOrdine r = RigaOrdine.builder()
                    .servizio(s)
                    .prodotto(p)
                    .quantita(qta)
                    .prezzoUnitario(p.getPrezzo())
                    .build();

            righe.add(r);
            totale += p.getPrezzo() * qta;
        }

        s.setRighe(righe);
        s.setTotale(totale);

        // stato iniziale
        StatoServizio statoIniziale = statoRepo.findByCodice("IN_PREPARAZIONE")
                .orElseThrow(() -> new IllegalStateException("Stato IN_PREPARAZIONE mancante"));
        s.setStatoCorrente(statoIniziale.getCodice());

        // ASSEGNAZIONE AUTOMATICA FATTORINO (solo DELIVERY)
        if (tipo.equals("DELIVERY")) {
            List<Fattorino> disponibili = fattorinoRepo.findByAttivoTrue();
            if (!disponibili.isEmpty()) {
                s.setFattorino(disponibili.get(0)); // per ora il primo disponibile
            }
        }

        Servizio salvato = servizioRepo.save(s);

        // registra transizione stato
        TransizioneStato t = TransizioneStato.builder()
                .servizio(salvato)
                .stato(statoIniziale)
                .dataOra(LocalDateTime.now())
                .build();
        transizioneRepo.save(t);

        return salvato;
    }

    public List<Servizio> ordiniPerUtente(Long utenteId) {
        return servizioRepo.findByUtenteId(utenteId);
    }

    @Transactional
    public void cambiaStato(Long servizioId, String nuovoStato) {

        Servizio s = servizioRepo.findById(servizioId)
                .orElseThrow(() -> new IllegalArgumentException("Servizio non trovato"));

        StatoServizio stato = statoRepo.findByCodice(nuovoStato)
                .orElseThrow(() -> new IllegalArgumentException("Stato non valido"));

        s.setStatoCorrente(stato.getCodice());
        servizioRepo.save(s);

        TransizioneStato t = TransizioneStato.builder()
                .servizio(s)
                .stato(stato)
                .dataOra(LocalDateTime.now())
                .build();

        transizioneRepo.save(t);
    }
}
