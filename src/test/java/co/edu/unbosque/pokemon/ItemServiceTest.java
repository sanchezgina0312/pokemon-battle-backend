package co.edu.unbosque.pokemon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.unbosque.pokemon.dto.ItemDTO;
import co.edu.unbosque.pokemon.entity.Item;
import co.edu.unbosque.pokemon.repository.ItemRepository;
import co.edu.unbosque.pokemon.service.ItemService;

/**
 * Clase de pruebas unitarias para {@link ItemService}.
 * 
 * <p>
 * Esta clase valida el correcto funcionamiento del método
 * del servicio de ítems utilizando Mockito para simular
 * el repositorio y evitar acceso a base de datos real.
 * </p>
 * 
 * <p>
 * Se prueban escenarios de listado de ítems.
 * </p>
 */
class ItemServiceTest {

	/**
	 * Mock del repositorio de ítems.
	 */
	@Mock
	private ItemRepository itemRep;

	/**
	 * Mapper para conversión entre DTO y entidad.
	 */
	private ModelMapper mapper;

	/**
	 * Servicio que será probado.
	 */
	private ItemService itemService;

	/**
	 * DTO de prueba.
	 */
	private ItemDTO dto;

	/**
	 * Entidad de prueba.
	 */
	private Item entity;

	/**
	 * Inicializa el entorno de pruebas antes de cada test.
	 */
	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		itemService = new ItemService();

		dto = new ItemDTO();
		dto.setNombre("Poción");

		entity = new Item();
		entity.setId(1L);
		entity.setNombre("Poción");

		try {

			Field repoField = ItemService.class
					.getDeclaredField("itemRep");

			repoField.setAccessible(true);

			repoField.set(itemService, itemRep);

			Field mapperField = ItemService.class
					.getDeclaredField("mapper");

			mapperField.setAccessible(true);

			mapperField.set(itemService, mapper);

		} catch (Exception e) {

			fail("Error configurando mocks: " + e.getMessage());
		}
	}

	/**
	 * Prueba el listado de todos los ítems.
	 */
	@Test
	void testListarTodos() {

		when(itemRep.findAll())
				.thenReturn(List.of(entity));

		List<ItemDTO> result =
				itemService.listarTodos();

		assertNotNull(result);

		assertEquals(1, result.size());

		assertEquals("Poción",
				result.get(0).getNombre());
	}

	/**
	 * Prueba el listado cuando no existen ítems.
	 */
	@Test
	void testListarTodosEmpty() {

		when(itemRep.findAll())
				.thenReturn(List.of());

		List<ItemDTO> result =
				itemService.listarTodos();

		assertNotNull(result);

		assertTrue(result.isEmpty());
	}
}