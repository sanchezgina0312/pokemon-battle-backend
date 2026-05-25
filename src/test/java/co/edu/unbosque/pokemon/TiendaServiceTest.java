package co.edu.unbosque.pokemon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import co.edu.unbosque.pokemon.entity.Inventario;
import co.edu.unbosque.pokemon.entity.Item;
import co.edu.unbosque.pokemon.entity.Tienda;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.repository.InventarioRepository;
import co.edu.unbosque.pokemon.repository.ItemRepository;
import co.edu.unbosque.pokemon.repository.TiendaRepository;
import co.edu.unbosque.pokemon.repository.UsuarioRepository;
import co.edu.unbosque.pokemon.service.TiendaService;

/**
 * Clase de pruebas unitarias para {@link TiendaService}.
 * 
 * <p>
 * Esta clase valida el correcto funcionamiento de los métodos
 * del servicio de tienda utilizando Mockito para simular
 * los repositorios y evitar acceso a base de datos real.
 * </p>
 * 
 * <p>
 * Se prueban escenarios de compra exitosa,
 * usuario o ítem inexistente, dinero insuficiente
 * y actualización del inventario.
 * </p>
 */
class TiendaServiceTest {

	/**
	 * Mock del repositorio de usuarios.
	 */
	@Mock
	private UsuarioRepository userRep;

	/**
	 * Mock del repositorio de ítems.
	 */
	@Mock
	private ItemRepository itemRep;

	/**
	 * Mock del repositorio de tienda.
	 */
	@Mock
	private TiendaRepository tiendaRep;

	/**
	 * Mock del repositorio de inventario.
	 */
	@Mock
	private InventarioRepository invRep;

	/**
	 * Servicio que será probado.
	 */
	private TiendaService tiendaService;

	/**
	 * Usuario de prueba.
	 */
	private Usuario user;

	/**
	 * Ítem de prueba.
	 */
	private Item item;

	/**
	 * Inicializa el entorno de pruebas antes de cada test.
	 */
	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		tiendaService = new TiendaService();

		user = new Usuario();
		user.setId(1L);
		user.setDinero(5000);

		item = new Item();
		item.setId(1L);
		item.setNombre("ULTRABOLA");
		item.setCosto(1000);

		try {

			var campoUserRep = TiendaService.class.getDeclaredField("userRep");
			campoUserRep.setAccessible(true);
			campoUserRep.set(tiendaService, userRep);

			var campoItemRep = TiendaService.class.getDeclaredField("itemRep");
			campoItemRep.setAccessible(true);
			campoItemRep.set(tiendaService, itemRep);

			var campoTiendaRep = TiendaService.class.getDeclaredField("tiendaRep");
			campoTiendaRep.setAccessible(true);
			campoTiendaRep.set(tiendaService, tiendaRep);

			var campoInvRep = TiendaService.class.getDeclaredField("invRep");
			campoInvRep.setAccessible(true);
			campoInvRep.set(tiendaService, invRep);

		} catch (Exception e) {
			fail("Error configurando mocks: " + e.getMessage());
		}
	}

	/**
	 * Prueba una compra realizada exitosamente.
	 */
	@Test
	void testRealizarCompraSuccess() {

		when(userRep.findById(1L))
				.thenReturn(Optional.of(user));

		when(itemRep.findById(1L))
				.thenReturn(Optional.of(item));

		when(invRep.findByIdUsuario(1L))
				.thenReturn(Optional.of(new ArrayList<>()));

		int result = tiendaService.realizarCompra(1L, 1L);

		assertEquals(0, result);

		assertEquals(3800, user.getDinero());

		verify(userRep).save(any(Usuario.class));

		verify(tiendaRep).save(any(Tienda.class));

		verify(invRep).save(any(Inventario.class));
	}

	/**
	 * Prueba la compra cuando el usuario no existe.
	 */
	@Test
	void testRealizarCompraUsuarioNoExiste() {

		when(userRep.findById(1L))
				.thenReturn(Optional.empty());

		when(itemRep.findById(1L))
				.thenReturn(Optional.of(item));

		int result = tiendaService.realizarCompra(1L, 1L);

		assertEquals(1, result);

		verify(userRep, never()).save(any());

		verify(tiendaRep, never()).save(any());

		verify(invRep, never()).save(any());
	}

	/**
	 * Prueba la compra cuando el ítem no existe.
	 */
	@Test
	void testRealizarCompraItemNoExiste() {

		when(userRep.findById(1L))
				.thenReturn(Optional.of(user));

		when(itemRep.findById(1L))
				.thenReturn(Optional.empty());

		int result = tiendaService.realizarCompra(1L, 1L);

		assertEquals(1, result);

		verify(userRep, never()).save(any());

		verify(tiendaRep, never()).save(any());

		verify(invRep, never()).save(any());
	}

	/**
	 * Prueba la compra cuando el usuario no tiene suficiente dinero.
	 */
	@Test
	void testRealizarCompraDineroInsuficiente() {

		user.setDinero(100);

		when(userRep.findById(1L))
				.thenReturn(Optional.of(user));

		when(itemRep.findById(1L))
				.thenReturn(Optional.of(item));

		int result = tiendaService.realizarCompra(1L, 1L);

		assertEquals(2, result);

		verify(userRep, never()).save(any());

		verify(tiendaRep, never()).save(any());

		verify(invRep, never()).save(any());
	}

	/**
	 * Prueba la actualización del inventario
	 * cuando el ítem ya existe en la mochila.
	 */
	@Test
	void testActualizarMochilaItemExistente() {

		Inventario inventario = new Inventario(1L, 1L, 2);

		List<Inventario> lista = List.of(inventario);

		when(userRep.findById(1L))
				.thenReturn(Optional.of(user));

		when(itemRep.findById(1L))
				.thenReturn(Optional.of(item));

		when(invRep.findByIdUsuario(1L))
				.thenReturn(Optional.of(lista));

		int result = tiendaService.realizarCompra(1L, 1L);

		assertEquals(0, result);

		assertEquals(3, inventario.getCantidad());

		verify(invRep).save(inventario);
	}
}