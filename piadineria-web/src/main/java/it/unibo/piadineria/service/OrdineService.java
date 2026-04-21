package it.unibo.piadineria.service;

import it.unibo.piadineria.model.*;
import it.unibo.piadineria.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrdineService {

    private final ServizioRepository servizioRepo;
    private final ProdottoRepository prodottoRepo;
    private final UtenteRepository utenteRepo;

    @Transactional
    public Servizio creaOrdineAsporto(Long utenteId, Map<Long, Integer> prodottiQuantita) {

        Utente u = utenteRepo.findById(utenteId)
                .orElseThrow(() -> new IllegalArgumentException("Utente non trovato"));

        Servizio s = Servizio.builder()
                .utente(u)
                .tipo("ASPORTO")
                .dataCreazione(LocalDateTime.now())
                .build();

        List<RigaOrdine> righe = new ArrayList<>();
        double totale = 0;

        for (var entry : prodottiQuantita.entrySet()) {
            Prodotto p = prodottoRepo.findById(entry.getKey())
                    .orElseThrow(() -> new IllegalArgumentException("Prodotto non trovato"));

            int qta = entry.getValue();
            if (qta <= 0) continue;

            RigaOrdine r = RigaOrdine.builder()
                    .servizio(s)
                    .prodotto(p)
                    .quantita(qta)
                    .prezzoUnitario(p.getPrezzo())
                    .build();

            righe.add(r);
            totale += p.getPrezzo() * qta;
        }

        s.setRighe(righe);
        s.setTotale(totale);

        return servizioRepo.save(s);
    }

    public List<Servizio> ordiniPerUtente(Long utenteId) {
        return servizioRepo.findByUtenteId(utenteId);
    }
}

