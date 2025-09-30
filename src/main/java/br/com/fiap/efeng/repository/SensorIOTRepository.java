package br.com.fiap.efeng.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.efeng.model.Dispositivo;
import br.com.fiap.efeng.model.SensorIOT;

@Repository
public interface SensorIOTRepository extends JpaRepository<SensorIOT, Long> {
    
    List<SensorIOT> findByTipoSensor(String tipoSensor);
    
    List<SensorIOT> findByDispositivo(Dispositivo dispositivo);
} 