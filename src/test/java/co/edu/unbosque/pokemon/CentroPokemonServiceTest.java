package co.edu.unbosque.pokemon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.unbosque.pokemon.entity.CentroPokemon;
import co.edu.unbosque.pokemon.entity.Pokemon;
import co.edu.unbosque.pokemon.repository.CentroPokemonRepository;
import co.edu.unbosque.pokemon.repository.PokemonRepository;
import co.edu.unbosque.pokemon.service.CentroPokemonService;

/**
 * Clase de pruebas unitarias para {@link CentroPokemonService}.
 * 
 * <p>
 * Esta clase valida el correcto funcionamiento de los métodos
 * del servicio del centro Pokémon utilizando Mockito para
 * simular los repositorios y evitar acceso a base de datos real.
 * </p>
 * 
 * <p>
 * Se prueban escenarios de curación individual y curación
 * de equipos completos.
 * </p>
 */
class CentroPokemonServiceTest {

	/**
	 * Mock del repositorio de Pokémon.
	 */
	@Mock
	private PokemonRepository pokeRep;

	/**
	 * Mock del repositorio de centro Pokémon.
	 */
	@Mock
	private CentroPokemonRepository centroRep;

	/**
	 * Servicio que será probado.
	 */
	private CentroPokemonService centroPokemonService;

	/**
	 * Entidad de prueba.
	 */
	private Pokemon pokemon;

	/**
	 * Inicializa el entorno de pruebas antes de cada test.
	 */
	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		centroPokemonService = new CentroPokemonService();

		pokemon = new Pokemon();
		pokemon.setId(1L);
		pokemon.setSaludActual(20);
		pokemon.setSaludMaxima(100);
		pokemon.setEstado("DEBIL");
		pokemon.setEstadoAlterado("QUEMADO");
		pokemon.setIdUsuarioPropietario(10L);

		try {

			Field pokeField = CentroPokemonService.class
					.getDeclaredField("pokeRep");

			pokeField.setAccessible(true);

			pokeField.set(centroPokemonService, pokeRep);

			Field centroField = CentroPokemonService.class
					.getDeclaredField("centroRep");

			centroField.setAccessible(true);

			centroField.set(centroPokemonService, centroRep);

		} catch (Exception e) {

			fail("Error configurando mocks: " + e.getMessage());
		}
	}

	/**
	 * Prueba la curación exitosa de un Pokémon.
	 */
	@Test
	void testCurarSuccess() {

		when(pokeRep.findById(1L))
				.thenReturn(Optional.of(pokemon));

		when(pokeRep.save(any(Pokemon.class)))
				.thenReturn(pokemon);

		int result = centroPokemonService.curar(1L);

		assertEquals(0, result);

		assertEquals(100, pokemon.getSaludActual());

		verify(pokeRep).save(any(Pokemon.class));

		verify(centroRep).save(any(CentroPokemon.class));
	}

	/**
	 * Prueba la curación cuando el Pokémon no existe.
	 */
	@Test
	void testCurarNotFound() {

		when(pokeRep.findById(1L))
				.thenReturn(Optional.empty());

		int result = centroPokemonService.curar(1L);

		assertEquals(1, result);

		verify(pokeRep, never()).save(any());

		verify(centroRep, never()).save(any());
	}

	/**
	 * Prueba la curación exitosa de un equipo.
	 */
	@Test
	void testCurarEquipoSuccess() {

		when(pokeRep.findById(1L))
				.thenReturn(Optional.of(pokemon));

		when(pokeRep.save(any(Pokemon.class)))
				.thenReturn(pokemon);

		int result = centroPokemonService
				.curarEquipo(List.of(1L), 10L);

		assertEquals(0, result);

		assertEquals(100, pokemon.getSaludActual());

		assertEquals("OK", pokemon.getEstado());

		assertNull(pokemon.getEstadoAlterado());

		verify(pokeRep).save(any(Pokemon.class));

		verify(centroRep).save(any(CentroPokemon.class));
	}

	/**
	 * Prueba la curación de un equipo cuando el Pokémon
	 * pertenece a otro usuario.
	 */
	@Test
	void testCurarEquipoPokemonAjeno() {

		when(pokeRep.findById(1L))
				.thenReturn(Optional.of(pokemon));

		int result = centroPokemonService
				.curarEquipo(List.of(1L), 99L);

		assertEquals(1, result);

		verify(pokeRep, never()).save(any());

		verify(centroRep, never()).save(any());
	}

	/**
	 * Prueba la curación de un equipo cuando el Pokémon
	 * no existe.
	 */
	@Test
	void testCurarEquipoNotFound() {

		when(pokeRep.findById(1L))
				.thenReturn(Optional.empty());

		int result = centroPokemonService
				.curarEquipo(List.of(1L), 10L);

		assertEquals(1, result);

		verify(pokeRep, never()).save(any());

		verify(centroRep, never()).save(any());
	}
}