package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "servizi")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Servizio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // ASPORTO per ora

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    private LocalDateTime dataCreazione;
    private double totale;

    @OneToMany(mappedBy = "servizio", cascade = CascadeType.ALL)
    private List<RigaOrdine> righe;
}
