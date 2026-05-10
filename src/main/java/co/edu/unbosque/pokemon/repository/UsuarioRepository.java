package co.edu.unbosque.pokemon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.pokemon.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	public Optional<List<Usuario>> findByUsername(String username);
	public Optional<List<Usuario>> findByRol(String rol);
}
