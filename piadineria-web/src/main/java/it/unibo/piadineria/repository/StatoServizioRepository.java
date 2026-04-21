package it.unibo.piadineria.repository;

import it.unibo.piadineria.model.StatoServizio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatoServizioRepository extends JpaRepository<StatoServizio, Long> {
    Optional<StatoServizio> findByCodice(String codice);
}

