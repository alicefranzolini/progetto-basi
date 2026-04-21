package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transizioni_stato")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class TransizioneStato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "servizio_id")
    private Servizio servizio;

    @ManyToOne
    @JoinColumn(name = "stato_id")
    private StatoServizio stato;

    private LocalDateTime dataOra;
}
