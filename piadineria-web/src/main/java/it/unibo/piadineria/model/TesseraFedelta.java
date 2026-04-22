package it.unibo.piadineria.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TesseraFedelta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int ordiniEffettuati;

    private LocalDate dataUltimoOrdine;

    @OneToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    @OneToMany(mappedBy = "tessera", cascade = CascadeType.ALL)
    private java.util.List<Coupon> coupon;
}