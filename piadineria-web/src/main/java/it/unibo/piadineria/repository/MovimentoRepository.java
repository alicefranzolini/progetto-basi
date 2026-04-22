package it.unibo.piadineria.repository;

import it.unibo.piadineria.model.Movimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentoRepository extends JpaRepository<Movimento, Long> {}