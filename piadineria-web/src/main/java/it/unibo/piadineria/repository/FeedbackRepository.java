package it.unibo.piadineria.repository;

import it.unibo.piadineria.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByUtenteId(Long utenteId);
    List<Feedback> findByServizioId(Long servizioId);
    List<Feedback> findByPrenotazioneId(Long prenotazioneId);
}
