package br.com.fiap.efeng.dto;

import java.time.LocalDate;

import br.com.fiap.efeng.model.SensorIOT;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorIOTDTO {

    @Schema(example = "701", description = "Identificador único do sensor IoT")
    private Long id;

    @NotBlank(message = "O tipo do sensor deve ser informado")
    @Schema(example = "Temperatura", description = "Tipo de sensor instalado (ex: Temperatura, Umidade, Movimento)")
    private String tipoSensor;

    @NotNull(message = "A data de instalação deve ser informada")
    @Schema(example = "2025-09-15", description = "Data em que o sensor foi instalado")
    private LocalDate dataInstalacao;

    @Schema(example = "101", description = "ID do dispositivo ao qual o sensor está vinculado")
    private Long dispositivoId;

    public static SensorIOTDTO fromEntity(SensorIOT entity) {
        return SensorIOTDTO.builder()
                .id(entity.getId())
                .tipoSensor(entity.getTipoSensor())
                .dataInstalacao(entity.getDataInstalacao())
                .dispositivoId(entity.getDispositivo() != null ? entity.getDispositivo().getId() : null)
                .build();
    }

    public SensorIOT toEntity() {
        return SensorIOT.builder()
                .id(this.id)
                .tipoSensor(this.tipoSensor)
                .dataInstalacao(this.dataInstalacao)
                .build();
    }
}