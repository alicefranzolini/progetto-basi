package it.unibo.piadineria.repository;

import it.unibo.piadineria.model.Fattorino;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FattorinoRepository extends JpaRepository<Fattorino, Long> {
    List<Fattorino> findByAttivoTrue();
}
