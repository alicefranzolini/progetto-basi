package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codiceCoupon;

    private double percentualeSconto;

    private boolean attivazione;

    @ManyToOne
    @JoinColumn(name = "id_tessera")
    private TesseraFedelta tessera;

    @ManyToOne
    @JoinColumn(name = "id_servizio")
    private Servizio servizio;
}