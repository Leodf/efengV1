package br.com.fiap.efeng.dto;

import java.time.LocalDateTime;

import br.com.fiap.efeng.model.ConsumoEnergia;
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
public class ConsumoDTO {

    @Schema(example = "1001", description = "Identificador único do registro de consumo")
    private Long id;

    @NotNull(message = "A data e hora devem ser informadas")
    @Schema(example = "2025-09-26T08:45:00", description = "Data e hora do registro de consumo")
    private LocalDateTime dataHora;

    @NotNull(message = "O consumo em kWh deve ser informado")
    @Positive(message = "O consumo deve ser um valor positivo")
    @Schema(example = "12.75", description = "Quantidade de energia consumida em kWh")
    private Double consumoKwh;

    @NotNull(message = "O dispositivo deve ser informado")
    @Schema(example = "501", description = "ID do dispositivo que gerou o consumo")
    private Long dispositivoId;

    public static ConsumoDTO fromEntity(ConsumoEnergia entity) {
        return ConsumoDTO.builder()
                .id(entity.getId())
                .dataHora(entity.getDataHora())
                .consumoKwh(entity.getConsumoKwh())
                .dispositivoId(entity.getDispositivo() != null ? entity.getDispositivo().getId() : null)
                .build();
    }

    public ConsumoEnergia toEntity() {
        return ConsumoEnergia.builder()
                .id(this.id)
                .dataHora(this.dataHora)
                .consumoKwh(this.consumoKwh)
                .build();
    }
}