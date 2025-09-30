package br.com.fiap.efeng.dto;

import java.time.LocalDateTime;

import br.com.fiap.efeng.model.AlertaEnergia;
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
public class AlertaEnergiaDTO {

    @Schema(example = "1", description = "Identificador único do alerta")
    private Long id;

    @NotBlank(message = "A mensagem deve ser informada")
    @Schema(example = "Consumo acima do limite", description = "Mensagem de alerta gerada pelo sistema")
    private String mensagem;

    @NotNull(message = "A data do alerta deve ser informada")
    @Schema(example = "2025-09-26T10:30:00", description = "Data e hora em que o alerta foi gerado")
    private LocalDateTime dataAlerta;

    @NotNull(message = "O status deve ser informado")
    @Schema(example = "ATIVO", description = "Status atual do alerta (ATIVO, RESOLVIDO, etc.)")
    private String status;

    @Schema(example = "42", description = "ID do registro de consumo associado ao alerta")
    private Long consumoId;

    public static AlertaEnergiaDTO fromEntity(AlertaEnergia entity) {
        return AlertaEnergiaDTO.builder()
                .id(entity.getId())
                .mensagem(entity.getMensagem())
                .dataAlerta(entity.getDataAlerta())
                .status(entity.getStatus().name())
                .consumoId(entity.getConsumo() != null ? entity.getConsumo().getId() : null)
                .build();
    }

    public AlertaEnergia toEntity() {
        return AlertaEnergia.builder()
                .id(this.id)
                .mensagem(this.mensagem)
                .dataAlerta(this.dataAlerta)
                .status(AlertaEnergia.Status.valueOf(this.status))
                .build();
    }
}