package it.unibo.piadineria.repository;

import it.unibo.piadineria.model.Fattorino;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FattorinoRepository extends JpaRepository<Fattorino, Long> {
    Fattorino findFirstByAttivoTrue();
    Optional<Fattorino> findByUtenteId(Long utenteId);
}