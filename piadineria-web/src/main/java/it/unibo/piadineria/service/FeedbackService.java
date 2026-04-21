package it.unibo.piadineria.service;

import it.unibo.piadineria.model.*;
import it.unibo.piadineria.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepo;
    private final UtenteRepository utenteRepo;
    private final ServizioRepository servizioRepo;
    private final PrenotazioneRepository prenotazioneRepo;

    public Feedback creaFeedbackOrdine(Long utenteId, Long servizioId, int voto, String commento) {

        Utente u = utenteRepo.findById(utenteId)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));

        Servizio s = servizioRepo.findById(servizioId)
                .orElseThrow(() -> new IllegalArgumentException("Ordine non trovato"));

        Feedback f = Feedback.builder()
                .utente(u)
                .servizio(s)
                .voto(voto)
                .commento(commento)
                .build();

        return feedbackRepo.save(f);
    }

    public Feedback creaFeedbackPrenotazione(Long utenteId, Long prenotazioneId, int voto, String commento) {

        Utente u = utenteRepo.findById(utenteId)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));

        Prenotazione p = prenotazioneRepo.findById(prenotazioneId)
                .orElseThrow(() -> new IllegalArgumentException("Prenotazione non trovata"));

        Feedback f = Feedback.builder()
                .utente(u)
                .prenotazione(p)
                .voto(voto)
                .commento(commento)
                .build();

        return feedbackRepo.save(f);
    }

    public List<Feedback> feedbackUtente(Long utenteId) {
        return feedbackRepo.findByUtenteId(utenteId);
    }

    public List<Feedback> tuttiFeedback() {
        return feedbackRepo.findAll();
    }
}
