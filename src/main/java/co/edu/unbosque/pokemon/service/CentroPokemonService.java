package co.edu.unbosque.pokemon.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.entity.CentroPokemon;
import co.edu.unbosque.pokemon.entity.Pokemon;
import co.edu.unbosque.pokemon.repository.CentroPokemonRepository;
import co.edu.unbosque.pokemon.repository.PokemonRepository;

/**
 * Servicio encargado de la gestión del Centro Pokémon.
 * 
 * Permite curar Pokémon individuales o equipos completos,
 * registrando cada curación en el historial del centro Pokémon.
 */
@Service
public class CentroPokemonService {
	
	@Autowired
	private PokemonRepository pokeRep;
	
	@Autowired
	private CentroPokemonRepository centroRep;

	/**
	 * Cura completamente un Pokémon, restaurando su salud al máximo.
	 * 
	 * Además, registra la acción en el historial del Centro Pokémon.
	 *
	 * @param idPokemon identificador del Pokémon a curar
	 * @return 0 si la operación fue exitosa, 1 si el Pokémon no existe
	 */
	public int curar(long idPokemon) {
		Optional<Pokemon> pOpt = pokeRep.findById(idPokemon);

		if (pOpt.isPresent()) {
			Pokemon p = pOpt.get();
			p.setSaludActual(p.getSaludMaxima());

			pokeRep.save(p);
			centroRep.save(new CentroPokemon(
					p.getIdUsuarioPropietario(),
					idPokemon,
					LocalDateTime.now()
			));

			return 0;
		}
		return 1;
	}
	
	/**
	 * Cura un conjunto de Pokémon pertenecientes a un usuario autenticado.
	 * 
	 * Solo se curan los Pokémon que pertenezcan al usuario propietario.
	 * Cada curación se registra en el historial del Centro Pokémon.
	 *
	 * @param idsPokemon lista de identificadores de Pokémon a curar
	 * @param idUsuarioPropietarioAutenticado ID del usuario autenticado
	 * @return 0 si al menos un Pokémon fue curado, 1 si ninguno fue procesado
	 */
	public int curarEquipo(List<Long> idsPokemon, Long idUsuarioPropietarioAutenticado) {
		boolean algunoCurado = false;
		
		for (Long idPokemon : idsPokemon) {
			Optional<Pokemon> pOpt = pokeRep.findById(idPokemon);

			if (pOpt.isPresent()) {
				Pokemon p = pOpt.get();

				if (p.getIdUsuarioPropietario().equals(idUsuarioPropietarioAutenticado)) {
					p.setSaludActual(p.getSaludMaxima());
					p.setEstado("OK");
					p.setEstadoAlterado(null);

					pokeRep.save(p);
					centroRep.save(new CentroPokemon(
							idUsuarioPropietarioAutenticado,
							idPokemon,
							LocalDateTime.now()
					));

					algunoCurado = true;
				} else {
					System.out.println("⚠️ Intento de curar un Pokémon ajeno. ID Pokémon: " + idPokemon);
				}
			}
		}
		
		return algunoCurado ? 0 : 1;
	}
}