package co.edu.unbosque.pokemon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.pokemon.service.CentroPokemonService;

@RestController
@RequestMapping("/centropokemon")
@CrossOrigin(origins = "*")
public class CentroPokemonController {

	@Autowired
	private CentroPokemonService centroSer;

	@PostMapping("/curar")
	public ResponseEntity<String> curar(@RequestParam long idPokemon) {
		int resultado = centroSer.curarPokemon(idPokemon);

		if (resultado == 0) {
			return new ResponseEntity<>("Pokémon curado completamente", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No se pudo encontrar al Pokémon", HttpStatus.NOT_FOUND);
		}
	}
}
