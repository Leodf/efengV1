package br.com.fiap.efeng.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.fiap.efeng.model.LimiteConsumo;

@Repository
public interface LimiteConsumoRepository extends JpaRepository<LimiteConsumo, Long> {
    
    List<LimiteConsumo> findByLocalizacao(String localizacao);
    
    @Query("SELECT l FROM LimiteConsumo l WHERE l.dataInicio <= :data AND (l.dataFim IS NULL OR l.dataFim >= :data)")
    List<LimiteConsumo> findValidLimits(LocalDate data);
} 