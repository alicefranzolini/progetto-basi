package it.unibo.piadineria.repository;

import it.unibo.piadineria.model.Tavolo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TavoloRepository extends JpaRepository<Tavolo, Long> {
    List<Tavolo> findByDisponibileTrue();
}
