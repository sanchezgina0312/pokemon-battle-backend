package co.edu.unbosque.pokemon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.pokemon.service.TiendaService;

@RestController
@RequestMapping("/tienda")
@CrossOrigin(origins = "*")
public class TiendaController {

	@Autowired
	private TiendaService tiendaSer;

	@PostMapping("/comprar")
	public ResponseEntity<String> comprar(@RequestParam long idUsuario, @RequestParam long idItem) {
		int res = tiendaSer.realizarCompra(idUsuario, idItem);
		if (res == 0)
			return new ResponseEntity<>("¡Compra exitosa!", HttpStatus.ACCEPTED);
		if (res == 2)
			return new ResponseEntity<>("No tienes suficiente dinero", HttpStatus.PAYMENT_REQUIRED);
		return new ResponseEntity<>("Error al procesar", HttpStatus.BAD_REQUEST);
	}
}