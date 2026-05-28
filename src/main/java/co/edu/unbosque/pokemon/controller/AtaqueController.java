package co.edu.unbosque.pokemon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.pokemon.dto.AtaqueDTO;
import co.edu.unbosque.pokemon.service.AtaqueService;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los ataques de
 * los Pokémon.
 * <p>
 * Proporciona endpoints para modificar el estado y la disponibilidad de los
 * ataques en el sistema. Permite peticiones CORS desde los orígenes locales en
 * los puertos 8080 y 8081.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
@RestController
@RequestMapping("/ataque")
public class AtaqueController {

	/**
	 * Servicio que contiene la lógica de negocio para la gestión de ataques.
	 */
	@Autowired
	private AtaqueService ataqueSer;

	/**
	 * Banea un ataque del sistema buscando por su nombre específico.
	 * <p>
	 * Este método recibe el nombre de un ataque mediante un parámetro de consulta
	 * (Query Param), invoca al servicio para cambiar su estado de baneo a
	 * {@code true} y retorna una respuesta de confirmación con estado HTTP 202
	 * (Accepted).
	 * </p>
	 * 
	 * @param nombre El nombre del ataque que se desea banear. No debe ser nulo o
	 *               vacío.
	 * @return Un {@link ResponseEntity} que contiene el mensaje de confirmación
	 *         "Ataque baneado" y el estado HTTP {@link HttpStatus#ACCEPTED}.
	 */
	@PostMapping("/banear")
	public ResponseEntity<String> banear(@RequestParam String nombre) {
		ataqueSer.cambiarEstadoBaneo(nombre, true);
		return new ResponseEntity<>("Ataque baneado", HttpStatus.ACCEPTED);
	}

	/**
	 * Recupera el catálogo completo de ataques disponibles en el sistema.
	 * <p>
	 * Este método invoca al servicio para obtener la lista de todos los ataques
	 * registrados y la retorna junto con un estado HTTP 200 (OK).
	 * </p>
	 * * @return Un {@link ResponseEntity} que contiene una lista de objetos
	 * {@link AtaqueDTO} con la información de todos los ataques y el estado HTTP
	 * {@link HttpStatus#OK}.
	 */
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<AtaqueDTO>> mostrarTodos() {
		List<AtaqueDTO> lista = ataqueSer.obtenerCatalogoAtaques();
		return new ResponseEntity<>(lista, HttpStatus.OK);
	}
}