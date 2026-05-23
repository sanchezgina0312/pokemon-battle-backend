package co.edu.unbosque.pokemon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.pokemon.entity.CentroPokemon;

/**
 * Repositorio encargado de gestionar las operaciones
 * de acceso a datos de la entidad {@link CentroPokemon}.
 * <p>
 * Extiende {@link CrudRepository} para proporcionar
 * operaciones CRUD básicas sobre la base de datos.
 * </p>
 *
 * @version 1.0
 */
public interface CentroPokemonRepository extends CrudRepository<CentroPokemon, Long> {

	/**
	 * Busca los registros de centros Pokémon asociados a un usuario.
	 *
	 * @param idUsuario identificador del usuario.
	 * @return un {@link Optional} que contiene la lista de centros Pokémon
	 *         del usuario si existen registros.
	 */
	public Optional<List<CentroPokemon>> findByIdUsuario(long idUsuario);
}