package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stati_servizio")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class StatoServizio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codice;   // IN_PREPARAZIONE, IN_CONSEGNA, CONSEGNATO...
    private String descrizione;
}
