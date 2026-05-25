package co.edu.unbosque.pokemon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.pokemon.entity.Ataque;

/**
 * Repositorio encargado de gestionar las operaciones
 * de acceso a datos de la entidad {@link Ataque}.
 * <p>
 * Extiende {@link CrudRepository} para proporcionar
 * operaciones CRUD básicas sobre la base de datos.
 * </p>
 *
 * @version 1.0
 */
public interface AtaqueRepository extends CrudRepository<Ataque, String> {
	
	/**
	 * Obtiene la lista de ataques que se encuentran baneados.
	 *
	 * @return un {@link Optional} que contiene la lista de ataques baneados
	 *         si existen registros.
	 */
	public Optional<List<Ataque>> findByEstaBaneadoTrue();
}