package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ingredienti")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private double quantita; // in grammi o unità
    private double sogliaMinima; // per avvisi
}
