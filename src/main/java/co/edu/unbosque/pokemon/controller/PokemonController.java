package co.edu.unbosque.pokemon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import co.edu.unbosque.pokemon.service.PokemonService;
import co.edu.unbosque.pokemon.dto.PokemonDTO;

@RestController
@RequestMapping("/pokemon")
@CrossOrigin(origins = "*")
public class PokemonController {

	@Autowired
	private PokemonService pokeSer;

	@GetMapping("/equipo")
	public ResponseEntity<List<PokemonDTO>> verEquipo(@RequestParam Long idUsuario) {
		List<PokemonDTO> equipo = pokeSer.verEquipo(idUsuario);
		if (!equipo.isEmpty()) {
			return new ResponseEntity<>(equipo, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
