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

    // ASPORTO, DELIVERY, PRENOTAZIONE_TAVOLO
    private String tipo;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    private LocalDateTime dataCreazione;
    private double totale;

    // stato attuale dell’ordine
    private String statoCorrente;

    // fattorino assegnato (solo per DELIVERY)
    @ManyToOne
    @JoinColumn(name = "fattorino_id")
    private Fattorino fattorino;

    @OneToMany(mappedBy = "servizio", cascade = CascadeType.ALL)
    private List<RigaOrdine> righe;
}
