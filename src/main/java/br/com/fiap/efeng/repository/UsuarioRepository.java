package br.com.fiap.efeng.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.efeng.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);
}