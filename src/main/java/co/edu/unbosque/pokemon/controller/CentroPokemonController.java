package co.edu.unbosque.pokemon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.service.CentroPokemonService;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Controlador REST que gestiona las operaciones del Centro Pokémon.
 * <p>
 * Proporciona endpoints para interactuar con los servicios de salud y recuperación
 * de los Pokémon en el sistema. Permite peticiones CORS desde los orígenes locales 
 * en los puertos 8080 y 8081.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
@RestController
@RequestMapping("/centropokemon")
@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:8081", "http://localhost:4200"})
public class CentroPokemonController {

	/**
	 * Servicio que contiene la lógica de negocio para las operaciones del Centro Pokémon.
	 */
	@Autowired
	private CentroPokemonService centroSer;

	/**
	 * Restaura la salud de un Pokémon específico en el sistema utilizando su identificador.
	 * <p>
	 * El método evalúa el resultado de la operación realizada por el servicio:
	 * Si retorna {@code 0}, la curación fue exitosa y se responde con un estado HTTP 202 (Accepted).
	 * Cualquier otro valor indica que el Pokémon no fue hallado, respondiendo un estado HTTP 404 (Not Found).
	 * </p>
	 * 
	 * @param idPokemon El identificador único del Pokémon que se desea curar.
	 * @return Un {@link ResponseEntity} con el mensaje "Curado" y estado {@link HttpStatus#ACCEPTED} (202) 
	 *          si el proceso fue exitoso; de lo contrario, un mensaje "No encontrado" junto con el 
	 *          estado {@link HttpStatus#NOT_FOUND} (404).
	 */
	@PostMapping("/curar")
	public ResponseEntity<String> curar(@RequestParam long idPokemon) {
		return (centroSer.curar(idPokemon) == 0) ? new ResponseEntity<>("Curado", HttpStatus.ACCEPTED)
				: new ResponseEntity<>("No encontrado", HttpStatus.NOT_FOUND);
	}

		/**
		 * Restaura la salud de todo el equipo Pokémon enviado.
		 */
	@PostMapping("/curarEquipo")
    public ResponseEntity<String> curarEquipo(@RequestBody List<Long> idsPokemon, Authentication authentication) {
        
        Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();
        return (centroSer.curarEquipo(idsPokemon, usuarioAutenticado.getId()) == 0) 
                ? new ResponseEntity<>("Equipo Curado", HttpStatus.ACCEPTED)
                : new ResponseEntity<>("No se curaron Pokémon (o no eran tuyos)", HttpStatus.NOT_FOUND);
    }
}