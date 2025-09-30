package br.com.fiap.efeng.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.efeng.dto.DispositivoDTO;
import br.com.fiap.efeng.exception.ResourceNotFoundException;
import br.com.fiap.efeng.model.Dispositivo;
import br.com.fiap.efeng.model.LimiteConsumo;
import br.com.fiap.efeng.repository.DispositivoRepository;
import br.com.fiap.efeng.repository.LimiteConsumoRepository;

@Service
public class DispositivoService {
    
    @Autowired
    private DispositivoRepository repository;
    
    @Autowired
    private LimiteConsumoRepository limiteRepository;
    
    public List<DispositivoDTO> findAll() {
        return repository.findAll().stream()
                .map(DispositivoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public DispositivoDTO findById(Long id) {
        Dispositivo dispositivo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dispositivo", "id", id));
        return DispositivoDTO.fromEntity(dispositivo);
    }
    
    public List<DispositivoDTO> findByLocalizacao(String localizacao) {
        return repository.findByLocalizacao(localizacao).stream()
                .map(DispositivoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<DispositivoDTO> findByStatus(String status) {
        Dispositivo.Status statusEnum = Dispositivo.Status.valueOf(status);
        return repository.findByStatus(statusEnum).stream()
                .map(DispositivoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<DispositivoDTO> findByLimiteConsumo(Long limiteId) {
        LimiteConsumo limite = limiteRepository.findById(limiteId)
                .orElseThrow(() -> new ResourceNotFoundException("LimiteConsumo", "id", limiteId));
                
        return repository.findByLimiteConsumo(limite).stream()
                .map(DispositivoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public DispositivoDTO save(DispositivoDTO dto) {
        Dispositivo dispositivo = dto.toEntity();
        
        if (dto.getLimiteConsumoId() != null) {
            LimiteConsumo limite = limiteRepository.findById(dto.getLimiteConsumoId())
                    .orElseThrow(() -> new ResourceNotFoundException("LimiteConsumo", "id", dto.getLimiteConsumoId()));
            dispositivo.setLimiteConsumo(limite);
        }
        
        dispositivo = repository.save(dispositivo);
        return DispositivoDTO.fromEntity(dispositivo);
    }
    
    @Transactional
    public DispositivoDTO update(Long id, DispositivoDTO dto) {
        Dispositivo existingDispositivo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dispositivo", "id", id));
        
        existingDispositivo.setNome(dto.getNome());
        existingDispositivo.setLocalizacao(dto.getLocalizacao());
        existingDispositivo.setPotenciaWatts(dto.getPotenciaWatts());
        existingDispositivo.setStatus(Dispositivo.Status.valueOf(dto.getStatus()));
        
        if (dto.getLimiteConsumoId() != null) {
            LimiteConsumo limite = limiteRepository.findById(dto.getLimiteConsumoId())
                    .orElseThrow(() -> new ResourceNotFoundException("LimiteConsumo", "id", dto.getLimiteConsumoId()));
            existingDispositivo.setLimiteConsumo(limite);
        } else {
            existingDispositivo.setLimiteConsumo(null);
        }
        
        existingDispositivo = repository.save(existingDispositivo);
        return DispositivoDTO.fromEntity(existingDispositivo);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Dispositivo", "id", id);
        }
        repository.deleteById(id);
    }
} 