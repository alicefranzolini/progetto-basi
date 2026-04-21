package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "fattorini")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Fattorino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;
    private String telefono;
    private String zona;   // es. "Centro", "Periferia"
    private boolean attivo;
}
