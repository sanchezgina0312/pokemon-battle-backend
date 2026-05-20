package co.edu.unbosque.pokemon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.pokemon.service.AtaqueService;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los ataques de los Pokémon.
 * <p>
 * Proporciona endpoints para modificar el estado y la disponibilidad de los ataques
 * en el sistema. Permite peticiones CORS desde los orígenes locales en los puertos 8080 y 8081.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
@RestController
@RequestMapping("/ataque")
@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:8081", "http://localhost:4200"})
public class AtaqueController {

	/**
	 * Servicio que contiene la lógica de negocio para la gestión de ataques.
	 */
	@Autowired
	private AtaqueService ataqueSer;

	/**
	 * Banea un ataque del sistema buscando por su nombre específico.
	 * <p>
	 * Este método recibe el nombre de un ataque mediante un parámetro de consulta (Query Param),
	 * invoca al servicio para cambiar su estado de baneo a {@code true} y retorna una respuesta
	 * de confirmación con estado HTTP 202 (Accepted).
	 * </p>
	 * 
	 * @param nombre El nombre del ataque que se desea banear. No debe ser nulo o vacío.
	 * @return Un {@link ResponseEntity} que contiene el mensaje de confirmación "Ataque baneado" 
	 *          y el estado HTTP {@link HttpStatus#ACCEPTED}.
	 */
	@PostMapping("/banear")
	public ResponseEntity<String> banear(@RequestParam String nombre) {
		ataqueSer.cambiarEstadoBaneo(nombre, true);
		return new ResponseEntity<>("Ataque baneado", HttpStatus.ACCEPTED);
	}
}