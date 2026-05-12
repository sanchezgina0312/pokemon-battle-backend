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
		int resultado = tiendaSer.realizarCompra(idUsuario, idItem);

		if (resultado == 0) {
			return new ResponseEntity<>("Compra realizada con éxito", HttpStatus.ACCEPTED);
		} else if (resultado == 2) {
			return new ResponseEntity<>("Fondos insuficientes", HttpStatus.PAYMENT_REQUIRED);
		} else {
			return new ResponseEntity<>("Error en la transacción", HttpStatus.BAD_REQUEST);
		}
	}
}