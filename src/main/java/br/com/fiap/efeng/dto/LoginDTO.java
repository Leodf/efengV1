package br.com.fiap.efeng.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

public record LoginDTO(

                @NotBlank(message = "O e-mail do usuário é obrigatório!") @Email(message = "O e-mail do usuário não é válido!") @Schema(example = "leonardo@empresa.com", description = "E-mail do usuário para autenticação") String email,

                @NotBlank(message = "A senha é obrigatória") @Size(min = 6, max = 20, message = "A senha deve conter entre 6 e 20 caracteres!") @Schema(example = "senhaSegura123", description = "Senha do usuário") String senha) {
}