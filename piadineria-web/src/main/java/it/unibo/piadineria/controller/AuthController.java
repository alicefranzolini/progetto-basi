package it.unibo.piadineria.controller;

import it.unibo.piadineria.service.UtenteService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UtenteService service;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        model.addAttribute("utente", session.getAttribute("utente"));
        return "home";
    }

    @GetMapping("/registrazione")
    public String regForm() {
        return "registrazione";
    }

    @PostMapping("/registrazione")
    public String registra(@RequestParam String nome,
                           @RequestParam String cognome,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {
        service.registra(nome, cognome, email, password);
        model.addAttribute("msg", "Registrazione completata! Ora puoi accedere.");
        return "login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam String ruolo,
                        HttpSession session,
                        Model model) {

        return service.login(email, password)
                .map(u -> {
                    if (!ruolo.equals(u.getRuolo())) {
                        model.addAttribute("errore", "Ruolo non corretto per questo account");
                        return "login";
                    }
                    session.setAttribute("utente", u);
                    if ("ADMIN".equals(u.getRuolo()))     return "redirect:/admin";
                    if ("FATTORINO".equals(u.getRuolo())) return "redirect:/fattorino/ordini";
                    return "redirect:/menu";
                })
                .orElseGet(() -> {
                    model.addAttribute("errore", "Credenziali errate");
                    return "login";
                });
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}