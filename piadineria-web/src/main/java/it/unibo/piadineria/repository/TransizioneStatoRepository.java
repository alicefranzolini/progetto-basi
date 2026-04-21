package it.unibo.piadineria.repository;

import it.unibo.piadineria.model.TransizioneStato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransizioneStatoRepository extends JpaRepository<TransizioneStato, Long> {
}

