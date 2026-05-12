package co.edu.unbosque.pokemon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unbosque.pokemon.dto.UsuarioDTO;
import co.edu.unbosque.pokemon.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioSer;

	@PostMapping("/login")
	public ResponseEntity<UsuarioDTO> login(@RequestParam String u, @RequestParam String p) {
		UsuarioDTO user = usuarioSer.login(u, p);
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/jugadores")
	public ResponseEntity<List<UsuarioDTO>> listarJugadores() {
		List<UsuarioDTO> lista = usuarioSer.obtenerJugadores();
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}