package it.unibo.piadineria.service;

import it.unibo.piadineria.model.*;
import it.unibo.piadineria.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepo;
    private final TavoloRepository tavoloRepo;
    private final UtenteRepository utenteRepo;

    @Transactional
    public Prenotazione creaPrenotazione(Long utenteId,
                                         LocalDate data,
                                         LocalTime ora,
                                         int persone,
                                         String note) {

        Utente u = utenteRepo.findById(utenteId)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));

        // trova tavolo disponibile
        List<Tavolo> disponibili = tavoloRepo.findByDisponibileTrue();
        if (disponibili.isEmpty()) {
            throw new IllegalStateException("Nessun tavolo disponibile");
        }

        Tavolo t = disponibili.get(0); // per ora il primo libero

        Prenotazione p = Prenotazione.builder()
                .utente(u)
                .data(data)
                .ora(ora)
                .persone(persone)
                .note(note)
                .tavolo(t)
                .stato("IN_ATTESA")
                .build();

        return prenotazioneRepo.save(p);
    }

    public List<Prenotazione> prenotazioniUtente(Long utenteId) {
        return prenotazioneRepo.findByUtenteId(utenteId);
    }

    @Transactional
    public void cambiaStato(Long prenotazioneId, String nuovoStato) {
        Prenotazione p = prenotazioneRepo.findById(prenotazioneId)
                .orElseThrow(() -> new IllegalArgumentException("Prenotazione non trovata"));

        p.setStato(nuovoStato);
        prenotazioneRepo.save(p);
    }
}
