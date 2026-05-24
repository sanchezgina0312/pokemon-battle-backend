package co.edu.unbosque.pokemon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.unbosque.pokemon.dto.CapturaDTO;
import co.edu.unbosque.pokemon.entity.Captura;
import co.edu.unbosque.pokemon.repository.CapturaRepository;
import co.edu.unbosque.pokemon.service.CapturaService;

/**
 * Clase de pruebas unitarias para {@link CapturaService}.
 * 
 * <p>
 * Esta clase valida el correcto funcionamiento de los métodos
 * del servicio de capturas utilizando Mockito para simular
 * el repositorio y evitar acceso a base de datos real.
 * </p>
 * 
 * <p>
 * Se prueban escenarios de registro y consulta de capturas.
 * </p>
 */
class CapturaServiceTest {

	/**
	 * Mock del repositorio de capturas.
	 */
	@Mock
	private CapturaRepository capturaRep;

	/**
	 * Mapper para conversión entre DTO y entidad.
	 */
	private ModelMapper mapper;

	/**
	 * Servicio que será probado.
	 */
	private CapturaService capturaService;

	/**
	 * DTO de prueba.
	 */
	private CapturaDTO dto;

	/**
	 * Entidad de prueba.
	 */
	private Captura entity;

	/**
	 * Inicializa el entorno de pruebas antes de cada test.
	 */
	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		capturaService = new CapturaService();

		dto = new CapturaDTO();
		dto.setIdUsuario(1L);
		dto.setNombrePokemon("Pikachu");

		entity = new Captura();
		entity.setId(1L);
		entity.setIdUsuario(dto.getIdUsuario());
		entity.setNombrePokemon(dto.getNombrePokemon());
		entity.setFechaCaptura(LocalDateTime.now());

		try {

			Field repoField = CapturaService.class
					.getDeclaredField("capturaRep");

			repoField.setAccessible(true);

			repoField.set(capturaService, capturaRep);

			Field mapperField = CapturaService.class
					.getDeclaredField("mapper");

			mapperField.setAccessible(true);

			mapperField.set(capturaService, mapper);

		} catch (Exception e) {

			fail("Error configurando mocks: " + e.getMessage());
		}
	}

	/**
	 * Prueba el registro exitoso de una captura.
	 */
	@Test
	void testRegistrar() {

		when(capturaRep.save(any(Captura.class)))
				.thenReturn(entity);

		CapturaDTO result = capturaService.registrar(dto);

		assertNotNull(result);

		assertEquals("Pikachu", result.getNombrePokemon());

		verify(capturaRep).save(any(Captura.class));
	}

	/**
	 * Prueba la obtención de capturas por usuario.
	 */
	@Test
	void testObtenerPorUsuario() {

		when(capturaRep.findByIdUsuario(1L))
				.thenReturn(Optional.of(List.of(entity)));

		List<CapturaDTO> result =
				capturaService.obtenerPorUsuario(1L);

		assertNotNull(result);

		assertEquals(1, result.size());

		assertEquals("Pikachu",
				result.get(0).getNombrePokemon());
	}

	/**
	 * Prueba la obtención de capturas cuando no existen registros.
	 */
	@Test
	void testObtenerPorUsuarioEmpty() {

		when(capturaRep.findByIdUsuario(1L))
				.thenReturn(Optional.empty());

		List<CapturaDTO> result =
				capturaService.obtenerPorUsuario(1L);

		assertNotNull(result);

		assertTrue(result.isEmpty());
	}
}