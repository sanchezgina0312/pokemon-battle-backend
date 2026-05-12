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

import co.edu.unbosque.pokemon.dto.CapturaDTO;
import co.edu.unbosque.pokemon.service.CapturaService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/captura")
@CrossOrigin(origins = "*")
public class CapturaController {
	@Autowired
	private CapturaService capturaSer;

	@PostMapping("/registrar")
	public ResponseEntity<CapturaDTO> registrar(@RequestBody CapturaDTO nueva) {
		return new ResponseEntity<>(capturaSer.registrar(nueva), HttpStatus.CREATED);
	}

	@GetMapping("/historial")
	public ResponseEntity<List<CapturaDTO>> listar(@RequestParam long idUsuario) {
		List<CapturaDTO> lista = capturaSer.obtenerPorUsuario(idUsuario);
		return (!lista.isEmpty()) ? new ResponseEntity<>(lista, HttpStatus.ACCEPTED)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
