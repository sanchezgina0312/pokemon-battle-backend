package co.edu.unbosque.pokemon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.unbosque.pokemon.entity.Item;
import co.edu.unbosque.pokemon.service.ItemService;

@RestController
@RequestMapping("/item")
@CrossOrigin(origins = "*")
public class ItemController {

	@Autowired
	private ItemService itemSer;

	@GetMapping("/catalogo")
	public ResponseEntity<List<Item>> listarItems() {
		List<Item> lista = itemSer.listarTodos();
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crearNuevoItem(@RequestBody Item item) {
		itemSer.guardar(item);
		return new ResponseEntity<>("Item añadido al sistema", HttpStatus.CREATED);
	}
}
