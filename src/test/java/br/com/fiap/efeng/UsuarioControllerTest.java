package br.com.fiap.efeng;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fiap.efeng.controller.UsuarioController;
import br.com.fiap.efeng.dto.UsuarioCadastroDTO;
import br.com.fiap.efeng.dto.UsuarioExibicaoDTO;
import br.com.fiap.efeng.exception.UsuarioNaoEncontradoException;
import br.com.fiap.efeng.model.Usuario;
import br.com.fiap.efeng.model.UsuarioRole;
import br.com.fiap.efeng.service.UsuarioService;

@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioController.class)
class UsuarioControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UsuarioService usuarioService;

	private ObjectMapper objectMapper = new ObjectMapper();

	@DisplayName("Deve criar um usuário com sucesso")
	@Test
	void deveCriarUsuarioComSucesso() throws Exception {
		UsuarioCadastroDTO usuarioCadastroDTO = new UsuarioCadastroDTO(
				null, "any name", "any@email.com", "any password", UsuarioRole.USER);

		Usuario usuarioMock = Usuario.builder()
				.idUsuario(1L)
				.email(usuarioCadastroDTO.email())
				.nome(usuarioCadastroDTO.nome())
				.senha(usuarioCadastroDTO.senha())
				.role(usuarioCadastroDTO.role())
				.build();

		UsuarioExibicaoDTO usuarioExibicaoDTO = new UsuarioExibicaoDTO(
				usuarioMock.getIdUsuario(), usuarioMock.getNome(), usuarioMock.getEmail());

		when(usuarioService.salvarUsuario(any())).thenReturn(usuarioExibicaoDTO);

		mockMvc.perform(
				post("/api/usuarios")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(usuarioCadastroDTO)))
				.andExpect(status().isCreated())
				.andExpect(
						jsonPath("$.usuarioId").value(usuarioExibicaoDTO.usuarioId()))
				.andExpect(
						jsonPath("$.nome").value(usuarioExibicaoDTO.nome()))
				.andExpect(
						jsonPath("$.email").value(usuarioExibicaoDTO.email()));

	}

	@DisplayName("Deve buscar um usuário por ID com sucesso")
	@Test
	void deveBuscarUsuarioPorIdComSucesso() throws Exception {
		Long id = 1L;
		UsuarioExibicaoDTO usuarioExibicaoDTO = new UsuarioExibicaoDTO(id, "any name", "any@email.com");

		when(usuarioService.buscarPorId(id)).thenReturn(usuarioExibicaoDTO);

		mockMvc.perform(get("/api/usuarios/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.usuarioId").value(id))
				.andExpect(jsonPath("$.nome").value("any name"))
				.andExpect(jsonPath("$.email").value("any@email.com"));
	}

	@DisplayName("Deve listar todos os usuários com sucesso")
	@Test
	void deveListarTodosUsuariosComSucesso() throws Exception {
		List<UsuarioExibicaoDTO> usuarios = List.of(
				new UsuarioExibicaoDTO(1L, "Leonardo", "leo@email.com"),
				new UsuarioExibicaoDTO(2L, "Ana", "ana@email.com"));

		when(usuarioService.listarTodos()).thenReturn(usuarios);

		mockMvc.perform(get("/api/usuarios"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].usuarioId").value(1L))
				.andExpect(jsonPath("$[0].nome").value("Leonardo"))
				.andExpect(jsonPath("$[0].email").value("leo@email.com"))
				.andExpect(jsonPath("$[1].usuarioId").value(2L))
				.andExpect(jsonPath("$[1].nome").value("Ana"))
				.andExpect(jsonPath("$[1].email").value("ana@email.com"));
	}

	@DisplayName("Deve excluir um usuário com sucesso")
	@Test
	void deveExcluirUsuarioComSucesso() throws Exception {
		Long id = 1L;

		doNothing().when(usuarioService).excluir(id);

		mockMvc.perform(delete("/api/usuarios/{id}", id))
				.andExpect(status().isNoContent());
	}

	@DisplayName("Deve retornar 404 ao buscar usuário inexistente")
	@Test
	void deveRetornar404AoBuscarUsuarioInexistente() throws Exception {
		Long idInexistente = 999L;

		when(usuarioService.buscarPorId(idInexistente))
				.thenThrow(new UsuarioNaoEncontradoException("Usuário não encontrado"));
		mockMvc.perform(get("/api/usuarios/{id}", idInexistente))
				.andExpect(status().isNotFound());
	}

}