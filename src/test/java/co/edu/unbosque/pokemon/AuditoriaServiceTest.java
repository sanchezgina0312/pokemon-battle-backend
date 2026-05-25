package co.edu.unbosque.pokemon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import co.edu.unbosque.pokemon.entity.Auditoria;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.repository.AuditoriaRepository;
import co.edu.unbosque.pokemon.service.AuditoriaService;

/**
 * Clase de pruebas unitarias para {@link AuditoriaService}.
 * 
 * <p>
 * Esta clase valida el correcto funcionamiento de los métodos
 * del servicio de auditoría utilizando Mockito para simular
 * el repositorio y evitar acceso a base de datos real.
 * </p>
 */
class AuditoriaServiceTest {

	/**
	 * Mock del repositorio de auditoría.
	 */
	@Mock
	private AuditoriaRepository auditoriaRepository;

	/**
	 * Servicio que será probado.
	 */
	private AuditoriaService auditoriaService;

	/**
	 * Entidad de prueba.
	 */
	private Auditoria auditoria;

	/**
	 * Inicializa el entorno de pruebas antes de cada test.
	 */
	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		auditoriaService = new AuditoriaService();

		auditoria = new Auditoria(
				1L,
				"usuario@mail.com",
				"ROLE_USER",
				"CREAR",
				"Pokemon",
				LocalDateTime.now());

		try {

			Field repoField = AuditoriaService.class
					.getDeclaredField("auditoriaRepository");

			repoField.setAccessible(true);

			repoField.set(auditoriaService, auditoriaRepository);

		} catch (Exception e) {

			fail("Error configurando mocks: " + e.getMessage());
		}
	}

	/**
	 * Prueba el registro de una acción autenticada.
	 */
	@Test
	void testRegistrar() {

		Usuario usuario = new Usuario();
		usuario.setId(1L);

		Authentication auth = mock(Authentication.class);

		when(auth.isAuthenticated()).thenReturn(true);

		when(auth.getName()).thenReturn("usuario@mail.com");

		List<GrantedAuthority> authorities = List.of(
				new SimpleGrantedAuthority("ROLE_USER"));

		when(auth.getAuthorities()).thenAnswer(invocation -> authorities);

		when(auth.getPrincipal()).thenReturn(usuario);

		SecurityContextHolder.getContext().setAuthentication(auth);

		auditoriaService.registrar("CREAR", "Pokemon");

		verify(auditoriaRepository).save(any(Auditoria.class));
	}

	/**
	 * Prueba el registro cuando el usuario es anónimo.
	 */
	@Test
	void testRegistrarAnonimo() {

		Authentication auth = mock(AnonymousAuthenticationToken.class);

		SecurityContextHolder.getContext().setAuthentication(auth);

		auditoriaService.registrar("ELIMINAR", "Pokemon");

		verify(auditoriaRepository).save(any(Auditoria.class));
	}

	/**
	 * Prueba la obtención de todas las auditorías.
	 */
	@Test
	void testGetAll() {

		when(auditoriaRepository.findAll())
				.thenReturn(List.of(auditoria));

		List<Auditoria> result = auditoriaService.getAll();

		assertNotNull(result);

		assertEquals(1, result.size());
	}

	/**
	 * Prueba el registro de login.
	 */
	@Test
	void testRegistrarLogin() {

		auditoriaService.registrarLogin(
				"usuario@mail.com",
				"ROLE_USER",
				1L);

		verify(auditoriaRepository).save(any(Auditoria.class));
	}

	/**
	 * Prueba el registro de una acción pública.
	 */
	@Test
	void testRegistrarAccionPublica() {

		auditoriaService.registrarAccionPublica(
				"anonimo@mail.com",
				"CONSULTAR",
				"Pokemon");

		verify(auditoriaRepository).save(any(Auditoria.class));
	}
}