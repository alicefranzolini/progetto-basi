package it.unibo.piadineria.controller;

import it.unibo.piadineria.model.Fattorino;
import it.unibo.piadineria.model.Servizio;
import it.unibo.piadineria.model.Utente;
import it.unibo.piadineria.repository.FattorinoRepository;
import it.unibo.piadineria.repository.ServizioRepository;
import it.unibo.piadineria.service.OrdineService;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/ordini")
    public String dashboard(HttpSession session, Model model) {
        Utente u = (Utente) session.getAttribute("utente");
        if (u == null) return "redirect:/login";

        Fattorino f = fattorinoRepo.findByUtenteId(u.getId())
                .orElseThrow(() -> new IllegalArgumentException("Fattorino non trovato"));

        List<Servizio> ordini = servizioRepo.findByFattorinoId(f.getId());

        model.addAttribute("fattorino", f);
        model.addAttribute("ordini", ordini);
        return "fattorino-dashboard";
    }

    @PostMapping("/stato/{servizioId}")
    public String cambiaStato(@PathVariable Long servizioId,
                              @RequestParam String nuovoStato,
                              HttpSession session) {
        Utente u = (Utente) session.getAttribute("utente");
        if (u == null) return "redirect:/login";

        ordineService.cambiaStato(servizioId, nuovoStato);
        return "redirect:/fattorino/ordini";
    }
}