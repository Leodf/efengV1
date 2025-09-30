package br.com.fiap.efeng.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.fiap.efeng.model.ConsumoEnergia;
import br.com.fiap.efeng.model.Dispositivo;

@Repository
public interface ConsumoEnergiaRepository extends JpaRepository<ConsumoEnergia, Long> {
    
    List<ConsumoEnergia> findByDispositivo(Dispositivo dispositivo);
    
    List<ConsumoEnergia> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);
    
    @Query("SELECT c FROM ConsumoEnergia c WHERE c.dispositivo.id = :dispositivoId AND c.dataHora BETWEEN :inicio AND :fim")
    List<ConsumoEnergia> findByDispositivoAndPeriod(Long dispositivoId, LocalDateTime inicio, LocalDateTime fim);
    
    @Query("SELECT SUM(c.consumoKwh) FROM ConsumoEnergia c WHERE c.dispositivo.localizacao = :localizacao AND c.dataHora BETWEEN :inicio AND :fim")
    Double calcularConsumoTotalPorLocalizacao(String localizacao, LocalDateTime inicio, LocalDateTime fim);
} 