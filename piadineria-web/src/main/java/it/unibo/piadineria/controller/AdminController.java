package it.unibo.piadineria.controller;

import it.unibo.piadineria.repository.*;
import it.unibo.piadineria.service.OrdineService;
import it.unibo.piadineria.service.PrenotazioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UtenteRepository utenteRepo;
    private final ServizioRepository servizioRepo;
    private final PrenotazioneRepository prenotazioneRepo;
    private final FeedbackRepository feedbackRepo;
    private final FattorinoRepository fattorinoRepo;
    private final TavoloRepository tavoloRepo;
    private final OrdineService ordineService;
    private final PrenotazioneService prenotazioneService;

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("utenti", utenteRepo.findAll());
        model.addAttribute("ordini", servizioRepo.findAll());
        model.addAttribute("prenotazioni", prenotazioneRepo.findAll());
        model.addAttribute("feedback", feedbackRepo.findAll());
        model.addAttribute("fattorini", fattorinoRepo.findAll());
        model.addAttribute("tavoli", tavoloRepo.findAll());
        return "admin-dashboard";
    }

    // ── ORDINI: cambia stato e assegna fattorino ──────────────────────────

    @PostMapping("/admin/ordini/{id}/stato")
    public String cambiaStatoOrdine(@PathVariable Long id,
                                    @RequestParam String nuovoStato) {
        ordineService.cambiaStato(id, nuovoStato);
        return "redirect:/admin";
    }

    @PostMapping("/admin/ordini/{id}/fattorino")
    public String assegnaFattorino(@PathVariable Long id,
                                   @RequestParam Long fattorinoId) {
        ordineService.assegnaFattorino(id, fattorinoId);
        return "redirect:/admin";
    }

    // ── PRENOTAZIONI: cambia stato ────────────────────────────────────────

    @PostMapping("/admin/prenotazioni/{id}/stato")
    public String cambiaStatoPrenotazione(@PathVariable Long id,
                                          @RequestParam String nuovoStato) {
        prenotazioneService.cambiaStato(id, nuovoStato);
        return "redirect:/admin";
    }
}