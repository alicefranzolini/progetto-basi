package it.unibo.piadineria.repository;

import it.unibo.piadineria.model.TesseraFedelta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TesseraFedeltaRepository extends JpaRepository<TesseraFedelta, Long> {
    Optional<TesseraFedelta> findByUtenteId(Long utenteId);
}