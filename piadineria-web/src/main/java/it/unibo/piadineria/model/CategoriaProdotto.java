package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorie_prodotto")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CategoriaProdotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descrizione;
}
