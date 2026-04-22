package it.unibo.piadineria.controller;

import it.unibo.piadineria.model.Ingrediente;
import it.unibo.piadineria.repository.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/ingredienti")
public class IngredienteController {

    private final IngredienteRepository ingredienteRepo;

    @GetMapping
    public String lista(Model model) {
        model.addAttribute("ingredienti", ingredienteRepo.findAll());
        return "ingredienti-list";
    }

    @GetMapping("/nuovo")
    public String nuovoForm(Model model) {
        model.addAttribute("ingrediente", new Ingrediente());
        return "ingrediente-form";
    }

    @PostMapping("/salva")
    public String salva(@ModelAttribute Ingrediente ingrediente) {
        ingredienteRepo.save(ingrediente);
        return "redirect:/admin/ingredienti";
    }

    @GetMapping("/modifica/{id}")
    public String modificaForm(@PathVariable Long id, Model model) {
        Ingrediente ing = ingredienteRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingrediente non trovato"));
        model.addAttribute("ingrediente", ing);
        return "ingrediente-form";
    }

    @GetMapping("/elimina/{id}")
    public String elimina(@PathVariable Long id) {
        ingredienteRepo.deleteById(id);
        return "redirect:/admin/ingredienti";
    }
}
