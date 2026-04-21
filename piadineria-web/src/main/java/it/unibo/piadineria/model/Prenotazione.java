package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "prenotazioni")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    private LocalTime ora;
    private int persone;
    private String note;

    private String stato; // IN_ATTESA, CONFERMATA, RIFIUTATA

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "tavolo_id")
    private Tavolo tavolo;
}
