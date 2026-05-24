package co.edu.unbosque.pokemon.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.entity.Inventario;
import co.edu.unbosque.pokemon.entity.Item;
import co.edu.unbosque.pokemon.entity.Tienda;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.repository.InventarioRepository;
import co.edu.unbosque.pokemon.repository.ItemRepository;
import co.edu.unbosque.pokemon.repository.TiendaRepository;
import co.edu.unbosque.pokemon.repository.UsuarioRepository;
import co.edu.unbosque.pokemon.util.LanzadorDeException;

/**
 * Servicio encargado de la lógica de la tienda del sistema Pokémon.
 * <p>
 * Gestiona la compra de ítems, validación de usuarios, cálculo de precios
 * dinámicos, registro de transacciones y actualización del inventario del usuario.
 * </p>
 */
@Service
public class TiendaService {

    @Autowired
    private UsuarioRepository userRep;

    @Autowired
    private ItemRepository itemRep;

    @Autowired
    private TiendaRepository tiendaRep;

    @Autowired
    private InventarioRepository invRep;

    /**
     * Realiza la compra de un ítem por parte de un usuario.
     *
     * <p>
     * Flujo:
     * <ul>
     *   <li>Valida IDs</li>
     *   <li>Busca usuario e ítem</li>
     *   <li>Calcula precio final</li>
     *   <li>Verifica dinero disponible</li>
     *   <li>Descuenta dinero</li>
     *   <li>Registra compra</li>
     *   <li>Actualiza inventario</li>
     * </ul>
     * </p>
     *
     * @param idUsuario identificador del usuario comprador.
     * @param idItem identificador del ítem a comprar.
     * @return 0 si la compra fue exitosa, 1 si no existe usuario o ítem,
     *         2 si el usuario no tiene suficiente dinero.
     */
    public int realizarCompra(long idUsuario, long idItem) {
        LanzadorDeException.verificarId(idUsuario);
        LanzadorDeException.verificarId(idItem);

        Optional<Usuario> usuarioOpt = userRep.findById(idUsuario);
        Optional<Item> itemOpt = itemRep.findById(idItem);

        if (usuarioOpt.isEmpty() || itemOpt.isEmpty()) return 1;

        Usuario user = usuarioOpt.get();
        Item item = itemOpt.get();

        int precioFinal = calcularPrecio(item);

        if (user.getDinero() < precioFinal) return 2;

        user.setDinero(user.getDinero() - precioFinal);
        userRep.save(user);

        tiendaRep.save(new Tienda(idUsuario, idItem, LocalDateTime.now()));

        actualizarMochila(idUsuario, idItem);

        return 0;
    }

    /**
     * Calcula el precio final de un ítem según reglas del negocio.
     *
     * <p>
     * Algunos ítems tienen precio fijo independiente del valor en base de datos.
     * </p>
     *
     * @param item ítem a evaluar.
     * @return precio final del ítem.
     */
    private int calcularPrecio(Item item) {
        String nombre = item.getNombre().toUpperCase();

        return switch (nombre) {
            case "ANTIPARALIZ", "POKÉ BALL" -> 200;
            case "SUPERBOLA", "CURA TOTAL" -> 600;
            case "ULTRABOLA" -> 1200;
            case "REVIVIR" -> 1500;
            case "ANTÍDOTO" -> 100;
            case "ANTIQUEMAR", "ANTIHIELO", "DESPERTAR" -> 250;
            case "SUPERPOSICIÓN" -> 700;
            case "REPELENTE" -> 300;
            default -> item.getCosto();
        };
    }

    /**
     * Actualiza el inventario del usuario después de una compra.
     *
     * <p>
     * Si el ítem ya existe en la mochila, incrementa su cantidad.
     * Si no existe, lo crea con cantidad inicial de 1.
     * </p>
     *
     * @param idUsuario identificador del usuario.
     * @param idItem identificador del ítem.
     */
    private void actualizarMochila(long idUsuario, long idItem) {
        Optional<List<Inventario>> invOpt = invRep.findByIdUsuario(idUsuario);

        Inventario itemEnMochila = invOpt.flatMap(list ->
                list.stream()
                        .filter(i -> i.getIdItem() == idItem)
                        .findFirst()
        ).orElse(null);

        if (itemEnMochila != null) {
            itemEnMochila.setCantidad(itemEnMochila.getCantidad() + 1);
            invRep.save(itemEnMochila);
        } else {
            invRep.save(new Inventario(idUsuario, idItem, 1));
        }
    }
}