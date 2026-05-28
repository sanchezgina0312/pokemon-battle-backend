package co.edu.unbosque.pokemon.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.pokemon.dto.EspeciePokemonDTO;
import co.edu.unbosque.pokemon.dto.GritoPokemonDTO;
import co.edu.unbosque.pokemon.dto.InformacionPokemonDTO;
import co.edu.unbosque.pokemon.dto.PokemonDTO;
import co.edu.unbosque.pokemon.dto.SpriteItemDTO;
import co.edu.unbosque.pokemon.dto.TipoPokemonDTO;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.exception.IdInvalidoException;
import co.edu.unbosque.pokemon.service.PokemonHTTPRequestHandler;
import co.edu.unbosque.pokemon.service.PokemonService;

/**
 * Controlador REST que gestiona las operaciones principales de los Pokémon,
 * incluyendo captura, actualización de estadísticas, consultas y servicios de
 * API externa.
 */
@RestController
@RequestMapping("/pokemon")
public class PokemonController {

	/** Servicio que contiene la lógica de negocio para la gestión de Pokémon. */
	@Autowired
	private PokemonService pokemonService;

	/** Constructor por defecto del controlador. */
	public PokemonController() {

	}

	/**
	 * Registra un nuevo Pokémon en el sistema como capturado. * @param nuevoPokemon
	 * DTO con la información del Pokémon a registrar.
	 * 
	 * @return Un {@link ResponseEntity} con un mensaje de éxito o error.
	 */
	@PostMapping("/capturar")
	public ResponseEntity<String> crearPokemon(@RequestBody PokemonDTO nuevoPokemon) {
		try {
			int status = pokemonService.create(nuevoPokemon);
			if (status == 0) {
				return new ResponseEntity<>("¡Pokémon capturado con éxito!", HttpStatus.CREATED);
			}
			return new ResponseEntity<>("Error al registrar el Pokémon", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error interno al capturar: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Obtiene la lista de Pokémon capturados por los usuarios. Si la lista local
	 * está vacía, carga datos de referencia desde la PokeAPI. * @return Una lista
	 * de {@link PokemonDTO} con los Pokémon disponibles.
	 */
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<PokemonDTO>> mostrarTodo() {
		List<PokemonDTO> listaLocal = pokemonService.getAll();

		if (listaLocal.isEmpty()) {
			List<PokemonDTO> listaAdmin = new ArrayList<>();
			if (PokemonHTTPRequestHandler.getPokedexDatos().isEmpty()) {
				PokemonHTTPRequestHandler.cargarPokedex();
			}

			for (InformacionPokemonDTO info : PokemonHTTPRequestHandler.getPokedexDatos()) {
				PokemonDTO dto = new PokemonDTO();
				dto.setPokeApiId(info.getId());
				dto.setApodo(info.getNombre());

				if (info.getListaTipos() != null && !info.getListaTipos().isEmpty()) {
					String tipo = info.getListaTipos().get(0).getInformacionTipo().getNombreTipo();
					dto.setEstado(tipo);
				}
				listaAdmin.add(dto);
			}
			return new ResponseEntity<>(listaAdmin, HttpStatus.OK);
		}
		return new ResponseEntity<>(listaLocal, HttpStatus.OK);
	}

	/**
	 * Actualiza las estadísticas, salud y ataques de un Pokémon específico en la
	 * base de datos. * @param id Identificador del Pokémon.
	 * 
	 * @param apodo                Nuevo apodo.
	 * @param nivel                Nivel actual.
	 * @param experienciaAcumulada Experiencia total acumulada.
	 * @param saludActual          Salud restante.
	 * @param saludMaxima          Salud máxima posible.
	 * @param nombreAtaque1        al 4 Nombres de los ataques equipados.
	 * @param estado               Estado actual del Pokémon.
	 * @return Mensaje confirmando la actualización o error.
	 */
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarPokemon(@RequestParam Long id, @RequestParam String apodo,
			@RequestParam int nivel, @RequestParam int experienciaAcumulada, @RequestParam int saludActual,
			@RequestParam int saludMaxima, @RequestParam String nombreAtaque1, @RequestParam String nombreAtaque2,
			@RequestParam String nombreAtaque3, @RequestParam String nombreAtaque4, @RequestParam String estado) {
		try {
			PokemonDTO pActualizado = new PokemonDTO();
			pActualizado.setApodo(apodo);
			pActualizado.setNivel(nivel);
			pActualizado.setExperienciaAcumulada(experienciaAcumulada);
			pActualizado.setSaludActual(saludActual);
			pActualizado.setSaludMaxima(saludMaxima);
			pActualizado.setNombreAtaque1(nombreAtaque1);
			pActualizado.setNombreAtaque2(nombreAtaque2);
			pActualizado.setNombreAtaque3(nombreAtaque3);
			pActualizado.setNombreAtaque4(nombreAtaque4);
			pActualizado.setEstado(estado);

			int status = pokemonService.updateById(id, pActualizado);

			if (status == 0) {
				return new ResponseEntity<>("Estadísticas de Pokémon actualizadas.", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("No se pudo actualizar el Pokémon.", HttpStatus.BAD_REQUEST);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Actualiza la configuración básica de una especie Pokémon. * @param pokeApiId
	 * ID referencial de la PokeAPI.
	 * 
	 * @param apodo  Apodo asignado.
	 * @param nivel  Nivel definido.
	 * @param estado Estado del Pokémon.
	 * @return Confirmación de la configuración guardada.
	 */
	@PutMapping("/actualizar-configuracion")
	public ResponseEntity<String> actualizarConfiguracion(@RequestParam Integer pokeApiId, @RequestParam String apodo,
			@RequestParam Integer nivel, @RequestParam String estado) {
		try {
			PokemonDTO dto = new PokemonDTO();
			dto.setPokeApiId(pokeApiId);
			dto.setApodo(apodo);
			dto.setNivel(nivel);
			dto.setEstado(estado);

			pokemonService.actualizarConfiguracionEspecie(dto);
			return new ResponseEntity<>("Configuración guardada", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al guardar: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Libera a un Pokémon del sistema eliminándolo de la base de datos. * @param id
	 * Identificador único del Pokémon.
	 * 
	 * @return Mensaje indicando si fue liberado con éxito o si no se encontró.
	 */
	@DeleteMapping("/liberar")
	public ResponseEntity<String> eliminarPokemon(@RequestParam Long id) {
		try {
			int status = pokemonService.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("El Pokémon ha sido liberado.", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("ID de Pokémon no encontrado.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Busca todos los Pokémon que pertenecen a un entrenador específico. * @param
	 * idUsuarioPropietario ID del usuario.
	 * 
	 * @return Lista de {@link PokemonDTO} pertenecientes al usuario.
	 */
	@GetMapping("/buscarporentrenador")
	public ResponseEntity<List<PokemonDTO>> buscarPorEntrenador(@RequestParam Long idUsuarioPropietario) {
		List<PokemonDTO> lista = pokemonService.findByPropietario(idUsuarioPropietario);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca Pokémon registrados que coincidan con un apodo específico. * @param
	 * apodo Apodo a buscar.
	 * 
	 * @return Lista de Pokémon encontrados.
	 */
	@GetMapping("/buscarporapodo")
	public ResponseEntity<List<PokemonDTO>> buscarPorApodo(@RequestParam String apodo) {
		List<PokemonDTO> lista = pokemonService.findByApodo(apodo);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Obtiene los detalles de un Pokémon local mediante su ID. * @param id
	 * Identificador del Pokémon.
	 * 
	 * @return {@link PokemonDTO} con la información o NOT_FOUND si no existe.
	 */
	@GetMapping("/buscarporid")
	public ResponseEntity<PokemonDTO> buscarPorId(@RequestParam Long id) {
		PokemonDTO p = obtenerPokemonLocal(id);
		if (p != null) {
			return new ResponseEntity<>(p, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Recupera la URL del sprite (frente o espalda) de un Pokémon desde la API
	 * externa. * @param id ID local del Pokémon.
	 * 
	 * @return Objeto {@link SpriteItemDTO} con la URL del sprite.
	 */
	@GetMapping("/{id}/sprites/front")
	public ResponseEntity<SpriteItemDTO> getSpriteFrente(@PathVariable Long id) {
		PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
		if (pokemonLocal == null)
			return ResponseEntity.notFound().build();

		InformacionPokemonDTO detalleAPI = PokemonHTTPRequestHandler
				.obtenerDetallePokemon(String.valueOf(pokemonLocal.getPokeApiId()));

		if (detalleAPI != null && detalleAPI.getImagenes() != null) {
			return ResponseEntity.ok(detalleAPI.getImagenes().getFrontDefault());
		}
		return ResponseEntity.noContent().build();
	}

	/**
	 * Recupera la URL del sprite (frente o espalda) de un Pokémon desde la API
	 * externa. * @param id ID local del Pokémon.
	 * 
	 * @return Objeto {@link SpriteItemDTO} con la URL del sprite.
	 */
	@GetMapping("/{id}/sprites/back")
	public ResponseEntity<SpriteItemDTO> getSpriteAtras(@PathVariable Long id) {
		PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
		if (pokemonLocal == null)
			return ResponseEntity.notFound().build();

		InformacionPokemonDTO detalleAPI = PokemonHTTPRequestHandler
				.obtenerDetallePokemon(String.valueOf(pokemonLocal.getPokeApiId()));

		if (detalleAPI != null && detalleAPI.getImagenes() != null) {
			return ResponseEntity.ok(detalleAPI.getImagenes().getBackDefault());
		}
		return ResponseEntity.noContent().build();
	}

	/**
	 * Genera la URL del grito (sonido) de un Pokémon basado en su ID de la API.
	 * * @param id ID local del Pokémon.
	 * 
	 * @return {@link GritoPokemonDTO} con la URL del archivo de audio.
	 */
	@GetMapping("/{id}/grito")
	public ResponseEntity<GritoPokemonDTO> getGrito(@PathVariable Long id) {
		PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
		if (pokemonLocal == null)
			return ResponseEntity.notFound().build();

		GritoPokemonDTO grito = new GritoPokemonDTO();
		grito.setGritoPokemon("https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/legacy/"
				+ pokemonLocal.getPokeApiId() + ".ogg");
		return ResponseEntity.ok(grito);
	}

	/**
	 * Obtiene y traduce la descripción de la historia de un Pokémon a un idioma
	 * específico. * @param id ID local del Pokémon.
	 * 
	 * @param idioma Código del idioma destino.
	 * @return Texto de la historia traducida.
	 */
	@GetMapping("/{id}/historia")
	public ResponseEntity<String> obtenerHistoriaTraducida(@PathVariable Long id, @RequestParam String idioma) {
		PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
		if (pokemonLocal == null)
			return ResponseEntity.notFound().build();

		EspeciePokemonDTO especie = PokemonHTTPRequestHandler.obtenerEspeciePokemon(pokemonLocal.getPokeApiId());
		String textoBase = PokemonHTTPRequestHandler.extraerTextoPorIdioma(especie.getListaDescripciones(), "en");
		String textoTraducido = PokemonHTTPRequestHandler.traducirTexto(textoBase, idioma);
		return ResponseEntity.ok(textoTraducido);
	}

	/**
	 * Consulta información detallada de un Pokémon desde la API externa (uso
	 * general). * @param id ID de la especie en la PokeAPI.
	 * 
	 * @return Información detallada del Pokémon.
	 */
	@GetMapping("/salvaje/{id}")
	public ResponseEntity<InformacionPokemonDTO> obtenerPokemonSalvaje(@PathVariable String id) {
		InformacionPokemonDTO detalle = PokemonHTTPRequestHandler.obtenerDetallePokemon(id);
		if (detalle != null) {
			return new ResponseEntity<>(detalle, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Genera una lista completa de todos los Pokémon disponibles para uso
	 * administrativo. *
	 * 
	 * @return Lista completa de {@link PokemonDTO}.
	 */
	private List<PokemonDTO> listaAdminBase() {
		List<PokemonDTO> personalizados = pokemonService.getAll();
		List<InformacionPokemonDTO> datosMemoria = PokemonHTTPRequestHandler.getPokedexDatos();

		if (datosMemoria == null || datosMemoria.isEmpty()) {
			PokemonHTTPRequestHandler.cargarPokedex();
			datosMemoria = PokemonHTTPRequestHandler.getPokedexDatos();
		}

		List<PokemonDTO> listaAdmin = new ArrayList<>();

		if (datosMemoria == null || datosMemoria.isEmpty()) {
			return listaAdmin;
		}

		for (InformacionPokemonDTO info : datosMemoria) {
			PokemonDTO configuracionBD = null;
			for (PokemonDTO p : personalizados) {
				if (p.getPokeApiId() != null && p.getPokeApiId().equals(info.getId())) {
					configuracionBD = p;
					break;
				}
			}

			PokemonDTO dto = new PokemonDTO();
			dto.setPokeApiId(info.getId());
			dto.setApodo(configuracionBD != null ? configuracionBD.getApodo() : info.getNombre().toUpperCase());
			dto.setNivel(configuracionBD != null ? configuracionBD.getNivel() : 1);
			dto.setEstado(configuracionBD != null ? configuracionBD.getEstado() : "OK");

			List<String> tipos = new ArrayList<>();
			if (info.getListaTipos() != null) {
				for (TipoPokemonDTO t : info.getListaTipos()) {
					if (t.getInformacionTipo() != null) {
						tipos.add(t.getInformacionTipo().getNombreTipo().toUpperCase());
					}
				}
			}
			dto.setTipos(tipos);

			List<String> ataques = PokemonHTTPRequestHandler.extraerCuatroPrimerosAtaques(info.getListaAtaques());
			dto.setNombreAtaque1(ataques.get(0));
			dto.setNombreAtaque2(ataques.get(1));
			dto.setNombreAtaque3(ataques.get(2));
			dto.setNombreAtaque4(ataques.get(3));

			dto.setSaludMaxima(PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "hp"));
			dto.setAtaque(PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "attack"));
			dto.setDefensa(PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "defense"));
			dto.setVelocidad(PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "speed"));

			listaAdmin.add(dto);
		}

		return listaAdmin;
	}

	/**
	 * Endpoints administrativos para consultar datos de especies sin depender de
	 * capturas.
	 */
	@GetMapping("/admin/todos")
	public ResponseEntity<List<PokemonDTO>> mostrarTodoAdmin() {
		return new ResponseEntity<>(listaAdminBase(), HttpStatus.OK);
	}

	/**
	 * Endpoints administrativos para consultar datos de especies sin depender de
	 * capturas.
	 */
	@GetMapping("/admin/especie/{pokeApiId}")
	public ResponseEntity<PokemonDTO> obtenerEspecieAdmin(@PathVariable Integer pokeApiId) {
		PokemonDTO dto = pokemonService.obtenerEspecieParaAdmin(pokeApiId);
		return (dto != null) ? new ResponseEntity<>(dto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	/**
	 * Endpoints administrativos para consultar datos de especies sin depender de
	 * capturas.
	 */
	@GetMapping("/admin/especie/{pokeApiId}/sprites/front")
	public ResponseEntity<SpriteItemDTO> getSpriteFrenteAdmin(@PathVariable Integer pokeApiId) {
		InformacionPokemonDTO detalle = PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(pokeApiId));
		return (detalle != null && detalle.getImagenes() != null)
				? ResponseEntity.ok(detalle.getImagenes().getFrontDefault())
				: ResponseEntity.notFound().build();
	}

	/**
	 * Endpoints administrativos para consultar datos de especies sin depender de
	 * capturas.
	 */
	@GetMapping("/admin/especie/{pokeApiId}/grito")
	public ResponseEntity<GritoPokemonDTO> getGritoAdmin(@PathVariable Integer pokeApiId) {
		GritoPokemonDTO grito = new GritoPokemonDTO();
		grito.setGritoPokemon(
				"https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/legacy/" + pokeApiId + ".ogg");
		return ResponseEntity.ok(grito);
	}

	/**
	 * Asigna un Pokémon inicial a un usuario recién registrado. * @param tipo Tipo
	 * de starter elegido (fuego, etc).
	 * 
	 * @param authentication Sesión del usuario autenticado.
	 * @return Mensaje de éxito tras asignar el starter.
	 */
	@PostMapping("/starter")
	public ResponseEntity<String> elegirStarter(@RequestParam String tipo, Authentication authentication) {
		Usuario usuario = (Usuario) authentication.getPrincipal();

		int pokeApiId = "fuego".equalsIgnoreCase(tipo) ? 4 : 7;

		InformacionPokemonDTO info = PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(pokeApiId));

		if (info == null) {
			return new ResponseEntity<>("No se pudo obtener información del Pokémon", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		PokemonDTO starter = new PokemonDTO();
		starter.setPokeApiId(info.getId());
		starter.setApodo(info.getNombre().toUpperCase());
		starter.setNivel(5);
		starter.setExperienciaAcumulada(0);

		int hp = PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "hp");
		int atk = PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "attack");
		int def = PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "defense");
		int speed = PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "speed");

		starter.setSaludActual(hp);
		starter.setSaludMaxima(hp);
		starter.setAtaque(atk);
		starter.setDefensa(def);
		starter.setVelocidad(speed);

		List<String> tipos = new ArrayList<>();
		for (TipoPokemonDTO t : info.getListaTipos()) {
			tipos.add(t.getInformacionTipo().getNombreTipo().toUpperCase());
		}
		starter.setTipos(tipos);

		List<String> ataques = PokemonHTTPRequestHandler.extraerCuatroPrimerosAtaques(info.getListaAtaques());
		starter.setNombreAtaque1(ataques.size() > 0 ? ataques.get(0) : "---");
		starter.setNombreAtaque2(ataques.size() > 1 ? ataques.get(1) : "---");
		starter.setNombreAtaque3(ataques.size() > 2 ? ataques.get(2) : "---");
		starter.setNombreAtaque4(ataques.size() > 3 ? ataques.get(3) : "---");

		starter.setEstado("ACTIVO");
		starter.setIdUsuarioPropietario(usuario.getId());

		pokemonService.create(starter);
		return new ResponseEntity<>("Starter asignado correctamente", HttpStatus.CREATED);
	}

	/**
	 * Método auxiliar para buscar un objeto PokemonDTO en la lista local. * @param
	 * id ID del Pokémon.
	 * 
	 * @return El Pokémon encontrado o null.
	 */
	private PokemonDTO obtenerPokemonLocal(Long id) {
		return pokemonService.getAll().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
	}
}