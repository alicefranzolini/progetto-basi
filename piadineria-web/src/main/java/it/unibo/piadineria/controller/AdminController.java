package it.unibo.piadineria.controller;

import it.unibo.piadineria.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UtenteRepository utenteRepo;
    private final ServizioRepository servizioRepo;
    private final PrenotazioneRepository prenotazioneRepo;
    private final FeedbackRepository feedbackRepo;
    private final FattorinoRepository fattorinoRepo;
    private final TavoloRepository tavoloRepo;

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
}
