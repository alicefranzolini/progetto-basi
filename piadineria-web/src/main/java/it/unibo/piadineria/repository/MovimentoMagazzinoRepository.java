package it.unibo.piadineria.repository;

import it.unibo.piadineria.model.MovimentoMagazzino;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentoMagazzinoRepository extends JpaRepository<MovimentoMagazzino, Long> {
}
