package br.com.fiap.efeng.dto;

import java.time.LocalDate;

import br.com.fiap.efeng.model.LimiteConsumo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LimiteConsumoDTO {

    @Schema(example = "301", description = "Identificador único do limite de consumo")
    private Long id;

    @NotBlank(message = "A localização deve ser informada")
    @Schema(example = "Sala de servidores", description = "Local onde o limite de consumo será aplicado")
    private String localizacao;

    @NotNull(message = "O limite de kWh diário deve ser informado")
    @Positive(message = "O limite deve ser um valor positivo")
    @Schema(example = "25.5", description = "Limite diário de consumo de energia em kWh")
    private Double limiteKwhDia;

    @NotNull(message = "A data de início deve ser informada")
    @Schema(example = "2025-10-01", description = "Data de início da vigência do limite")
    private LocalDate dataInicio;

    @Schema(example = "2025-12-31", description = "Data de fim da vigência do limite (opcional)")
    private LocalDate dataFim;

    public static LimiteConsumoDTO fromEntity(LimiteConsumo entity) {
        return LimiteConsumoDTO.builder()
                .id(entity.getId())
                .localizacao(entity.getLocalizacao())
                .limiteKwhDia(entity.getLimiteKwhDia())
                .dataInicio(entity.getDataInicio())
                .dataFim(entity.getDataFim())
                .build();
    }

    public LimiteConsumo toEntity() {
        return LimiteConsumo.builder()
                .id(this.id)
                .localizacao(this.localizacao)
                .limiteKwhDia(this.limiteKwhDia)
                .dataInicio(this.dataInicio)
                .dataFim(this.dataFim)
                .build();
    }
}