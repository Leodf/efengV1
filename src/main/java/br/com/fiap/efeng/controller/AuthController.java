package br.com.fiap.efeng.controller;

import br.com.fiap.efeng.config.security.TokenService;
import br.com.fiap.efeng.dto.LoginDTO;
import br.com.fiap.efeng.dto.TokenDTO;
import br.com.fiap.efeng.dto.UsuarioCadastroDTO;
import br.com.fiap.efeng.dto.UsuarioExibicaoDTO;
import br.com.fiap.efeng.model.Usuario;
import br.com.fiap.efeng.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoints de login e registro")
public class AuthController {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private TokenService tokenService;

  @Operation(summary = "Login", security = @SecurityRequirement(name = ""))
  @PostMapping("/login")
  public ResponseEntity<TokenDTO> login(
      @RequestBody @Valid LoginDTO loginDto) {
    UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(loginDto.email(),
        loginDto.senha());
    Authentication auth = authenticationManager.authenticate(usernamePassword);
    String token = tokenService.gerarToken((Usuario) auth.getPrincipal());

    return ResponseEntity
        .ok(new TokenDTO(token));
  }

  @Operation(summary = "Register", security = @SecurityRequirement(name = ""))
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<UsuarioExibicaoDTO> registrar(
      @RequestBody @Valid UsuarioCadastroDTO usuarioCadastroDTO) {
    UsuarioExibicaoDTO usuarioSalvo = null;
    usuarioSalvo = usuarioService.salvarUsuario(usuarioCadastroDTO);
    return ResponseEntity.ok(usuarioSalvo);
  }
}
