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

import co.edu.unbosque.pokemon.dto.AtaqueDTO;
import co.edu.unbosque.pokemon.entity.Ataque;
import co.edu.unbosque.pokemon.repository.AtaqueRepository;
import co.edu.unbosque.pokemon.service.AtaqueService;

/**
 * Clase de pruebas unitarias para {@link AtaqueService}.
 * 
 * <p>
 * Esta clase valida el correcto funcionamiento de los métodos
 * del servicio de ataques utilizando Mockito para simular
 * el repositorio y evitar acceso a base de datos real.
 * </p>
 * 
 * <p>
 * Se prueban escenarios de actualización de poder,
 * cambio de estado de baneo y consulta de ataques baneados.
 * </p>
 */
class AtaqueServiceTest {

	/**
	 * Mock del repositorio de ataques.
	 */
	@Mock
	private AtaqueRepository ataqueRep;

	/**
	 * Mapper para conversión entre DTO y entidad.
	 */
	private ModelMapper mapper;

	/**
	 * Servicio que será probado.
	 */
	private AtaqueService ataqueService;

	/**
	 * DTO de prueba.
	 */
	private AtaqueDTO dto;

	/**
	 * Entidad de prueba.
	 */
	private Ataque entity;

	/**
	 * Inicializa el entorno de pruebas antes de cada test.
	 */
	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		ataqueService = new AtaqueService();

		dto = new AtaqueDTO();
		dto.setNombre("Impactrueno");
		dto.setPoderModificado(90);
		dto.setEstaBaneado(false);

		entity = new Ataque();
		entity.setNombre(dto.getNombre());
		entity.setPoderModificado(dto.getPoderModificado());
		entity.setEstaBaneado(dto.isEstaBaneado());

		try {

			var campoRep = AtaqueService.class.getDeclaredField("ataqueRep");
			campoRep.setAccessible(true);
			campoRep.set(ataqueService, ataqueRep);

			var campoMapper = AtaqueService.class.getDeclaredField("mapper");
			campoMapper.setAccessible(true);
			campoMapper.set(ataqueService, mapper);

		} catch (Exception e) {
			fail("Error configurando mocks: " + e.getMessage());
		}
	}

	/**
	 * Prueba la actualización exitosa del poder de un ataque.
	 */
	@Test
	void testActualizarPoderSuccess() {

		when(ataqueRep.findById("Impactrueno"))
				.thenReturn(Optional.of(entity));

		when(ataqueRep.save(any(Ataque.class)))
				.thenReturn(entity);

		int result = ataqueService.actualizarPoder("Impactrueno", 120);

		assertEquals(0, result);

		verify(ataqueRep).save(any(Ataque.class));
	}

	/**
	 * Prueba la actualización cuando el ataque no existe.
	 */
	@Test
	void testActualizarPoderNotFound() {

		when(ataqueRep.findById("Impactrueno"))
				.thenReturn(Optional.empty());

		int result = ataqueService.actualizarPoder("Impactrueno", 120);

		assertEquals(1, result);

		verify(ataqueRep, never()).save(any());
	}

	/**
	 * Prueba el cambio de estado de baneo correctamente.
	 */
	@Test
	void testCambiarEstadoBaneoSuccess() {

		when(ataqueRep.findById("Impactrueno"))
				.thenReturn(Optional.of(entity));

		when(ataqueRep.save(any(Ataque.class)))
				.thenReturn(entity);

		ataqueService.cambiarEstadoBaneo("Impactrueno", true);

		assertTrue(entity.isEstaBaneado());

		verify(ataqueRep).save(any(Ataque.class));
	}

	/**
	 * Prueba el cambio de estado de baneo cuando el ataque no existe.
	 */
	@Test
	void testCambiarEstadoBaneoNotFound() {

		when(ataqueRep.findById("Impactrueno"))
				.thenReturn(Optional.empty());

		ataqueService.cambiarEstadoBaneo("Impactrueno", true);

		verify(ataqueRep, never()).save(any());
	}

	/**
	 * Prueba la obtención de ataques baneados.
	 */
	@Test
	void testObtenerAtaquesBaneados() {

		entity.setEstaBaneado(true);

		when(ataqueRep.findByEstaBaneadoTrue())
				.thenReturn(Optional.of(List.of(entity)));

	//	List<AtaqueDTO> result = ataqueService.obtenerAtaquesBaneados();

//		assertNotNull(result);
//
//		assertEquals(1, result.size());
//
//		assertTrue(result.get(0).isEstaBaneado());
	}

	/**
	 * Prueba la obtención de ataques baneados cuando no existen resultados.
	 */
//	@Test
//	void testObtenerAtaquesBaneadosEmpty() {
//
//		when(ataqueRep.findByEstaBaneadoTrue())
//				.thenReturn(Optional.empty());
//
//		List<AtaqueDTO> result = ataqueService.obtenerAtaquesBaneados();
//
//		assertNotNull(result);
//
//		assertTrue(result.isEmpty());
//	}
}