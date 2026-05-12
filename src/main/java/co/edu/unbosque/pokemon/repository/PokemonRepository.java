package co.edu.unbosque.pokemon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.pokemon.entity.Pokemon;

/**
 * Interfaz de repositorio para la entidad Pokemon.
 * <p>
 * Gestiona la persistencia de los pokemones capturados, permitiendo realizar
 * consultas por apodo, estado y por el entrenador propietario.
 * </p>
 *
 * @version 1.0
 */
@Repository
public interface PokemonRepository extends CrudRepository<Pokemon, Long> {

	/**
	 * Busca pokemones por su apodo.
	 * @param apodo El apodo asignado al pokemon.
	 * @return Un Optional con la lista de pokemones que coinciden.
	 */
	public Optional<List<Pokemon>> findByApodo(String apodo);

	/**
	 * Busca todos los pokemones que pertenecen a un usuario específico.
	 * @param idUsuarioPropietario El ID del usuario entrenador.
	 * @return Un Optional con la lista de pokemones del entrenador.
	 */
	public Optional<List<Pokemon>> findByIdUsuarioPropietario(Long idUsuarioPropietario);

	/**
	 * Busca pokemones según su estado actual (ej: OK, DEBILITADO, QUEMADO).
	 * @param estado El estado a consultar.
	 * @return Un Optional con la lista de pokemones en ese estado.
	 */
	public Optional<List<Pokemon>> findByEstado(String estado);

	/**
	 * Verifica si un pokemon existe en la base de datos por su ID de la PokeAPI.
	 * Útil para evitar duplicados si la lógica del negocio lo requiere.
	 * @param pokeApiId ID proveniente de la API externa.
	 * @return true si existe, false en caso contrario.
	 */
	public boolean existsByPokeApiId(Integer pokeApiId);
	
}