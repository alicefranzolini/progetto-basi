package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "feedback")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int voto; // 1-5
    private String commento;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    // feedback su ordine
    @ManyToOne
    @JoinColumn(name = "servizio_id")
    private Servizio servizio;

    // feedback su prenotazione
    @ManyToOne
    @JoinColumn(name = "prenotazione_id")
    private Prenotazione prenotazione;
}
