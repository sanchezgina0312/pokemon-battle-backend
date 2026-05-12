package co.edu.unbosque.pokemon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.unbosque.pokemon.entity.Combate;
import co.edu.unbosque.pokemon.service.CombateService;

@RestController
@RequestMapping("/combate")
@CrossOrigin(origins = "*")
public class CombateController {

	@Autowired
	private CombateService combateSer;

	@PostMapping("/finalizar")
	public ResponseEntity<String> guardarResultado(@RequestBody Combate resultado) {
		combateSer.guardarRegistro(resultado);
		return new ResponseEntity<>("Resultado de combate guardado", HttpStatus.CREATED);
	}
}