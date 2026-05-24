package co.edu.unbosque.pokemon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.unbosque.pokemon.dto.PokemonDTO;
import co.edu.unbosque.pokemon.entity.Pokemon;
import co.edu.unbosque.pokemon.repository.PokemonRepository;
import co.edu.unbosque.pokemon.service.PokemonService;

/**
 * Clase de pruebas unitarias para {@link PokemonService}.
 * 
 * <p>
 * Esta clase valida el correcto funcionamiento
 * de los métodos CRUD y búsquedas del servicio Pokémon.
 * </p>
 */
class PokemonServiceTest {

	/**
	 * Mock del repositorio Pokémon.
	 */
	@Mock
	private PokemonRepository pokemonRep;

	/**
	 * Mapper para conversión DTO-entidad.
	 */
	private ModelMapper mapper;

	/**
	 * Servicio probado.
	 */
	private PokemonService pokemonService;

	/**
	 * DTO de prueba.
	 */
	private PokemonDTO dto;

	/**
	 * Entidad de prueba.
	 */
	private Pokemon entity;

	/**
	 * Inicializa entorno de pruebas.
	 */
	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		pokemonService = new PokemonService();

		pokemonService.setPokemonRep(pokemonRep);
		pokemonService.setMapper(mapper);

		dto = new PokemonDTO();
		dto.setApodo("Pikachu");
		dto.setIdUsuarioPropietario(1L);
		dto.setNivel(5);
		dto.setExperienciaAcumulada(50);
		dto.setSaludActual(80);
		dto.setSaludMaxima(100);
		dto.setEstado("OK");
		dto.setPokeApiId(25);

		entity = new Pokemon();
		entity.setId(1L);
		entity.setApodo("Pikachu");
		entity.setIdUsuarioPropietario(1L);
		entity.setNivel(5);
		entity.setExperienciaAcumulada(50);
		entity.setSaludActual(80);
		entity.setSaludMaxima(100);
		entity.setEstado("OK");
		entity.setPokeApiId(25);
	}

	/**
	 * Prueba creación exitosa.
	 */
	@Test
	void testCreate() {

		when(pokemonRep.save(any(Pokemon.class)))
				.thenReturn(entity);

		int result = pokemonService.create(dto);

		assertEquals(0, result);

		verify(pokemonRep).save(any(Pokemon.class));
	}

	/**
	 * Prueba obtención de todos los Pokémon.
	 */
	@Test
	void testGetAll() {

		when(pokemonRep.findAll())
				.thenReturn(List.of(entity));

		List<PokemonDTO> result =
				pokemonService.getAll();

		assertNotNull(result);

		assertEquals(1, result.size());
	}

	/**
	 * Prueba eliminación exitosa.
	 */
	@Test
	void testDeleteByIdSuccess() {

		when(pokemonRep.findById(1L))
				.thenReturn(Optional.of(entity));

		int result =
				pokemonService.deleteById(1L);

		assertEquals(0, result);

		verify(pokemonRep).delete(entity);
	}

	/**
	 * Prueba eliminación fallida.
	 */
	@Test
	void testDeleteByIdNotFound() {

		when(pokemonRep.findById(1L))
				.thenReturn(Optional.empty());

		int result =
				pokemonService.deleteById(1L);

		assertEquals(1, result);
	}

	/**
	 * Prueba actualización exitosa.
	 */
	@Test
	void testUpdateByIdSuccess() {

		when(pokemonRep.findById(1L))
				.thenReturn(Optional.of(entity));

		when(pokemonRep.save(any(Pokemon.class)))
				.thenReturn(entity);

		int result =
				pokemonService.updateById(1L, dto);

		assertEquals(0, result);

		verify(pokemonRep).save(any(Pokemon.class));
	}

	/**
	 * Prueba actualización fallida.
	 */
	@Test
	void testUpdateByIdNotFound() {

		when(pokemonRep.findById(1L))
				.thenReturn(Optional.empty());

		int result =
				pokemonService.updateById(1L, dto);

		assertEquals(1, result);
	}

	/**
	 * Prueba búsqueda por apodo.
	 */
	@Test
	void testFindByApodo() {

		when(pokemonRep.findByApodo("Pikachu"))
				.thenReturn(Optional.of(List.of(entity)));

		List<PokemonDTO> result =
				pokemonService.findByApodo("Pikachu");

		assertNotNull(result);

		assertEquals(1, result.size());
	}

	/**
	 * Prueba búsqueda por propietario.
	 */
	@Test
	void testFindByPropietario() {

		when(pokemonRep.findByIdUsuarioPropietario(1L))
				.thenReturn(Optional.of(List.of(entity)));

		List<PokemonDTO> result =
				pokemonService.findByPropietario(1L);

		assertNotNull(result);

		assertEquals(1, result.size());
	}

	/**
	 * Prueba obtención por pokeApiId.
	 */
	@Test
	void testObtenerEspeciePorPokeApiId() {

		when(pokemonRep.findFirstByPokeApiId(25))
				.thenReturn(Optional.of(entity));

		PokemonDTO result =
				pokemonService
				.obtenerEspeciePorPokeApiId(25);

		assertNotNull(result);

		assertEquals(25, result.getPokeApiId());
	}

	/**
	 * Prueba suma de experiencia.
	 */
	@Test
	void testSumarExperiencia() {

		entity.setExperienciaAcumulada(50);

		when(pokemonRep.findById(1L))
				.thenReturn(Optional.of(entity));

		pokemonService.sumarExperiencia(1L, 30);

		verify(pokemonRep).save(any(Pokemon.class));
	}

	/**
	 * Prueba subida de nivel.
	 */
	@Test
	void testSumarExperienciaSubeNivel() {

		entity.setExperienciaAcumulada(90);
		entity.setNivel(5);
		entity.setAtaque(20);
		entity.setDefensa(20);

		when(pokemonRep.findById(1L))
				.thenReturn(Optional.of(entity));

		pokemonService.sumarExperiencia(1L, 20);

		assertEquals(6, entity.getNivel());

		verify(pokemonRep).save(any(Pokemon.class));
	}

	/**
	 * Prueba conteo.
	 */
	@Test
	void testCount() {

		when(pokemonRep.count()).thenReturn(5L);

		assertEquals(5,
				pokemonService.count());
	}

	/**
	 * Prueba existencia.
	 */
	@Test
	void testExist() {

		when(pokemonRep.existsById(1L))
				.thenReturn(true);

		assertTrue(
				pokemonService.exist(1L));
	}
}