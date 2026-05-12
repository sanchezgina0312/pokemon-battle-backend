package co.edu.unbosque.pokemon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.pokemon.entity.Usuario;

/**
 * Interfaz de repositorio para la entidad Usuario.
 * <p>
 * Define las operaciones de persistencia para gestionar los datos de los
 * usuarios en la base de datos. Extiende de CrudRepository para
 * heredar funcionalidades básicas de CRUD y añade métodos de consulta
 * personalizados basados en los atributos específicos de la entidad.
 *
 * @version 1.0
 */
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

	/**
	 * Busca usuarios por su nombre de usuario (username). 
	 * @param username El nombre de usuario a buscar.
	 * * @return Un Optional con la lista de usuarios encontrados.
	 */
	public Optional<List<Usuario>> findByUsername(String username);

	/**
	 * Busca usuarios asociados a un correo electrónico específico.
	 * @param correo El correo electrónico a buscar.
	 * * @return Un Optional con la lista de usuarios encontrados.
	 */
	public Optional<List<Usuario>> findByCorreo(String correo);

	/**
	 * Busca usuarios que tengan un rol específico (por ejemplo: admin, jugador).
	 * @param rol El rol a consultar.
	 * * @return Un Optional con la lista de usuarios encontrados.
	 */
	public Optional<List<Usuario>> findByRol(String rol);

	/**
	 * Verifica la existencia de un usuario en la base de datos mediante
	 * su nombre de usuario. 
	 * @param username El nombre de usuario a comprobar.
	 * * @return true si el registro existe, false en caso contrario.
	 */
	public boolean existsByUsername(String username);
	
	// Spring generará automáticamente: SELECT * FROM usuario WHERE nombre = ?
    List<Usuario> findByNombre(String nombre);
}