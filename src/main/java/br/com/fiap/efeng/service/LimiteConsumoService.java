package br.com.fiap.efeng.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fiap.efeng.dto.LimiteConsumoDTO;
import br.com.fiap.efeng.exception.ResourceNotFoundException;
import br.com.fiap.efeng.model.LimiteConsumo;
import br.com.fiap.efeng.repository.LimiteConsumoRepository;

@Service
public class LimiteConsumoService {
    
    @Autowired
    private LimiteConsumoRepository repository;
    
    public List<LimiteConsumoDTO> findAll() {
        return repository.findAll().stream()
                .map(LimiteConsumoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public LimiteConsumoDTO findById(Long id) {
        LimiteConsumo limiteConsumo = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LimiteConsumo", "id", id));
        return LimiteConsumoDTO.fromEntity(limiteConsumo);
    }
    
    public List<LimiteConsumoDTO> findByLocalizacao(String localizacao) {
        return repository.findByLocalizacao(localizacao).stream()
                .map(LimiteConsumoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    public List<LimiteConsumoDTO> findValidLimits(LocalDate data) {
        return repository.findValidLimits(data).stream()
                .map(LimiteConsumoDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public LimiteConsumoDTO save(LimiteConsumoDTO dto) {
        LimiteConsumo limiteConsumo = dto.toEntity();
        limiteConsumo = repository.save(limiteConsumo);
        return LimiteConsumoDTO.fromEntity(limiteConsumo);
    }
    
    @Transactional
    public LimiteConsumoDTO update(Long id, LimiteConsumoDTO dto) {
        LimiteConsumo existingLimite = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LimiteConsumo", "id", id));
        
        existingLimite.setLocalizacao(dto.getLocalizacao());
        existingLimite.setLimiteKwhDia(dto.getLimiteKwhDia());
        existingLimite.setDataInicio(dto.getDataInicio());
        existingLimite.setDataFim(dto.getDataFim());
        
        existingLimite = repository.save(existingLimite);
        return LimiteConsumoDTO.fromEntity(existingLimite);
    }
    
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("LimiteConsumo", "id", id);
        }
        repository.deleteById(id);
    }
} 