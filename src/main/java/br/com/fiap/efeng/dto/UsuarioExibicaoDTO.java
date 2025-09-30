package br.com.fiap.efeng.dto;

import br.com.fiap.efeng.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

public record UsuarioExibicaoDTO(

    @Schema(example = "1001", description = "ID do usuário exibido") Long usuarioId,

    @Schema(example = "Leonardo Silva", description = "Nome completo do usuário") String nome,

    @Schema(example = "leonardo@empresa.com", description = "E-mail do usuário") String email) {
  public UsuarioExibicaoDTO(Usuario usuario) {
    this(
        usuario.getIdUsuario(),
        usuario.getNome(),
        usuario.getEmail());
  }
}