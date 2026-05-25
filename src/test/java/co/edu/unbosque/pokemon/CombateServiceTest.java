package co.edu.unbosque.pokemon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.unbosque.pokemon.dto.CombateDTO;
import co.edu.unbosque.pokemon.entity.Combate;
import co.edu.unbosque.pokemon.repository.CombateRepository;
import co.edu.unbosque.pokemon.service.CombateService;

/**
 * Clase de pruebas unitarias para {@link CombateService}.
 * 
 * <p>
 * Esta clase valida el correcto funcionamiento de los métodos
 * del servicio de combate utilizando Mockito para simular
 * el repositorio y evitar acceso a base de datos real.
 * </p>
 * 
 * <p>
 * Se prueban escenarios de cálculo de daño,
 * creación de registros, historial de peleas,
 * recompensas y cálculo de niveles.
 * </p>
 */
class CombateServiceTest {

	/**
	 * Mock del repositorio de combate.
	 */
	@Mock
	private CombateRepository combateRep;

	/**
	 * Mapper para conversión entre DTO y entidad.
	 */
	private ModelMapper mapper;

	/**
	 * Servicio que será probado.
	 */
	private CombateService combateService;

	/**
	 * DTO de prueba.
	 */
	private CombateDTO dto;

	/**
	 * Entidad de prueba.
	 */
	private Combate entity;

	/**
	 * Inicializa el entorno de pruebas antes de cada test.
	 */
	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		combateService = new CombateService();

		dto = new CombateDTO();
		dto.setIdUsuarioJugador(1L);

		entity = new Combate();
		entity.setId(1L);
		entity.setIdUsuarioJugador(1L);

		try {

			Field repoField = CombateService.class
					.getDeclaredField("combateRep");

			repoField.setAccessible(true);

			repoField.set(combateService, combateRep);

			Field mapperField = CombateService.class
					.getDeclaredField("mapper");

			mapperField.setAccessible(true);

			mapperField.set(combateService, mapper);

		} catch (Exception e) {

			fail("Error configurando mocks: " + e.getMessage());
		}
	}

	/**
	 * Prueba el cálculo de daño básico.
	 */
	@Test
	void testCalcularDanio() {

		int daño = combateService.calcularDanio(
				50,
				100,
				80,
				"FIRE",
				"GRASS");

		assertTrue(daño > 0);
	}

	/**
	 * Prueba la creación exitosa de un registro de combate.
	 */
	@Test
	void testCrearRegistro() {

		when(combateRep.save(any(Combate.class)))
				.thenReturn(entity);

		CombateDTO result = combateService.crearRegistro(dto);

		assertNotNull(result);

		assertEquals(1L, result.getIdUsuarioJugador());

		verify(combateRep).save(any(Combate.class));
	}

	/**
	 * Prueba la obtención del historial de peleas.
	 */
	@Test
	void testHistorialPeleas() {

		when(combateRep.findByIdUsuarioJugador(1L))
				.thenReturn(Optional.of(List.of(entity)));

		List<CombateDTO> result =
				combateService.historialPeleas(1L);

		assertNotNull(result);

		assertEquals(1, result.size());
	}

	/**
	 * Prueba el historial de peleas vacío.
	 */
	@Test
	void testHistorialPeleasEmpty() {

		when(combateRep.findByIdUsuarioJugador(1L))
				.thenReturn(Optional.empty());

		List<CombateDTO> result =
				combateService.historialPeleas(1L);

		assertNotNull(result);

		assertTrue(result.isEmpty());
	}

	/**
	 * Prueba el cálculo de recompensas.
	 */
	@Test
	void testCalcularRecompensas() {

		Map<String, Object> recompensas =
				combateService.calcularRecompensas(10);

		assertNotNull(recompensas);

		assertTrue(recompensas.containsKey("exp"));

		assertTrue(recompensas.containsKey("dinero"));
	}

	/**
	 * Prueba el cálculo de un nuevo nivel.
	 */
	@Test
	void testCalcularNuevoNivel() {

		int nuevoNivel =
				combateService.calcularNuevoNivel(
						1,
						50,
						100);

		assertTrue(nuevoNivel >= 1);
	}
}