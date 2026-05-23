package co.edu.unbosque.pokemon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.pokemon.entity.Inventario;

/**
 * Repositorio encargado de gestionar las operaciones
 * de acceso a datos de la entidad {@link Inventario}.
 * <p>
 * Extiende {@link CrudRepository} para proporcionar
 * operaciones CRUD básicas sobre la base de datos.
 * </p>
 *
 * @version 1.0
 */
@Repository
public interface InventarioRepository extends CrudRepository<Inventario, Long> {

	/**
	 * Busca los inventarios asociados a un usuario.
	 *
	 * @param idUsuario identificador del usuario.
	 * @return un {@link Optional} que contiene la lista de inventarios
	 *         del usuario si existen registros.
	 */
	public Optional<List<Inventario>> findByIdUsuario(long idUsuario);
}