package br.com.fiap.efeng.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.efeng.model.Dispositivo;
import br.com.fiap.efeng.model.LimiteConsumo;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Long> {
    
    List<Dispositivo> findByLocalizacao(String localizacao);
    
    List<Dispositivo> findByStatus(Dispositivo.Status status);
    
    List<Dispositivo> findByLimiteConsumo(LimiteConsumo limiteConsumo);
} 