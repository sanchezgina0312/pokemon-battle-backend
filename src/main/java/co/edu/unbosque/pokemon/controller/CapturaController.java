package co.edu.unbosque.pokemon.controller;

import java.awt.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.pokemon.entity.Captura;
import co.edu.unbosque.pokemon.service.CapturaService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/captura")
@CrossOrigin(origins = "*")
public class CapturaController {

	@Autowired
	private CapturaService capturaSer;

	@PostMapping("/registrar")
	public ResponseEntity<String> registrarCaptura(@RequestBody Captura nueva) {
		int res = capturaSer.registrar(nueva);
		if (res == 0) {
			return new ResponseEntity<>("Captura registrada en el pokedex", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("Error al registrar captura", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/historial")
	public ResponseEntity<List<Captura>> listarPorUsuario(@RequestParam long idUsuario) {
		List<Captura> lista = capturaSer.obtenerPorUsuario(idUsuario);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
