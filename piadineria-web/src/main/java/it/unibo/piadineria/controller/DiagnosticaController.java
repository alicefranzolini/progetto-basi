package it.unibo.piadineria.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiagnosticaController {

    @GetMapping("/test-app")
    public String test() {
        return "Il server è attivo e il controller è stato rilevato!";
    }
}