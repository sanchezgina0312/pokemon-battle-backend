package co.edu.unbosque.pokemon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.pokemon.entity.*;
import co.edu.unbosque.pokemon.repository.*;

@Service
public class TiendaService {
	@Autowired private ItemRepository itemRep;
	@Autowired private UsuarioRepository userRep;

	public String comprarItem(long idUsuario, long idItem) {
		Usuario u = userRep.findById(idUsuario).get();
		Item i = itemRep.findById(idItem).get();

		if (u.getDinero() >= i.getCosto()) {
			u.setDinero(u.getDinero() - i.getCosto());
			userRep.save(u);
			return "Compra exitosa de " + i.getNombre();
		}
		return "Dinero insuficiente";
	}
}