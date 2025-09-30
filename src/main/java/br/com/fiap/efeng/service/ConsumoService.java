package br.com.fiap.efeng.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.efeng.dto.ConsumoDTO;
import br.com.fiap.efeng.exception.ResourceNotFoundException;
import br.com.fiap.efeng.model.ConsumoEnergia;
import br.com.fiap.efeng.model.Dispositivo;
import br.com.fiap.efeng.repository.ConsumoEnergiaRepository;
import br.com.fiap.efeng.repository.DispositivoRepository;

@Service
public class ConsumoService {
    
    @Autowired
    private ConsumoEnergiaRepository repository;
    
    @Autowired
    private DispositivoRepository dispositivoRepository;
    
    public List<ConsumoDTO> findAll() {
        return repository.findAll().stream()
                .map(ConsumoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public ConsumoDTO findById(Long id) {
        ConsumoEnergia consumo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ConsumoEnergia", "id", id));
        return ConsumoDTO.fromEntity(consumo);
    }
    
    public List<ConsumoDTO> findByDispositivo(Long dispositivoId) {
        Dispositivo dispositivo = dispositivoRepository.findById(dispositivoId)
                .orElseThrow(() -> new ResourceNotFoundException("Dispositivo", "id", dispositivoId));
                
        return repository.findByDispositivo(dispositivo).stream()
                .map(ConsumoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<ConsumoDTO> findByPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return repository.findByDataHoraBetween(inicio, fim).stream()
                .map(ConsumoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<ConsumoDTO> findByDispositivoAndPeriod(Long dispositivoId, LocalDateTime inicio, LocalDateTime fim) {
        return repository.findByDispositivoAndPeriod(dispositivoId, inicio, fim).stream()
                .map(ConsumoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public Double calcularConsumoTotalPorLocalizacao(String localizacao, LocalDateTime inicio, LocalDateTime fim) {
        return repository.calcularConsumoTotalPorLocalizacao(localizacao, inicio, fim);
    }
    
    @Transactional
    public ConsumoDTO save(ConsumoDTO dto) {
        ConsumoEnergia consumo = dto.toEntity();
        
        if (dto.getDispositivoId() != null) {
            Dispositivo dispositivo = dispositivoRepository.findById(dto.getDispositivoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Dispositivo", "id", dto.getDispositivoId()));
            consumo.setDispositivo(dispositivo);
        }
        
        consumo = repository.save(consumo);
        return ConsumoDTO.fromEntity(consumo);
    }
    
    @Transactional
    public ConsumoDTO update(Long id, ConsumoDTO dto) {
        ConsumoEnergia existingConsumo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ConsumoEnergia", "id", id));
        
        existingConsumo.setDataHora(dto.getDataHora());
        existingConsumo.setConsumoKwh(dto.getConsumoKwh());
        
        if (dto.getDispositivoId() != null) {
            Dispositivo dispositivo = dispositivoRepository.findById(dto.getDispositivoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Dispositivo", "id", dto.getDispositivoId()));
            existingConsumo.setDispositivo(dispositivo);
        } else {
            existingConsumo.setDispositivo(null);
        }
        
        existingConsumo = repository.save(existingConsumo);
        return ConsumoDTO.fromEntity(existingConsumo);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("ConsumoEnergia", "id", id);
        }
        repository.deleteById(id);
    }
} 