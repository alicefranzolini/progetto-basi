package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movimento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Ingrediente ingrediente;

    private String tipo;       // "CARICO" o "SCARICO"
    private double quantita;
    private LocalDateTime data;
}