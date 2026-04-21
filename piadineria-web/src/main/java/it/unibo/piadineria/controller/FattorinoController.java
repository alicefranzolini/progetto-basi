package it.unibo.piadineria.controller;

import it.unibo.piadineria.model.Servizio;
import it.unibo.piadineria.model.Fattorino;
import it.unibo.piadineria.repository.ServizioRepository;
import it.unibo.piadineria.repository.FattorinoRepository;
import it.unibo.piadineria.service.OrdineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fattorino")
public class FattorinoController {

    private final FattorinoRepository fattorinoRepo;
    private final ServizioRepository servizioRepo;
    private final OrdineService ordineService;

    @GetMapping("/{id}")
    public String dashboard(@PathVariable Long id, Model model) {

        Fattorino f = fattorinoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fattorino non trovato"));

        List<Servizio> ordini = servizioRepo.findByFattorinoId(id);

        model.addAttribute("fattorino", f);
        model.addAttribute("ordini", ordini);

        return "fattorino-dashboard";
    }

    @PostMapping("/{fattorinoId}/stato/{servizioId}")
    public String cambiaStato(@PathVariable Long fattorinoId,
                              @PathVariable Long servizioId,
                              @RequestParam String nuovoStato) {

        ordineService.cambiaStato(servizioId, nuovoStato);

        return "redirect:/fattorino/" + fattorinoId;
    }
}
