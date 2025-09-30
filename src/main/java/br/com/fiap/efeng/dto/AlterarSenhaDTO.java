package br.com.fiap.efeng.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlterarSenhaDTO {

    @NotBlank(message = "A senha atual deve ser informada")
    @Schema(example = "senhaAntiga123", description = "Senha atual do usuário")
    private String senhaAtual;

    @NotBlank(message = "A nova senha deve ser informada")
    @Size(min = 6, message = "A nova senha deve ter no mínimo 6 caracteres")
    @Schema(example = "novaSenhaSegura456", description = "Nova senha desejada pelo usuário")
    private String novaSenha;

    @NotBlank(message = "A confirmação da nova senha deve ser informada")
    @Schema(example = "novaSenhaSegura456", description = "Confirmação da nova senha")
    private String confirmacaoNovaSenha;
}