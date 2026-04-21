package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "indirizzi")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Indirizzo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeEtichetta; // "Casa", "Ufficio"...
    private String via;
    private String citta;
    private String cap;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;
}
