package it.unibo.piadineria.controller;

import it.unibo.piadineria.model.Utente;
import it.unibo.piadineria.service.FeedbackService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService service;

    @GetMapping("/ordine/{id}")
    public String formOrdine(@PathVariable Long id, Model model) {
        model.addAttribute("servizioId", id);
        return "feedback-ordine-form";
    }

    @PostMapping("/ordine/{id}")
    public String salvaOrdine(@PathVariable Long id,
                              @RequestParam int voto,
                              @RequestParam String commento,
                              HttpSession session) {

        Utente u = (Utente) session.getAttribute("utente");
        if (u == null) return "redirect:/login";

        service.creaFeedbackOrdine(u.getId(), id, voto, commento);

        return "redirect:/feedback/miei";
    }

    @GetMapping("/prenotazione/{id}")
    public String formPrenotazione(@PathVariable Long id, Model model) {
        model.addAttribute("prenotazioneId", id);
        return "feedback-prenotazione-form";
    }

    @PostMapping("/prenotazione/{id}")
    public String salvaPrenotazione(@PathVariable Long id,
                                    @RequestParam int voto,
                                    @RequestParam String commento,
                                    HttpSession session) {

        Utente u = (Utente) session.getAttribute("utente");
        if (u == null) return "redirect:/login";

        service.creaFeedbackPrenotazione(u.getId(), id, voto, commento);

        return "redirect:/feedback/miei";
    }

    @GetMapping("/miei")
    public String mieiFeedback(HttpSession session, Model model) {
        Utente u = (Utente) session.getAttribute("utente");
        if (u == null) return "redirect:/login";

        model.addAttribute("feedback", service.feedbackUtente(u.getId()));
        return "feedback-utente";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("feedback", service.tuttiFeedback());
        return "feedback-admin";
    }
}
