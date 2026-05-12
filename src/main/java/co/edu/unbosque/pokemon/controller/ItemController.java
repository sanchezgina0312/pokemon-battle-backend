package co.edu.unbosque.pokemon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.pokemon.dto.ItemDTO;
import co.edu.unbosque.pokemon.service.ItemService;

@RestController
@RequestMapping("/item")
@CrossOrigin(origins = "*")
public class ItemController {

	@Autowired
	private ItemService itemSer;

	@GetMapping("/catalogo")
	public ResponseEntity<List<ItemDTO>> verCatalogo() {
		List<ItemDTO> lista = itemSer.listarTodos();
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}
}