package it.unibo.piadineria.repository;

import it.unibo.piadineria.model.Servizio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServizioRepository extends JpaRepository<Servizio, Long> {
    List<Servizio> findByUtenteId(Long utenteId);
    List<Servizio> findByFattorinoId(Long fattorinoId);
}
