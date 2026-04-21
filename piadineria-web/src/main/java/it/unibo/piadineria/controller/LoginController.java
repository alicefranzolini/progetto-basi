package it.unibo.piadineria.controller;

import it.unibo.piadineria.model.Utente;
import it.unibo.piadineria.repository.UtenteRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UtenteRepository utenteRepo;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Utente u = utenteRepo.findByEmail(email)
                .orElse(null);

        if (u == null || !u.getPassword().equals(password)) {
            model.addAttribute("errore", "Credenziali non valide");
            return "login";
        }

        session.setAttribute("utente", u);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
