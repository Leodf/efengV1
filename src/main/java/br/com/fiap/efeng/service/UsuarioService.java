package br.com.fiap.efeng.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.efeng.dto.UsuarioAtualizacaoDTO;
import br.com.fiap.efeng.dto.UsuarioCadastroDTO;
import br.com.fiap.efeng.dto.UsuarioExibicaoDTO;
import br.com.fiap.efeng.exception.UsuarioNaoEncontradoException;
import br.com.fiap.efeng.model.Usuario;
import br.com.fiap.efeng.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioExibicaoDTO salvarUsuario(UsuarioCadastroDTO usuarioDTO) {

        String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDTO.senha());

        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);
        usuario.setSenha(senhaCriptografada);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return new UsuarioExibicaoDTO(usuarioSalvo);

    }

    public UsuarioExibicaoDTO buscarPorId(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            return new UsuarioExibicaoDTO(usuarioOptional.get());
        } else {
            throw new UsuarioNaoEncontradoException("Usuário não existe no banco de dados!");
        }
    }

    public List<UsuarioExibicaoDTO> listarTodos() {
        return usuarioRepository
                .findAll()
                .stream()
                .map(UsuarioExibicaoDTO::new)
                .toList();
    }

    public void excluir(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            usuarioRepository.delete(usuarioOptional.get());
        } else {
            throw new RuntimeException("Produto não encontrado!");
        }
    }

    public UsuarioAtualizacaoDTO atualizar(UsuarioAtualizacaoDTO usuarioDTO) {

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioDTO.idUsuario());

        if (usuarioOptional.isPresent()) {
            Usuario usuarioEncontrado = usuarioOptional.get();
            String senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDTO.senha());

            BeanUtils.copyProperties(usuarioDTO, usuarioEncontrado);

            if (usuarioDTO.senha() != usuarioEncontrado.getSenha()) {
                usuarioEncontrado.setSenha(senhaCriptografada);
            }
            usuarioRepository.save(usuarioEncontrado);

            return usuarioDTO;
        } else {
            throw new RuntimeException("Usuário não encontrado!");
        }
    }
}