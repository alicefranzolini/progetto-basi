package it.unibo.piadineria.controller;

import it.unibo.piadineria.model.*;
import it.unibo.piadineria.repository.ProdottoRepository;
import it.unibo.piadineria.service.OrdineService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {

    private final ProdottoRepository prodottoRepo;
    private final OrdineService ordineService;

    @GetMapping
    public String menu(Model model, HttpSession session) {
        Utente u = (Utente) session.getAttribute("utente");
        if (u == null) return "redirect:/login";

        model.addAttribute("utente", u);
        model.addAttribute("prodotti", prodottoRepo.findAll());
        return "menu";
    }

    @PostMapping("/ordine-asporto")
    public String creaOrdineAsporto(@RequestParam Map<String, String> params,
                                    HttpSession session,
                                    Model model) {

        Utente u = (Utente) session.getAttribute("utente");
        if (u == null) return "redirect:/login";

        Map<Long, Integer> prodottiQuantita = estraiQuantita(params);

        Servizio s = ordineService.creaOrdineAsporto(u.getId(), prodottiQuantita);

        model.addAttribute("ordine", s);
        model.addAttribute("utente", u);
        return "riepilogo-ordine";
    }

    @PostMapping("/ordine-delivery")
    public String creaOrdineDelivery(@RequestParam Map<String, String> params,
                                     HttpSession session,
                                     Model model) {

        Utente u = (Utente) session.getAttribute("utente");
        if (u == null) return "redirect:/login";

        Map<Long, Integer> prodottiQuantita = estraiQuantita(params);

        // per ora indirizzoId = null, poi aggiungiamo selezione indirizzo
        Servizio s = ordineService.creaOrdineDelivery(u.getId(), prodottiQuantita, null);

        model.addAttribute("ordine", s);
        model.addAttribute("utente", u);
        return "riepilogo-ordine";
    }

    @GetMapping("/ordini")
    public String ordini(HttpSession session, Model model) {
        Utente u = (Utente) session.getAttribute("utente");
        if (u == null) return "redirect:/login";

        model.addAttribute("utente", u);
        model.addAttribute("ordini", ordineService.ordiniPerUtente(u.getId()));
        return "ordini";
    }

    private Map<Long, Integer> estraiQuantita(Map<String, String> params) {
        Map<Long, Integer> prodottiQuantita = new HashMap<>();
        params.forEach((k, v) -> {
            if (k.startsWith("qta_")) {
                Long id = Long.parseLong(k.substring(4));
                int qta = Integer.parseInt(v);
                prodottiQuantita.put(id, qta);
            }
        });
        return prodottiQuantita;
    }
}
