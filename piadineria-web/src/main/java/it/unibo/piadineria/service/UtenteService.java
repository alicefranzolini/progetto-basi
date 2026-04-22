package it.unibo.piadineria.service;

import it.unibo.piadineria.model.Utente;
import it.unibo.piadineria.repository.UtenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UtenteService {

    private final UtenteRepository repo;

    public Utente registra(String nome, String cognome, String email, String password) {
        Utente u = Utente.builder()
                .nome(nome)
                .cognome(cognome)
                .email(email)
                .password(password)
                .ruolo("UTENTE")
                .build();
        return repo.save(u);
    }

    public Optional<Utente> login(String email, String password) {
        return repo.findByEmailAndPassword(email, password);
    }
}