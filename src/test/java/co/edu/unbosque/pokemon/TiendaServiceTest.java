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

import co.edu.unbosque.pokemon.entity.Inventario;
import co.edu.unbosque.pokemon.entity.Item;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.repository.InventarioRepository;
import co.edu.unbosque.pokemon.repository.ItemRepository;
import co.edu.unbosque.pokemon.repository.TiendaRepository;
import co.edu.unbosque.pokemon.repository.UsuarioRepository;
import co.edu.unbosque.pokemon.service.TiendaService;

/**
 * Clase de pruebas unitarias para {@link TiendaService}.
 * 
 * Valida el correcto funcionamiento del proceso de compra en la tienda,
 * incluyendo escenarios de éxito, errores por usuario o item inexistente,
 * dinero insuficiente y creación/actualización del inventario.
 */
class TiendaServiceTest {

	@Mock
	private UsuarioRepository userRep;

	@Mock
	private ItemRepository itemRep;

	@Mock
	private TiendaRepository tiendaRep;

	@Mock
	private InventarioRepository invRep;

	private ModelMapper mapper;

	private TiendaService tiendaService;

	private Usuario user;

	private Item item;

	private Inventario inventario;

	/**
	 * Configura los mocks y la inyección manual de dependencias
	 * en {@link TiendaService} antes de cada prueba.
	 */
	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		mapper = new ModelMapper();

		tiendaService = new TiendaService();

		try {

			Field f1 = TiendaService.class.getDeclaredField("userRep");
			f1.setAccessible(true);
			f1.set(tiendaService, userRep);

			Field f2 = TiendaService.class.getDeclaredField("itemRep");
			f2.setAccessible(true);
			f2.set(tiendaService, itemRep);

			Field f3 = TiendaService.class.getDeclaredField("tiendaRep");
			f3.setAccessible(true);
			f3.set(tiendaService, tiendaRep);

			Field f4 = TiendaService.class.getDeclaredField("invRep");
			f4.setAccessible(true);
			f4.set(tiendaService, invRep);

			Field f5 = TiendaService.class.getDeclaredField("mapper");
			f5.setAccessible(true);
			f5.set(tiendaService, mapper);

		} catch (Exception e) {
			fail("Error configurando mocks: " + e.getMessage());
		}

		user = new Usuario();
		user.setId(1L);
		user.setDinero(2000);

		item = new Item();
		item.setId(1L);
		item.setNombre("POKE BALL");
		item.setCosto(200);

		inventario = new Inventario();
		inventario.setIdUsuario(1L);
		inventario.setIdItem(1L);
		inventario.setCantidad(1);
	}

	/**
	 * Verifica una compra exitosa:
	 * usuario y item existen, hay dinero suficiente y se actualiza inventario.
	 */
	@Test
	void testCompraExitosa() {

		when(userRep.findById(1L)).thenReturn(Optional.of(user));
		when(itemRep.findById(1L)).thenReturn(Optional.of(item));
		when(invRep.findByIdUsuario(1L)).thenReturn(Optional.of(List.of(inventario)));

		int result = tiendaService.realizarCompra(1L, 1L);

		assertEquals(0, result);

		verify(userRep).save(any(Usuario.class));
		verify(tiendaRep).save(any());
		verify(invRep).save(any(Inventario.class));
	}

	/**
	 * Verifica el caso donde el usuario o el item no existen.
	 */
	@Test
	void testUsuarioOItemNoExiste() {

		when(userRep.findById(1L)).thenReturn(Optional.empty());
		when(itemRep.findById(1L)).thenReturn(Optional.of(item));

		int result = tiendaService.realizarCompra(1L, 1L);

		assertEquals(1, result);
	}

	/**
	 * Verifica el caso donde el usuario no tiene dinero suficiente para comprar.
	 */
	@Test
	void testDineroInsuficiente() {

		user.setDinero(50);

		when(userRep.findById(1L)).thenReturn(Optional.of(user));
		when(itemRep.findById(1L)).thenReturn(Optional.of(item));

		int result = tiendaService.realizarCompra(1L, 1L);

		assertEquals(2, result);
	}

	/**
	 * Verifica la creación de un nuevo item en el inventario cuando no existe previamente.
	 */
	@Test
	void testItemNuevoEnInventario() {

		when(userRep.findById(1L)).thenReturn(Optional.of(user));
		when(itemRep.findById(1L)).thenReturn(Optional.of(item));
		when(invRep.findByIdUsuario(1L)).thenReturn(Optional.empty());

		int result = tiendaService.realizarCompra(1L, 1L);

		assertEquals(0, result);

		verify(invRep).save(any(Inventario.class));
	}
}