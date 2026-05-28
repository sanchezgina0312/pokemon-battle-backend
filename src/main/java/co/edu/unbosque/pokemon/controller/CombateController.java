package co.edu.unbosque.pokemon.controller;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.edu.unbosque.pokemon.dto.CombateDTO;
import co.edu.unbosque.pokemon.service.CombateService;
import co.edu.unbosque.pokemon.service.PokemonService;
import co.edu.unbosque.pokemon.service.UsuarioService;

/**
 * Controlador REST que gestiona las operaciones relacionadas con los combates
 * Pokémon.
 * <p>
 * Ofrece endpoints para registrar los resultados de los enfrentamientos y
 * consultar el historial de peleas por usuario. Habilita peticiones CORS desde
 * los entornos locales en los puertos 8080 y 8081.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
@RestController
@RequestMapping("/combate")
public class CombateController {

	/**
	 * Servicio que encapsula la lógica de negocio asociada a los combates.
	 */
	@Autowired
	private CombateService combateSer;

	/**
	 * Servicio que gestiona la lógica de persistencia y estado de los Pokémon.
	 * Utilizado para actualizar niveles y experiencia tras los combates.
	 */
	@Autowired
	private PokemonService pokemonSer;

	/**
	 * Servicio que gestiona la lógica de cuentas de usuario. Utilizado para
	 * actualizar el saldo de dinero del entrenador.
	 */
	@Autowired
	private UsuarioService usuarioSer;

	/**
	 * Registra un nuevo registro de combate en el sistema.
	 * <p>
	 * Recibe la información del enfrentamiento en formato JSON a través del cuerpo
	 * de la petición, delega la persistencia al servicio y retorna el objeto
	 * guardado.
	 * </p>
	 * 
	 * @param data Objeto {@link CombateDTO} con los detalles del combate a
	 *             registrar.
	 * @return Un {@link ResponseEntity} que contiene el {@link CombateDTO}
	 *         registrado y el estado HTTP {@link HttpStatus#CREATED} (201).
	 */
	@PostMapping("/registrar")
	public ResponseEntity<CombateDTO> guardar(@RequestBody CombateDTO data) {
		CombateDTO respuesta = combateSer.crearRegistro(data);
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}

	/**
	 * Recupera el historial de combates en los que ha participado un usuario en
	 * específico.
	 * <p>
	 * Evalúa la lista devuelta por el servicio: si contiene registros, devuelve la
	 * lista con un estado HTTP 200 (OK). Si la lista está vacía, se interpreta que
	 * no hay actividad y responde con un estado HTTP 204 (No Content).
	 * </p>
	 * 
	 * @param idUser El identificador único del usuario consultado.
	 * @return Un {@link ResponseEntity} con la lista de {@link CombateDTO} y estado
	 *         {@link HttpStatus#OK} (200) si posee registros; de lo contrario, un
	 *         {@link ResponseEntity} vacío con estado {@link HttpStatus#NO_CONTENT}
	 *         (204).
	 */
	@GetMapping("/historial")
	public ResponseEntity<List<CombateDTO>> verHistorial(@RequestParam Long idUser) {
		List<CombateDTO> lista = combateSer.historialPeleas(idUser);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("/calcular-danio")
	public ResponseEntity<Integer> calcularDanio(@RequestBody Map<String, Object> params) {
		try {
			int nivel = (int) params.get("nivel");
			int atk = (int) params.get("atk");
			int def = (int) params.get("def");
			String tipoAtk = (String) params.get("tipoAtk");
			String tipoDef = (String) params.get("tipoDef");

			int danio = combateSer.calcularDanio(nivel, atk, def, tipoAtk, tipoDef);
			return new ResponseEntity<>(danio, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Procesa la finalización de un combate, calculando y otorgando las recompensas
	 * correspondientes al usuario y su Pokémon.
	 * <p>
	 * Calcula experiencia basada en el nivel del rival y el dinero ganado,
	 * delegando la persistencia a los servicios correspondientes.
	 * </p>
	 * * @param request Mapa que contiene "idPokemon" (Long) y "nivelRival" (int).
	 * 
	 * @return un mapa de las recompensas otorgadas (exp y dinero).
	 */
	@PostMapping("/finalizar")
	public ResponseEntity<Map<String, Object>> finalizarCombate(@RequestBody Map<String, Object> request) {

		Long idPokemon = Long.valueOf(request.get("idPokemon").toString());
		Long idUsuario = Long.valueOf(request.get("idUsuario").toString());
		int nivelRival = (int) request.get("nivelRival");

		int expGanada = (nivelRival * 10);
		int dineroGanado = (nivelRival * 5);

		pokemonSer.sumarExperiencia(idPokemon, expGanada);
		usuarioSer.sumarDinero(idUsuario, dineroGanado);

		Map<String, Object> response = new HashMap<>();
		response.put("expGanada", expGanada);
		response.put("dineroGanado", dineroGanado);
		response.put("mensaje", "Recompensas procesadas");

		return ResponseEntity.ok(response);
	}
}