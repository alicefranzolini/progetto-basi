package it.unibo.piadineria.controller;

import it.unibo.piadineria.repository.IngredienteRepository;
import it.unibo.piadineria.repository.MovimentoMagazzinoRepository;
import it.unibo.piadineria.service.MagazzinoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/magazzino")
public class MagazzinoController {

    private final IngredienteRepository ingredienteRepo;
    private final MovimentoMagazzinoRepository movimentoRepo;
    private final MagazzinoService magazzinoService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("ingredienti", ingredienteRepo.findAll());
        model.addAttribute("movimenti", movimentoRepo.findAll());
        return "magazzino-dashboard";
    }

    @PostMapping("/carico")
    public String carico(@RequestParam Long ingredienteId,
                         @RequestParam double quantita) {
        magazzinoService.carico(ingredienteId, quantita);
        return "redirect:/admin/magazzino";
    }

    @PostMapping("/scarico")
    public String scarico(@RequestParam Long ingredienteId,
                          @RequestParam double quantita) {
        magazzinoService.scarico(ingredienteId, quantita);
        return "redirect:/admin/magazzino";
    }
}
