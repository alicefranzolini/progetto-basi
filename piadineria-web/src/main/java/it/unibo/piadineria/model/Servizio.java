package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Servizio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    private String tipo; // "DELIVERY", "ASPORTO", "PRENOTAZIONE"

    private String statoCorrente; // es. "IN_PREPARAZIONE", "IN_CONSEGNA", ecc.

    private double totale;

    private double scontoApplicato;

    private LocalDateTime dataCreazione;

    @ManyToOne
    @JoinColumn(name = "id_fattorino")
    private Fattorino fattorino;

    @ManyToOne
    @JoinColumn(name = "id_coupon")
    private Coupon coupon;

    @OneToMany(mappedBy = "servizio", cascade = CascadeType.ALL)
    private List<RigaOrdine> righe;
}