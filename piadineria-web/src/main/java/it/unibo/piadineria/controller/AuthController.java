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
        model.addAttribute("msg", "Registrazione completata");
        return "login";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        return service.login(email, password)
                .map(u -> {
                    session.setAttribute("utente", u);
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
