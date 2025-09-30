package br.com.fiap.efeng.dto;

import br.com.fiap.efeng.model.UsuarioRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

public record UsuarioCadastroDTO(

    Long idUsuario,

    @NotBlank(message = "O Nome do usuário é obrigatório") @Schema(example = "Leonardo Silva", description = "Nome completo do usuário") String nome,

    @NotBlank(message = "O e-mail do usuário é obrigatório") @Email(message = "O e-mail do usuário não é válido") @Schema(example = "leonardo@empresa.com", description = "E-mail do usuário") String email,

    @NotBlank(message = "A senha é obrigatória") @Size(min = 6, max = 20, message = "A senha deve conter entre 6 e 20 caracteres!") @Schema(example = "senhaSegura123", description = "Senha de acesso do usuário") String senha,

    @Schema(example = "ADMIN", description = "Papel do usuário no sistema (ex: ADMIN, USER)") UsuarioRole role) {
}