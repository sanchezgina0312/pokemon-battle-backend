package co.edu.unbosque.pokemon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.unbosque.pokemon.dto.InventarioDTO;
import co.edu.unbosque.pokemon.entity.Inventario;
import co.edu.unbosque.pokemon.repository.InventarioRepository;
import co.edu.unbosque.pokemon.service.InventarioService;

/**
 * Clase de pruebas unitarias para {@link InventarioService}.
 * 
 * <p>
 * Esta clase valida el correcto funcionamiento del método
 * del servicio de inventario utilizando Mockito para simular
 * el repositorio y evitar acceso a base de datos real.
 * </p>
 * 
 * <p>
 * Se prueban escenarios de consulta de mochila.
 * </p>
 */
class InventarioServiceTest {

	/**
	 * Mock del repositorio de inventario.
	 */
	@Mock
	private InventarioRepository invRep;

	/**
	 * Mapper para conversión entre DTO y entidad.
	 */
	private ModelMapper mapper;

	/**
	 * Servicio que será probado.
	 */
	private InventarioService inventarioService;

	/**
	 * DTO de prueba.
	 */
	private InventarioDTO dto;

	/**
	 * Entidad de prueba.
	 */
	private Inventario entity;

	/**
	 * Inicializa el entorno de pruebas antes de cada test.
	 */
	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		inventarioService = new InventarioService();

		dto = new InventarioDTO();
		dto.setIdUsuario(1L);

		entity = new Inventario();
		entity.setId(1L);
		entity.setIdUsuario(1L);

		try {

			Field repoField = InventarioService.class
					.getDeclaredField("invRep");

			repoField.setAccessible(true);

			repoField.set(inventarioService, invRep);

			Field mapperField = InventarioService.class
					.getDeclaredField("mapper");

			mapperField.setAccessible(true);

			mapperField.set(inventarioService, mapper);

		} catch (Exception e) {

			fail("Error configurando mocks: " + e.getMessage());
		}
	}

	/**
	 * Prueba la consulta de mochila correctamente.
	 */
	@Test
	void testVerMochila() {

		when(invRep.findByIdUsuario(1L))
				.thenReturn(Optional.of(List.of(entity)));

		List<InventarioDTO> result =
				inventarioService.verMochila(1L);

		assertNotNull(result);

		assertEquals(1, result.size());
	}

	/**
	 * Prueba la consulta de mochila cuando no existen registros.
	 */
	@Test
	void testVerMochilaEmpty() {

		when(invRep.findByIdUsuario(1L))
				.thenReturn(Optional.empty());

		List<InventarioDTO> result =
				inventarioService.verMochila(1L);

		assertNotNull(result);

		assertTrue(result.isEmpty());
	}
}