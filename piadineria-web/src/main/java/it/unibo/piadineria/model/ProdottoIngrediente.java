package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prodotto_ingredienti")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ProdottoIngrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double quantitaNecessaria;

    @ManyToOne
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;

    @ManyToOne
    @JoinColumn(name = "ingrediente_id")
    private Ingrediente ingrediente;
}
