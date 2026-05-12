package co.edu.unbosque.pokemon.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.pokemon.entity.*;
import co.edu.unbosque.pokemon.repository.*;

@Service
public class TiendaService {

	@Autowired
	private ItemRepository itemRep;

	@Autowired
	private UsuarioRepository userRep;

	@Autowired
	private TiendaRepository tiendaRep;

	public int realizarCompra(long idUsuario, long idItem) {
		if (!userRep.existsById(idUsuario) || !itemRep.existsById(idItem)) {
			return 1;
		}

		Usuario u = userRep.findById(idUsuario).get();
		Item i = itemRep.findById(idItem).get();

		if (u.getDinero() >= i.getCosto()) {
			u.setDinero(u.getDinero() - i.getCosto());
			userRep.save(u);

			Tienda transaccion = new Tienda(idUsuario, idItem, LocalDateTime.now());
			tiendaRep.save(transaccion);
			return 0;
		}
		return 2;
	}
}