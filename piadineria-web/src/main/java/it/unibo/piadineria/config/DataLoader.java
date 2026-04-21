package it.unibo.piadineria.config;

import it.unibo.piadineria.model.*;
import it.unibo.piadineria.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final CategoriaProdottoRepository categoriaRepo;
    private final ProdottoRepository prodottoRepo;
    private final StatoServizioRepository statoRepo;
    private final FattorinoRepository fattorinoRepo;

    @Override
    public void run(String... args) {
        if (prodottoRepo.count() == 0) {
            CategoriaProdotto piadine = categoriaRepo.save(
                    CategoriaProdotto.builder()
                            .nome("Piadine")
                            .descrizione("Piadine classiche")
                            .build()
            );

            prodottoRepo.save(Prodotto.builder()
                    .nome("Piadina crudo e squacquerone")
                    .descrizione("Crudo di Parma e squacquerone")
                    .prezzo(6.50)
                    .categoria(piadine)
                    .build());

            prodottoRepo.save(Prodotto.builder()
                    .nome("Piadina vegetariana")
                    .descrizione("Verdure grigliate e formaggio")
                    .prezzo(6.00)
                    .categoria(piadine)
                    .build());
        }

        if (statoRepo.count() == 0) {
            statoRepo.save(StatoServizio.builder()
                    .codice("IN_PREPARAZIONE")
                    .descrizione("Ordine in preparazione")
                    .build());
            statoRepo.save(StatoServizio.builder()
                    .codice("IN_CONSEGNA")
                    .descrizione("Ordine in consegna")
                    .build());
            statoRepo.save(StatoServizio.builder()
                    .codice("CONSEGNATO")
                    .descrizione("Ordine consegnato")
                    .build());
        }

        if (fattorinoRepo.count() == 0) {
            fattorinoRepo.save(Fattorino.builder()
                    .nome("Marco")
                    .cognome("Rossi")
                    .telefono("3331234567")
                    .zona("Centro")
                    .attivo(true)
                    .build());
        }
    }
}
