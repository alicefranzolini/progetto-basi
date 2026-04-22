package it.unibo.piadineria.repository;

import it.unibo.piadineria.model.ProdottoIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdottoIngredienteRepository extends JpaRepository<ProdottoIngrediente, Long> {
    List<ProdottoIngrediente> findByProdottoId(Long prodottoId);
}
