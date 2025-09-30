package br.com.fiap.efeng.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.efeng.dto.AlertaEnergiaDTO;
import br.com.fiap.efeng.exception.ResourceNotFoundException;
import br.com.fiap.efeng.model.AlertaEnergia;
import br.com.fiap.efeng.model.ConsumoEnergia;
import br.com.fiap.efeng.repository.AlertaEnergiaRepository;
import br.com.fiap.efeng.repository.ConsumoEnergiaRepository;

@Service
public class AlertaEnergiaService {
    
    @Autowired
    private AlertaEnergiaRepository repository;
    
    @Autowired
    private ConsumoEnergiaRepository consumoRepository;
    
    public List<AlertaEnergiaDTO> findAll() {
        return repository.findAll().stream()
                .map(AlertaEnergiaDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public AlertaEnergiaDTO findById(Long id) {
        AlertaEnergia alerta = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AlertaEnergia", "id", id));
        return AlertaEnergiaDTO.fromEntity(alerta);
    }
    
    public List<AlertaEnergiaDTO> findByStatus(String status) {
        AlertaEnergia.Status statusEnum = AlertaEnergia.Status.valueOf(status);
        return repository.findByStatus(statusEnum).stream()
                .map(AlertaEnergiaDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<AlertaEnergiaDTO> findByConsumo(Long consumoId) {
        ConsumoEnergia consumo = consumoRepository.findById(consumoId)
                .orElseThrow(() -> new ResourceNotFoundException("ConsumoEnergia", "id", consumoId));
                
        return repository.findByConsumo(consumo).stream()
                .map(AlertaEnergiaDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<AlertaEnergiaDTO> findByPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return repository.findByDataAlertaBetween(inicio, fim).stream()
                .map(AlertaEnergiaDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<AlertaEnergiaDTO> findByDispositivoId(Long dispositivoId) {
        return repository.findByDispositivoId(dispositivoId).stream()
                .map(AlertaEnergiaDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public AlertaEnergiaDTO save(AlertaEnergiaDTO dto) {
        AlertaEnergia alerta = dto.toEntity();
        
        if (dto.getConsumoId() != null) {
            ConsumoEnergia consumo = consumoRepository.findById(dto.getConsumoId())
                    .orElseThrow(() -> new ResourceNotFoundException("ConsumoEnergia", "id", dto.getConsumoId()));
            alerta.setConsumo(consumo);
        }
        
        alerta = repository.save(alerta);
        return AlertaEnergiaDTO.fromEntity(alerta);
    }
    
    @Transactional
    public AlertaEnergiaDTO update(Long id, AlertaEnergiaDTO dto) {
        AlertaEnergia existingAlerta = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AlertaEnergia", "id", id));
        
        existingAlerta.setMensagem(dto.getMensagem());
        existingAlerta.setDataAlerta(dto.getDataAlerta());
        existingAlerta.setStatus(AlertaEnergia.Status.valueOf(dto.getStatus()));
        
        if (dto.getConsumoId() != null) {
            ConsumoEnergia consumo = consumoRepository.findById(dto.getConsumoId())
                    .orElseThrow(() -> new ResourceNotFoundException("ConsumoEnergia", "id", dto.getConsumoId()));
            existingAlerta.setConsumo(consumo);
        } else {
            existingAlerta.setConsumo(null);
        }
        
        existingAlerta = repository.save(existingAlerta);
        return AlertaEnergiaDTO.fromEntity(existingAlerta);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("AlertaEnergia", "id", id);
        }
        repository.deleteById(id);
    }
} 