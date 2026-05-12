package co.edu.unbosque.pokemon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.pokemon.service.AtaqueService;

@RestController
@RequestMapping("/ataque")
@CrossOrigin(origins = "*")
public class AtaqueController {

	@Autowired
	private AtaqueService ataqueSer;

	@PostMapping("/banear")
	public ResponseEntity<String> banear(@RequestParam String nombre) {
		ataqueSer.cambiarEstadoBaneo(nombre, true);
		return new ResponseEntity<>("Ataque baneado", HttpStatus.ACCEPTED);
	}
}