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
import org.springframework.security.crypto.password.PasswordEncoder;

import co.edu.unbosque.pokemon.dto.UsuarioDTO;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.repository.UsuarioRepository;
import co.edu.unbosque.pokemon.service.UsuarioService;

/**
 * Clase de pruebas unitarias para {@link UsuarioService}.
 *
 * <p>Valida el comportamiento del servicio de usuarios mediante el uso de mocks
 * para el repositorio y el codificador de contraseñas. Se prueban operaciones
 * CRUD, autenticación y métodos adicionales del servicio.</p>
 *
 * <p>El objetivo es asegurar que la lógica de negocio funcione correctamente
 * sin depender de la base de datos ni de componentes externos.</p>
 *
 * @author Equipo de desarrollo
 * @version 1.0
 */
class UsuarioServiceTest {

	@Mock
	private UsuarioRepository usuarioRep;

	@Mock
	private PasswordEncoder passwordEncoder;

	private ModelMapper mapper;
	private UsuarioService usuarioService;

	private UsuarioDTO dto;
	private Usuario entity;

	/**
	 * Configuración inicial antes de cada prueba.
	 * Se inicializan los mocks y el servicio a probar.
	 */
	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		usuarioService = new UsuarioService();
		usuarioService.setUsuarioRep(usuarioRep);
		usuarioService.setMapper(mapper);
		usuarioService.setPasswordEncoder(passwordEncoder);

		dto = new UsuarioDTO();
		dto.setNombre("Juan Perez");
		dto.setCorreo("juan@mail.com");
		dto.setContrasenia("Password1");
		dto.setIdiomaPreferido("ES");
		dto.setDinero(1000);

		entity = new Usuario();
		entity.setId(1L);
		entity.setNombre("Juan Perez");
		entity.setCorreo("juan@mail.com");
		entity.setContrasenia("encoded");
		entity.setIdiomaPreferido("ES");
		entity.setDinero(1000);
	}

	/**
	 * Prueba creación exitosa de un usuario.
	 * Se espera que el usuario se guarde correctamente.
	 */
	@Test
	void testCreateSuccess() {

		when(usuarioRep.existsByCorreo(dto.getCorreo())).thenReturn(false);
		when(usuarioRep.existsByNombre(dto.getNombre())).thenReturn(false);

		when(passwordEncoder.encode(dto.getContrasenia())).thenReturn("encoded");
		when(usuarioRep.save(any(Usuario.class))).thenReturn(entity);

		int result = usuarioService.create(dto);

		assertEquals(0, result);
		verify(usuarioRep).save(any(Usuario.class));
	}

	/**
	 * Prueba de creación fallida por nombre duplicado.
	 * Se espera que no se guarde el usuario.
	 */
	@Test
	void testCreateNombreDuplicado() {

		when(usuarioRep.existsByCorreo(dto.getCorreo())).thenReturn(false);
		when(usuarioRep.existsByNombre(dto.getNombre())).thenReturn(true);

		int result = usuarioService.create(dto);

		assertEquals(1, result);
		verify(usuarioRep, never()).save(any());
	}

	/**
	 * Prueba de obtención de todos los usuarios.
	 * Se espera que se retorne una lista con elementos.
	 */
	@Test
	void testGetAll() {

		when(usuarioRep.findAll()).thenReturn(List.of(entity));

		List<?> result = usuarioService.getAll();

		assertEquals(1, result.size());
	}

	/**
	 * Prueba de eliminación exitosa de usuario por ID.
	 */
	@Test
	void testDeleteSuccess() {

		when(usuarioRep.existsById(1L)).thenReturn(true);

		int result = usuarioService.deleteById(1L);

		assertEquals(0, result);
		verify(usuarioRep).deleteById(1L);
	}

	/**
	 * Prueba de eliminación cuando el usuario no existe.
	 */
	@Test
	void testDeleteNotFound() {

		when(usuarioRep.existsById(1L)).thenReturn(false);

		int result = usuarioService.deleteById(1L);

		assertEquals(1, result);
		verify(usuarioRep, never()).deleteById(any());
	}

	/**
	 * Prueba de actualización exitosa de usuario.
	 */
	@Test
	void testUpdateSuccess() {

		when(usuarioRep.findById(1L)).thenReturn(Optional.of(entity));
		when(usuarioRep.existsByNombre(dto.getNombre())).thenReturn(false);
		when(passwordEncoder.encode(anyString())).thenReturn("encoded");

		int result = usuarioService.updateById(1L, dto);

		assertEquals(0, result);
		verify(usuarioRep).save(any(Usuario.class));
	}

	/**
	 * Prueba de actualización cuando el usuario no existe.
	 */
	@Test
	void testUpdateNotFound() {

		when(usuarioRep.findById(1L)).thenReturn(Optional.empty());

		int result = usuarioService.updateById(1L, dto);

		assertEquals(1, result);
		verify(usuarioRep, never()).save(any());
	}

	/**
	 * Prueba de login exitoso.
	 */
	@Test
	void testLoginSuccess() {

		when(usuarioRep.findByCorreo(dto.getCorreo()))
				.thenReturn(Optional.of(entity));

		when(passwordEncoder.matches(anyString(), anyString()))
				.thenReturn(true);

		int result = usuarioService.login(dto.getCorreo(), "Password1");

		assertEquals(0, result);
	}

	/**
	 * Prueba de login con contraseña incorrecta.
	 */
	@Test
	void testLoginWrongPassword() {

		when(usuarioRep.findByCorreo(dto.getCorreo()))
				.thenReturn(Optional.of(entity));

		when(passwordEncoder.matches(anyString(), anyString()))
				.thenReturn(false);

		int result = usuarioService.login(dto.getCorreo(), "Password1");

		assertEquals(1, result);
	}

	/**
	 * Prueba de login cuando el usuario no existe.
	 */
	@Test
	void testLoginNotFound() {

		when(usuarioRep.findByCorreo(dto.getCorreo()))
				.thenReturn(Optional.empty());

		int result = usuarioService.login(dto.getCorreo(), "Password1");

		assertEquals(2, result);
	}

	/**
	 * Prueba de suma de dinero a un usuario.
	 */
	@Test
	void testSumarDinero() {

		when(usuarioRep.findById(1L)).thenReturn(Optional.of(entity));

		usuarioService.sumarDinero(1L, 500);

		verify(usuarioRep).save(any(Usuario.class));
	}

	/**
	 * Prueba de actualización de género de usuario.
	 */
	@Test
	void testActualizarGenero() {

		when(usuarioRep.findById(1L)).thenReturn(Optional.of(entity));

		int result = usuarioService.actualizarGenero(1L, "M");

		assertEquals(0, result);

		verify(usuarioRep).save(any(Usuario.class));
	}
}