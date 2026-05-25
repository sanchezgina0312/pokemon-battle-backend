package co.edu.unbosque.pokemon.repository;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.pokemon.entity.Item;

/**
 * Repositorio encargado de gestionar las operaciones
 * de acceso a datos de la entidad {@link Item}.
 * <p>
 * Extiende {@link CrudRepository} para proporcionar
 * operaciones CRUD básicas sobre la base de datos.
 * </p>
 *
 * @version 1.0
 */
public interface ItemRepository extends CrudRepository<Item, Long> {

}