package br.com.fiap.efeng.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UsuarioAtualizacaoDTO(

        @Schema(example = "1001", description = "ID do usuário que será atualizado") Long idUsuario,

        @Schema(example = "Leonardo Silva", description = "Novo nome do usuário") String nome,

        @Schema(example = "leonardo@empresa.com", description = "Novo e-mail do usuário") String email,

        @Schema(example = "novaSenha123", description = "Nova senha do usuário") String senha) {
}