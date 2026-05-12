package co.edu.unbosque.pokemon.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.pokemon.entity.*;
import co.edu.unbosque.pokemon.repository.*;

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

	public int realizarCompra(long idUsuario, long idItem) {
		Optional<Usuario> usuario = userRep.findById(idUsuario);
		Optional<Item> items = itemRep.findById(idItem);

		if (!usuario.isPresent() || !items.isPresent()) {
			return 1;
		}

		Usuario user = usuario.get();
		Item item = items.get();

		int precioFinal = 0;
		String nombre = item.getNombre().toUpperCase();

		switch (nombre) {
		case "POCION":
			precioFinal = 200;
			break;
		case "POKEBOLA":
			precioFinal = 200;
			break;
		case "SUPERBOLA":
			precioFinal = 600;
			break;
		case "ULTRABOLA":
			precioFinal = 1200;
			break;
		case "REVIVIR":
			precioFinal = 1500;
			break;
		case "ANTIDOTO":
			precioFinal = 100;
			break;
		case "ANTIQUEMAR":
			precioFinal = 250;
			break;
		case "ANTIPARALIZ":
			precioFinal = 200;
			break;
		case "DESHIELO":
			precioFinal = 250;
			break;
		case "DESPERTAR":
			precioFinal = 250;
			break;
		case "CURA TOTAL":
			precioFinal = 600;
			break;

		default:
			precioFinal = item.getCosto();
		}

		if (user.getDinero() < precioFinal) {
			return 2;
		}

		user.setDinero(user.getDinero() - precioFinal);
		userRep.save(user);

		Tienda registro = new Tienda(idUsuario, idItem, LocalDateTime.now());
		tiendaRep.save(registro);

		actualizarMochila(idUsuario, idItem);

		return 0;
	}

	private void actualizarMochila(long idUsuario, long idItem) {
		Optional<List<Inventario>> invOpt = invRep.findByIdUsuario(idUsuario);
		Inventario itemEnMochila = null;

		if (invOpt.isPresent()) {
			for (Inventario inv : invOpt.get()) {
				if (inv.getIdItem() == idItem) {
					itemEnMochila = inv;
					break;
				}
			}
		}

		if (itemEnMochila != null) {
			itemEnMochila.setCantidad(itemEnMochila.getCantidad() + 1);
			invRep.save(itemEnMochila);
		} else {
			Inventario nuevoItem = new Inventario(idUsuario, idItem, 1);
			invRep.save(nuevoItem);
		}
	}
}