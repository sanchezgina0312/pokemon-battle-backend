package co.edu.unbosque.pokemon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.pokemon.entity.Tienda;

/**
 * Repositorio encargado de gestionar las operaciones
 * de acceso a datos de la entidad {@link Tienda}.
 * <p>
 * Extiende {@link CrudRepository} para proporcionar
 * operaciones CRUD básicas sobre la base de datos.
 * </p>
 *
 * @version 1.0
 */
public interface TiendaRepository extends CrudRepository<Tienda, Long> {

	/**
	 * Busca las tiendas asociadas a un usuario.
	 *
	 * @param idUsuario identificador del usuario.
	 * @return un {@link Optional} que contiene la lista de tiendas
	 *         del usuario si existen registros.
	 */
	public Optional<List<Tienda>> findByIdUsuario(long idUsuario);
}