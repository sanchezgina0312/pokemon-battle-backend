package co.edu.unbosque.pokemon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.pokemon.dto.CapturaDTO;
import co.edu.unbosque.pokemon.service.CapturaService;

/**
 * Controlador REST que gestiona las operaciones relacionadas con las capturas de Pokémon.
 * <p>
 * Proporciona endpoints para registrar nuevas capturas y consultar el historial de capturas
 * de los usuarios. Permite peticiones CORS desde los puertos locales 8080 y 8081.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
@RestController
@RequestMapping("/captura")
@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:8081", "http://localhost:4200"})
public class CapturaController {

	/**
	 * Servicio que contiene la lógica de negocio para la gestión de capturas.
	 */
	@Autowired
	private CapturaService capturaSer;

	/**
	 * Registra una nueva captura de Pokémon en el sistema.
	 * <p>
	 * Recibe la información de la captura en el cuerpo de la petición (JSON) y la procesa
	 * a través del servicio correspondiente.
	 * </p>
	 * 
	 * @param nueva Objeto {@link CapturaDTO} que contiene los datos de la captura a registrar.
	 * @return Un {@link ResponseEntity} que contiene el {@link CapturaDTO} guardado con éxito 
	 *          y el estado HTTP {@link HttpStatus#CREATED} (201).
	 */
	@PostMapping("/registrar")
	public ResponseEntity<CapturaDTO> registrar(@RequestBody CapturaDTO nueva) {
		return new ResponseEntity<>(capturaSer.registrar(nueva), HttpStatus.CREATED);
	}

	/**
	 * Obtiene el historial de capturas realizadas por un usuario específico.
	 * <p>
	 * Busca en el sistema todas las capturas asociadas al ID del usuario enviado como parámetro.
	 * Si el usuario tiene capturas, las retorna con un estado HTTP 202 (Accepted). 
	 * Si no se encuentra ninguna captura, retorna una respuesta vacía con estado HTTP 204 (No Content).
	 * </p>
	 * 
	 * @param idUsuario El identificador único del usuario del cual se quiere consultar el historial.
	 * @return Un {@link ResponseEntity} con la lista de {@link CapturaDTO} y estado {@link HttpStatus#ACCEPTED} (202) 
	 *          si contiene datos, o un {@link ResponseEntity} vacío con estado {@link HttpStatus#NO_CONTENT} (204) 
	 *          si la lista está vacía.
	 */
	@GetMapping("/historial")
	public ResponseEntity<List<CapturaDTO>> listar(@RequestParam long idUsuario) {
		List<CapturaDTO> lista = capturaSer.obtenerPorUsuario(idUsuario);
		return (!lista.isEmpty()) ? new ResponseEntity<>(lista, HttpStatus.ACCEPTED)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}