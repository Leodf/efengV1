package br.com.fiap.efeng.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenDTO(
    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...", description = "Token JWT gerado após autenticação") String token) {
}