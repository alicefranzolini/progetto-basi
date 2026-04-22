package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimenti_magazzino")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MovimentoMagazzino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // CARICO o SCARICO
    private double quantita;
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "ingrediente_id")
    private Ingrediente ingrediente;
}
