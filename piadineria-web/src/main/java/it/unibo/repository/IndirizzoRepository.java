package it.unibo.repository;

import it.unibo.piadineria.model.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndirizzoRepository extends JpaRepository<Indirizzo, Long> {
    List<Indirizzo> findByUtenteId(Long utenteId);
}

