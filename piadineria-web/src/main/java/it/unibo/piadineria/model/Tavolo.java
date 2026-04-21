package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tavoli")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Tavolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numero;
    private int posti;
    private boolean disponibile;
}
