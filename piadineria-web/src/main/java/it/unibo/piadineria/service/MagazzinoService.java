package it.unibo.piadineria.service;

import it.unibo.piadineria.model.*;
import it.unibo.piadineria.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MagazzinoService {

    private final IngredienteRepository ingredienteRepo;
    private final MovimentoRepository movimentoRepo;
    private final ProdottoIngredienteRepository prodottoIngredienteRepo;

    // ============================
    // CARICO MANUALE
    // ============================
    @Transactional
    public void carico(Long ingredienteId, double quantita) {
        Ingrediente ing = ingredienteRepo.findById(ingredienteId)
                .orElseThrow(() -> new IllegalArgumentException("Ingrediente non trovato"));

        ing.setQuantita(ing.getQuantita() + quantita);
        ingredienteRepo.save(ing);

        movimentoRepo.save(Movimento.builder()
                .ingrediente(ing)
                .tipo("CARICO")
                .quantita(quantita)
                .data(LocalDateTime.now())
                .build());
    }

    // ============================
    // SCARICO MANUALE
    // ============================
    @Transactional
    public void scarico(Long ingredienteId, double quantita) {
        Ingrediente ing = ingredienteRepo.findById(ingredienteId)
                .orElseThrow(() -> new IllegalArgumentException("Ingrediente non trovato"));

        if (ing.getQuantita() < quantita)
            throw new IllegalStateException("Quantità insufficiente per: " + ing.getNome());

        ing.setQuantita(ing.getQuantita() - quantita);
        ingredienteRepo.save(ing);

        movimentoRepo.save(Movimento.builder()
                .ingrediente(ing)
                .tipo("SCARICO")
                .quantita(quantita)
                .data(LocalDateTime.now())
                .build());
    }

    // ============================
    // ⭐ SCARICO AUTOMATICO PER ORDINE
    // ============================
    @Transactional
    public void scaricaPerOrdine(Servizio servizio) {

        for (RigaOrdine riga : servizio.getRighe()) {

            Long prodottoId = riga.getProdotto().getId();
            int quantitaProdotti = riga.getQuantita();

            List<ProdottoIngrediente> ingredientiRichiesti =
                    prodottoIngredienteRepo.findByProdottoId(prodottoId);

            for (ProdottoIngrediente pi : ingredientiRichiesti) {

                Ingrediente ing = pi.getIngrediente();
                double totaleDaScalare = pi.getQuantitaNecessaria() * quantitaProdotti;

                if (ing.getQuantita() < totaleDaScalare) {
                    throw new IllegalStateException(
                            "Ingrediente insufficiente: " + ing.getNome()
                    );
                }

                // SCARICO
                ing.setQuantita(ing.getQuantita() - totaleDaScalare);
                ingredienteRepo.save(ing);

                // REGISTRA MOVIMENTO
                movimentoRepo.save(Movimento.builder()
                        .ingrediente(ing)
                        .tipo("SCARICO")
                        .quantita(totaleDaScalare)
                        .data(LocalDateTime.now())
                        .build());
            }
        }
    }
}
