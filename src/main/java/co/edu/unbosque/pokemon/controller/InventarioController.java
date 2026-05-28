package co.edu.unbosque.pokemon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import co.edu.unbosque.pokemon.service.InventarioService;
import co.edu.unbosque.pokemon.dto.InventarioDTO;

/**
 * Controlador REST que gestiona las operaciones relacionadas con el inventario
 * de los usuarios.
 * <p>
 * Proporciona endpoints para consultar los objetos, ítems o herramientas
 * (mochila) que posee un entrenador en el sistema. Permite peticiones CORS
 * desde los orígenes locales en los puertos 8080 y 8081.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
@RestController
@RequestMapping("/inventario")
public class InventarioController {

	/**
	 * Servicio que contiene la lógica de negocio para la gestión del inventario.
	 */
	@Autowired
	private InventarioService invSer;

	/**
	 * Obtiene la lista de ítems en la mochila de un usuario específico.
	 * <p>
	 * Realiza una consulta mediante el identificador del usuario recibido como
	 * parámetro. Si la mochila contiene elementos, retorna la lista de ítems junto
	 * con un estado HTTP 200 (OK). En caso de que el inventario esté vacío, retorna
	 * una respuesta sin cuerpo con un estado HTTP 204 (No Content).
	 * </p>
	 * 
	 * @param idUsuario El identificador único del usuario o entrenador del cual se
	 *                  quiere consultar la mochila.
	 * @return Un {@link ResponseEntity} que contiene la lista de
	 *         {@link InventarioDTO} y el estado HTTP {@link HttpStatus#OK} (200) si
	 *         tiene ítems; de lo contrario, un {@link ResponseEntity} vacío con
	 *         estado HTTP {@link HttpStatus#NO_CONTENT} (204).
	 */
	@GetMapping("/mochila")
	public ResponseEntity<List<InventarioDTO>> verMochila(@RequestParam Long idUsuario) {
		List<InventarioDTO> mochila = invSer.verMochila(idUsuario);
		if (!mochila.isEmpty()) {
			return new ResponseEntity<>(mochila, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Recupera el catálogo completo de todos los objetos disponibles en el sistema.
	 * <p>
	 * Este método invoca al servicio para obtener la lista global de ítems
	 * registrados en el catálogo y la retorna junto con un estado HTTP 200 (OK).
	 * </p>
	 * * @return Un {@link ResponseEntity} que contiene una lista de objetos
	 * {@link InventarioDTO} con la información de todos los ítems y el estado HTTP
	 * {@link HttpStatus#OK}.
	 */
	@GetMapping("/listar-todo")
	public ResponseEntity<List<InventarioDTO>> listarTodo() {
		List<InventarioDTO> lista = invSer.obtenerCatalogoCompleto();
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
}