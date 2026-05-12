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

import co.edu.unbosque.pokemon.service.InventarioService;
import co.edu.unbosque.pokemon.dto.InventarioDTO;

@RestController
@RequestMapping("/inventario")
@CrossOrigin(origins = "*")
public class InventarioController {

	@Autowired
	private InventarioService invSer;

	@GetMapping("/mochila")
	public ResponseEntity<List<InventarioDTO>> verMochila(@RequestParam Long idUsuario) {
		List<InventarioDTO> mochila = invSer.verMochila(idUsuario);
		if (!mochila.isEmpty()) {
			return new ResponseEntity<>(mochila, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
