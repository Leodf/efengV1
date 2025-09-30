package br.com.fiap.efeng.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.efeng.dto.SensorIOTDTO;
import br.com.fiap.efeng.exception.ResourceNotFoundException;
import br.com.fiap.efeng.model.Dispositivo;
import br.com.fiap.efeng.model.SensorIOT;
import br.com.fiap.efeng.repository.DispositivoRepository;
import br.com.fiap.efeng.repository.SensorIOTRepository;

@Service
public class SensorIOTService {
    
    @Autowired
    private SensorIOTRepository repository;
    
    @Autowired
    private DispositivoRepository dispositivoRepository;
    
    public List<SensorIOTDTO> findAll() {
        return repository.findAll().stream()
                .map(SensorIOTDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public SensorIOTDTO findById(Long id) {
        SensorIOT sensor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SensorIOT", "id", id));
        return SensorIOTDTO.fromEntity(sensor);
    }
    
    public List<SensorIOTDTO> findByTipoSensor(String tipoSensor) {
        return repository.findByTipoSensor(tipoSensor).stream()
                .map(SensorIOTDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<SensorIOTDTO> findByDispositivo(Long dispositivoId) {
        Dispositivo dispositivo = dispositivoRepository.findById(dispositivoId)
                .orElseThrow(() -> new ResourceNotFoundException("Dispositivo", "id", dispositivoId));
                
        return repository.findByDispositivo(dispositivo).stream()
                .map(SensorIOTDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public SensorIOTDTO save(SensorIOTDTO dto) {
        SensorIOT sensor = dto.toEntity();
        
        if (dto.getDispositivoId() != null) {
            Dispositivo dispositivo = dispositivoRepository.findById(dto.getDispositivoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Dispositivo", "id", dto.getDispositivoId()));
            sensor.setDispositivo(dispositivo);
        }
        
        sensor = repository.save(sensor);
        return SensorIOTDTO.fromEntity(sensor);
    }
    
    @Transactional
    public SensorIOTDTO update(Long id, SensorIOTDTO dto) {
        SensorIOT existingSensor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SensorIOT", "id", id));
        
        existingSensor.setTipoSensor(dto.getTipoSensor());
        existingSensor.setDataInstalacao(dto.getDataInstalacao());
        
        if (dto.getDispositivoId() != null) {
            Dispositivo dispositivo = dispositivoRepository.findById(dto.getDispositivoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Dispositivo", "id", dto.getDispositivoId()));
            existingSensor.setDispositivo(dispositivo);
        } else {
            existingSensor.setDispositivo(null);
        }
        
        existingSensor = repository.save(existingSensor);
        return SensorIOTDTO.fromEntity(existingSensor);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("SensorIOT", "id", id);
        }
        repository.deleteById(id);
    }
} 