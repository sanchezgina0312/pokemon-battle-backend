package co.edu.unbosque.pokemon.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AuditoriaService auditoriaService;

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

        auditoriaService.registrar("COMPRAR", "Item: " + item.getNombre() + " | Usuario ID: " + idUsuario);
        return 0;
    }

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

    private void actualizarMochila(long idUsuario, long idItem) {
        Optional<List<Inventario>> invOpt = invRep.findByIdUsuario(idUsuario);
        Inventario itemEnMochila = invOpt.flatMap(list ->
            list.stream().filter(i -> i.getIdItem() == idItem).findFirst()
        ).orElse(null);

        if (itemEnMochila != null) {
            itemEnMochila.setCantidad(itemEnMochila.getCantidad() + 1);
            invRep.save(itemEnMochila);
        } else {
            invRep.save(new Inventario(idUsuario, idItem, 1));
        }
    }
}