package br.com.fiap.efeng.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.fiap.efeng.model.AlertaEnergia;
import br.com.fiap.efeng.model.ConsumoEnergia;

@Repository
public interface AlertaEnergiaRepository extends JpaRepository<AlertaEnergia, Long> {
    
    List<AlertaEnergia> findByStatus(AlertaEnergia.Status status);
    
    List<AlertaEnergia> findByConsumo(ConsumoEnergia consumo);
    
    List<AlertaEnergia> findByDataAlertaBetween(LocalDateTime inicio, LocalDateTime fim);
    
    @Query("SELECT a FROM AlertaEnergia a WHERE a.consumo.dispositivo.id = :dispositivoId")
    List<AlertaEnergia> findByDispositivoId(Long dispositivoId);
} 