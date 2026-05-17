package co.edu.unbosque.pokemon.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.pokemon.dto.ItemDTO;
import co.edu.unbosque.pokemon.service.ItemService;

/**
 * Controlador REST que gestiona las operaciones globales de los ítems u objetos del sistema.
 * <p>
 * Proporciona endpoints para visualizar el catálogo comercial o general de objetos disponibles
 * en el mundo Pokémon. Permite peticiones CORS desde los orígenes locales en los puertos 
 * 8080 y 8081.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
@RestController
@RequestMapping("/item")
@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:8081"})
public class ItemController {

	/**
	 * Servicio que contiene la lógica de negocio para la gestión y listado de ítems.
	 */
	@Autowired
	private ItemService itemSer;

	/**
	 * Recupera el catálogo completo de ítems registrados en el sistema.
	 * <p>
	 * Este método no requiere parámetros de filtrado. Consulta todos los objetos existentes
	 * a través del servicio; si existen registros en la base de datos, los retorna junto con 
	 * un estado HTTP 200 (OK). Si el sistema no cuenta con ningún ítem registrado, 
	 * devuelve una respuesta sin cuerpo con el estado HTTP 204 (No Content).
	 * </p>
	 * 
	 * @return Un {@link ResponseEntity} que contiene la lista global de {@link ItemDTO} 
	 *          y el estado HTTP {@link HttpStatus#OK} (200) si hay registros disponibles; 
	 *          de lo contrario, un {@link ResponseEntity} vacío con estado HTTP 
	 *          {@link HttpStatus#NO_CONTENT} (204).
	 */
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