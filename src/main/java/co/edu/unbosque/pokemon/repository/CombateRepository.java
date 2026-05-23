package co.edu.unbosque.pokemon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.pokemon.entity.Combate;

/**
 * Repositorio encargado de gestionar las operaciones
 * de acceso a datos de la entidad {@link Combate}.
 * <p>
 * Extiende {@link CrudRepository} para proporcionar
 * operaciones CRUD básicas sobre la base de datos.
 * </p>
 *
 * @version 1.0
 */
public interface CombateRepository extends CrudRepository<Combate, Long> {

	/**
	 * Busca los combates asociados a un jugador específico.
	 *
	 * @param idUsuarioJugador identificador del usuario jugador.
	 * @return un {@link Optional} que contiene la lista de combates
	 *         del jugador si existen registros.
	 */
	public Optional<List<Combate>> findByIdUsuarioJugador(long idUsuarioJugador);
}