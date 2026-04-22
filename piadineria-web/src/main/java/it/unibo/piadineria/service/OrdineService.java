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
    private final RigaOrdineRepository rigaOrdineRepo;
    private final UtenteRepository utenteRepo;
    private final FattorinoRepository fattorinoRepo;
    private final StatoServizioRepository statoRepo;
    private final TransizioneStatoRepository transizioneRepo;
    private final MagazzinoService magazzinoService;

    @Transactional
    public Servizio creaOrdine(Long utenteId, Map<Long, Integer> prodottiQuantita, String tipoOrdine, Long indirizzoId) {
        Utente utente = utenteRepo.findById(utenteId)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));

        Servizio servizio = new Servizio();
        servizio.setUtente(utente);
        servizio.setTipo(tipoOrdine);
        servizio.setDataCreazione(LocalDateTime.now());

        List<RigaOrdine> righe = new ArrayList<>();
        double totale = 0;

        for (var entry : prodottiQuantita.entrySet()) {
            Prodotto prodotto = prodottoRepo.findById(entry.getKey())
                    .orElseThrow(() -> new IllegalArgumentException("Prodotto non trovato"));

            RigaOrdine riga = new RigaOrdine();
            riga.setServizio(servizio);
            riga.setProdotto(prodotto);
            riga.setQuantita(entry.getValue());
            riga.setPrezzoUnitario(prodotto.getPrezzo());
            righe.add(riga);
            totale += prodotto.getPrezzo() * entry.getValue();
        }

        servizio.setRighe(righe);
        servizio.setTotale(totale);

        StatoServizio statoIniziale = statoRepo.findByCodice("IN_PREPARAZIONE")
                .orElseThrow(() -> new IllegalStateException("Stato non trovato"));
        servizio.setStatoCorrente(statoIniziale.getCodice());

        Servizio salvato = servizioRepo.save(servizio);
        rigaOrdineRepo.saveAll(righe);
        magazzinoService.scaricaPerOrdine(salvato);

        if ("DELIVERY".equals(tipoOrdine)) {
            Fattorino f = fattorinoRepo.findFirstByAttivoTrue();
            if (f != null) salvato.setFattorino(f);
        }

        // salva storico stato iniziale
        transizioneRepo.save(TransizioneStato.builder()
                .servizio(salvato)
                .stato(statoIniziale.getCodice())
                .dataTransizione(LocalDateTime.now())
                .build());

        return servizioRepo.save(salvato);
    }

    @Transactional
    public void cambiaStato(Long servizioId, String nuovoStato) {
        Servizio s = servizioRepo.findById(servizioId)
                .orElseThrow(() -> new IllegalArgumentException("Ordine non trovato"));

        s.setStatoCorrente(nuovoStato);
        servizioRepo.save(s);

        transizioneRepo.save(TransizioneStato.builder()
                .servizio(s)
                .stato(nuovoStato)
                .dataTransizione(LocalDateTime.now())
                .build());
    }

    public List<Servizio> ordiniPerUtente(Long utenteId) {
        return servizioRepo.findByUtenteId(utenteId);
    }

    @Transactional
    public Servizio creaOrdineAsporto(Long utenteId, Map<Long, Integer> prodottiQuantita) {
        return creaOrdine(utenteId, prodottiQuantita, "ASPORTO", null);
    }

    @Transactional
    public Servizio creaOrdineDelivery(Long utenteId, Map<Long, Integer> prodottiQuantita, Long indirizzoId) {
        return creaOrdine(utenteId, prodottiQuantita, "DELIVERY", indirizzoId);
    }

    @Transactional
    public void assegnaFattorino(Long servizioId, Long fattorinoId) {
        Servizio s = servizioRepo.findById(servizioId)
                .orElseThrow(() -> new IllegalArgumentException("Ordine non trovato"));
        Fattorino f = fattorinoRepo.findById(fattorinoId)
                .orElseThrow(() -> new IllegalArgumentException("Fattorino non trovato"));
        s.setFattorino(f);
        servizioRepo.save(s);
    }
}