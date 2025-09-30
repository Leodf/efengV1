package br.com.fiap.efeng.dto;

import br.com.fiap.efeng.model.Dispositivo;
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
public class DispositivoDTO {

    @Schema(example = "101", description = "Identificador único do dispositivo")
    private Long id;

    @NotBlank(message = "O nome deve ser informado")
    @Schema(example = "Ar-condicionado", description = "Nome do dispositivo")
    private String nome;

    @NotBlank(message = "A localização deve ser informada")
    @Schema(example = "Sala de reunião", description = "Local onde o dispositivo está instalado")
    private String localizacao;

    @NotNull(message = "A potência em watts deve ser informada")
    @Positive(message = "A potência deve ser um valor positivo")
    @Schema(example = "1500.0", description = "Potência nominal do dispositivo em watts")
    private Double potenciaWatts;

    @NotNull(message = "O status deve ser informado")
    @Schema(example = "ATIVO", description = "Status atual do dispositivo (ATIVO, INATIVO, etc.)")
    private String status;

    @Schema(example = "301", description = "ID do limite de consumo associado ao dispositivo")
    private Long limiteConsumoId;

    public static DispositivoDTO fromEntity(Dispositivo entity) {
        return DispositivoDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .localizacao(entity.getLocalizacao())
                .potenciaWatts(entity.getPotenciaWatts())
                .status(entity.getStatus().name())
                .limiteConsumoId(entity.getLimiteConsumo() != null ? entity.getLimiteConsumo().getId() : null)
                .build();
    }

    public Dispositivo toEntity() {
        return Dispositivo.builder()
                .id(this.id)
                .nome(this.nome)
                .localizacao(this.localizacao)
                .potenciaWatts(this.potenciaWatts)
                .status(Dispositivo.Status.valueOf(this.status))
                .build();
    }
}