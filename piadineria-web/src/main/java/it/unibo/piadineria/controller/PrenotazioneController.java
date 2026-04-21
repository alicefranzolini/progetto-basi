package it.unibo.piadineria.controller;

import it.unibo.piadineria.model.Prenotazione;
import it.unibo.piadineria.model.Utente;
import it.unibo.piadineria.service.PrenotazioneService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService service;

    @GetMapping
    public String formPrenotazione(HttpSession session, Model model) {
        Utente u = (Utente) session.getAttribute("utente");
        if (u == null) return "redirect:/login";

        model.addAttribute("utente", u);
        return "prenotazione-form";
    }

    @PostMapping
    public String creaPrenotazione(@RequestParam String data,
                                   @RequestParam String ora,
                                   @RequestParam int persone,
                                   @RequestParam(required = false) String note,
                                   HttpSession session,
                                   Model model) {

        Utente u = (Utente) session.getAttribute("utente");
        if (u == null) return "redirect:/login";

        Prenotazione p = service.creaPrenotazione(
                u.getId(),
                LocalDate.parse(data),
                LocalTime.parse(ora),
                persone,
                note
        );

        model.addAttribute("prenotazione", p);
        return "prenotazione-riepilogo";
    }

    @GetMapping("/mie")
    public String miePrenotazioni(HttpSession session, Model model) {
        Utente u = (Utente) session.getAttribute("utente");
        if (u == null) return "redirect:/login";

        model.addAttribute("prenotazioni", service.prenotazioniUtente(u.getId()));
        return "prenotazioni-utente";
    }
}
