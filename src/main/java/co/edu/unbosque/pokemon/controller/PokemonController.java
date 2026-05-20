package co.edu.unbosque.pokemon.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.pokemon.dto.DescripcionDTO;
import co.edu.unbosque.pokemon.dto.EspeciePokemonDTO;
import co.edu.unbosque.pokemon.dto.GritoPokemonDTO;
import co.edu.unbosque.pokemon.dto.InformacionPokemonDTO;
import co.edu.unbosque.pokemon.dto.PokemonDTO;
import co.edu.unbosque.pokemon.dto.SpriteItemDTO;
import co.edu.unbosque.pokemon.exception.IdInvalidoException;
import co.edu.unbosque.pokemon.service.PokemonHTTPRequestHandler;
import co.edu.unbosque.pokemon.service.PokemonService;

/**
 * Controlador REST para la gestión de los Pokémon en el sistema.
 * <p>
 * Centraliza las operaciones del ciclo de vida de un Pokémon local (capturar, listar,
 * actualizar y liberar), además de integrar servicios externos para el consumo de recursos multimedia
 * (sprites, audios de gritos) e historias traducidas mediante PokeAPI y traductores externos.
 * Permite peticiones CORS desde los entornos locales en los puertos 8080 y 8081.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
@RestController
@RequestMapping("/pokemon")
@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:8081", "http://localhost:4200"})
public class PokemonController {

	/**
	 * Servicio encargado de la lógica de negocio y persistencia local de los Pokémon.
	 */
	@Autowired
	private PokemonService pokemonService;

	/**
	 * Constructor por defecto de la clase controlador.
	 */
	public PokemonController() {
	}

	/**
	 * Registra un nuevo Pokémon capturado por un usuario entrenador en el sistema.
	 * <p>
	 * Recibe todos los atributos iniciales del Pokémon mediante parámetros de consulta,
	 * construye la entidad de transferencia de datos (DTO) y la envía al servicio local.
	 * Maneja errores de validación de negocio y excepciones imprevistas del sistema.
	 * </p>
	 * 
	 * @param pokeApiId            El ID numérico correspondiente al Pokémon en la PokeAPI.
	 * @param apodo                El sobrenombre personalizado asignado por el entrenador.
	 * @param nivel                El nivel inicial del Pokémon.
	 * @param experienciaAcumulada Los puntos de experiencia actuales.
	 * @param saludActual          Los puntos de vida (HP) actuales del Pokémon.
	 * @param saludMaxima          El límite máximo de puntos de vida del Pokémon.
	 * @param nombreAtaque1        Nombre de la primera habilidad/ataque equipado.
	 * @param nombreAtaque2        Nombre de la segunda habilidad/ataque equipado.
	 * @param nombreAtaque3        Nombre de la tercera habilidad/ataque equipado.
	 * @param nombreAtaque4        Nombre de la cuarta habilidad/ataque equipado.
	 * @param idUsuarioPropietario El identificador único del entrenador dueño del Pokémon.
	 * @param estado               El estado físico o de combate actual (ej. Activo, Debilitado).
	 * @return Un {@link ResponseEntity} con mensaje de éxito y estado {@link HttpStatus#CREATED} (201)
	 *          si el registro es exitoso; {@link HttpStatus#BAD_REQUEST} (400) ante fallos lógicos;
	 *          o {@link HttpStatus#INTERNAL_SERVER_ERROR} (500) en caso de excepciones críticas.
	 */
	@PostMapping("/capturar")
	public ResponseEntity<String> crearPokemon(@RequestParam Integer pokeApiId, @RequestParam String apodo,
			@RequestParam int nivel, @RequestParam int experienciaAcumulada, @RequestParam int saludActual,
			@RequestParam int saludMaxima, @RequestParam String nombreAtaque1, @RequestParam String nombreAtaque2,
			@RequestParam String nombreAtaque3, @RequestParam String nombreAtaque4,
			@RequestParam Long idUsuarioPropietario, @RequestParam String estado) {

		try {
			PokemonDTO nuevoPokemon = new PokemonDTO();
			nuevoPokemon.setPokeApiId(pokeApiId);
			nuevoPokemon.setApodo(apodo);
			nuevoPokemon.setNivel(nivel);
			nuevoPokemon.setExperienciaAcumulada(experienciaAcumulada);
			nuevoPokemon.setSaludActual(saludActual);
			nuevoPokemon.setSaludMaxima(saludMaxima);
			nuevoPokemon.setNombreAtaque1(nombreAtaque1);
			nuevoPokemon.setNombreAtaque2(nombreAtaque2);
			nuevoPokemon.setNombreAtaque3(nombreAtaque3);
			nuevoPokemon.setNombreAtaque4(nombreAtaque4);
			nuevoPokemon.setIdUsuarioPropietario(idUsuarioPropietario);
			nuevoPokemon.setEstado(estado);

			int status = pokemonService.create(nuevoPokemon);

			if (status == 0) {
				return new ResponseEntity<>("¡Pokémon capturado con éxito!", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Error al registrar el Pokémon", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Error interno al capturar", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Recupera y retorna la totalidad de los Pokémon registrados en la base de datos global.
	 * 
	 * @return Un {@link ResponseEntity} con la lista de {@link PokemonDTO} y estado {@link HttpStatus#ACCEPTED} (202)
	 *          si contiene registros; de lo contrario, una lista vacía con estado {@link HttpStatus#NO_CONTENT} (204).
	 */
	 
		@GetMapping("/mostrartodo")
		public ResponseEntity<List<PokemonDTO>> mostrarTodo() {
		    // 1. Intentamos traer lo que haya en la base de datos local (capturados)
		    List<PokemonDTO> listaLocal = pokemonService.getAll();
		    
		    // 2. Si la base de datos está vacía (Modo Administrador), cargamos los datos de la API
		    if (listaLocal.isEmpty()) {
		        List<PokemonDTO> listaAdmin = new ArrayList<>();
		        
		        // Ejecutamos la carga de datos de tu Handler (los 151 pokémon)
		        if (PokemonHTTPRequestHandler.getPokedexDatos().isEmpty()) {
		            PokemonHTTPRequestHandler.cargarPokedex();
		        }

		        for (InformacionPokemonDTO info : PokemonHTTPRequestHandler.getPokedexDatos()) {
		            PokemonDTO dto = new PokemonDTO();
		            dto.setPokeApiId(info.getId());
		            dto.setApodo(info.getNombre()); 
		            
		            // Extraemos el primer tipo del Pokémon (veneno, fuego, agua, etc.)
		            if (info.getListaTipos() != null && !info.getListaTipos().isEmpty()) {
		                String tipo = info.getListaTipos().get(0).getInformacionTipo().getNombreTipo();
		                dto.setEstado(tipo); // Guardamos el TIPO en el campo estado para Angular
		            }
		            
		            listaAdmin.add(dto);
		        }
		        // DEVOLVEMOS LA LISTA DE LA API
		        return new ResponseEntity<>(listaAdmin, HttpStatus.OK);
		    }
		    
		    // 3. SI HAY DATOS LOCALES, DEVOLVEMOS LOS LOCALES
		    return new ResponseEntity<>(listaLocal, HttpStatus.OK);
		}
		/**
		 * Actualiza las estadísticas de un Pokémon (usado tras una batalla o nivel subido).
		 */
	/**
	 * Actualiza las estadísticas, ataques o el estado de un Pokémon existente por su ID.
	 * <p>
	 * Este método se invoca comúnmente tras la finalización de batallas, cambios en la salud,
	 * alteraciones de estado o subidas de nivel del Pokémon.
	 * </p>
	 * 
	 * @param id                   El identificador único del Pokémon almacenado en la base de datos local.
	 * @param apodo                El apodo modificado o actual del Pokémon.
	 * @param nivel                El nuevo nivel alcanzado por el Pokémon.
	 * @param experienciaAcumulada La experiencia recalculada.
	 * @param saludActual          La salud actual resultante.
	 * @param saludMaxima          La salud máxima modificada o actual.
	 * @param nombreAtaque1        Nombre del primer ataque equipado.
	 * @param nombreAtaque2        Nombre del segundo ataque equipado.
	 * @param nombreAtaque3        Nombre del tercer ataque equipado.
	 * @param nombreAtaque4        Nombre del cuarto ataque equipado.
	 * @param estado               El nuevo estado físico o de combate del Pokémon.
	 * @return Un {@link ResponseEntity} con la confirmación de la actualización y estado {@link HttpStatus#ACCEPTED} (202);
	 *          estado {@link HttpStatus#BAD_REQUEST} (400) si el ID no es válido o hay fallas en la sintaxis;
	 *          o {@link HttpStatus#INTERNAL_SERVER_ERROR} (500) ante fallos imprevistos del servidor.
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
	 * Libera o elimina de forma permanente un Pokémon del sistema mediante su ID local.
	 * 
	 * @param id El identificador único del Pokémon que se va a eliminar.
	 * @return Un {@link ResponseEntity} con un texto descriptivo del proceso y estado {@link HttpStatus#ACCEPTED} (202)
	 *          en caso de éxito, o {@link HttpStatus#NOT_FOUND} (404) si el ID no corresponde a ningún registro.
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
	 * Realiza la búsqueda de todos los Pokémon que pertenecen a un entrenador específico.
	 * 
	 * @param idUsuarioPropietario El identificador único del usuario dueño del equipo.
	 * @return Un {@link ResponseEntity} con la lista de {@link PokemonDTO} filtrada y estado {@link HttpStatus#OK} (200),
	 *          o una respuesta sin cuerpo y estado {@link HttpStatus#NO_CONTENT} (204) si el usuario no posee Pokémon.
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
	 * Busca Pokémon registrados en el sistema que coincidan con un apodo específico.
	 * 
	 * @param apodo El sobrenombre del Pokémon bajo el cual realizar el filtro de búsqueda.
	 * @return Un {@link ResponseEntity} con la lista de coincidencias encontradas y estado {@link HttpStatus#OK} (200),
	 *          o estado {@link HttpStatus#NO_CONTENT} (204) si la lista está vacía.
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
	 * Obtiene la URL de la imagen (sprite) frontal por defecto de un Pokémon.
	 * <p>
	 * Busca el Pokémon localmente, extrae su identificador externo de la PokeAPI y consume 
	 * el manejador de peticiones HTTP para extraer el recurso gráfico de frente.
	 * </p>
	 * 
	 * @param id El identificador único del Pokémon en la base de datos local.
	 * @return Un {@link ResponseEntity} con el {@link SpriteItemDTO} que contiene la URL de la imagen frontal y estado {@link HttpStatus#OK} (200);
	 *          {@link HttpStatus#NOT_FOUND} (404) si el Pokémon no existe localmente; u {@link HttpStatus#NO_CONTENT} (204) 
	 *          si la API externa no posee la imagen.
	 */
	@GetMapping("/{id}/sprites/front")
	public ResponseEntity<SpriteItemDTO> getSpriteFrente(@PathVariable Long id) {
		PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
		if (pokemonLocal == null) return ResponseEntity.notFound().build();

		InformacionPokemonDTO detalleAPI = PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(pokemonLocal.getPokeApiId()));

		if (detalleAPI != null && detalleAPI.getImagenes() != null) {
			return ResponseEntity.ok(detalleAPI.getImagenes().getFrontDefault()); 
		}
		
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Obtiene la URL de la imagen (sprite) posterior o trasera por defecto de un Pokémon.
	 * <p>
	 * Busca el Pokémon localmente, localiza su ID de PokeAPI y utiliza el manejador de peticiones 
	 * externas para extraer el recurso gráfico de espalda.
	 * </p>
	 * 
	 * @param id El identificador único del Pokémon en la base de datos local.
	 * @return Un {@link ResponseEntity} con el {@link SpriteItemDTO} que aloja la URL de la imagen de espalda y estado {@link HttpStatus#OK} (200);
	 *          {@link HttpStatus#NOT_FOUND} (404) si el registro local no existe; u {@link HttpStatus#NO_CONTENT} (204) 
	 *          si no se halla contenido multimedia en la API externa.
	 */
	@GetMapping("/{id}/sprites/back")
	public ResponseEntity<SpriteItemDTO> getSpriteAtras(@PathVariable Long id) {
		PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
		if (pokemonLocal == null) return ResponseEntity.notFound().build();

		InformacionPokemonDTO detalleAPI = PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(pokemonLocal.getPokeApiId()));

		if (detalleAPI != null && detalleAPI.getImagenes() != null) {
			return ResponseEntity.ok(detalleAPI.getImagenes().getBackDefault()); 
		}
		
		return ResponseEntity.noContent().build();
	}
	

	/**
	 * Obtiene la URL del archivo de audio correspondiente al grito del Pokémon.
	 * <p>
	 * Construye dinámicamente la ruta de acceso al recurso multimedia oficial de audio (.ogg) 
	 * basándose exclusivamente en el mapeo del {@code pokeApiId} del Pokémon local.
	 * </p>
	 * 
	 * @param id El identificador único del Pokémon en el sistema local.
	 * @return Un {@link ResponseEntity} que envuelve el {@link GritoPokemonDTO} con la URL del archivo de audio y estado {@link HttpStatus#OK} (200),
	 *          o un estado {@link HttpStatus#NOT_FOUND} (404) si el Pokémon local especificado no existe.
	 */
	@GetMapping("/{id}/grito")
	public ResponseEntity<GritoPokemonDTO> getGrito(@PathVariable Long id) {
		PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
		if (pokemonLocal == null) return ResponseEntity.notFound().build();

		GritoPokemonDTO grito = new GritoPokemonDTO();
		grito.setGritoPokemon("https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/legacy/" 
								+ pokemonLocal.getPokeApiId() + ".ogg");
		
		return ResponseEntity.ok(grito);
	}

	/**
	 * Recupera la descripción literaria e histórica de un Pokémon traducida al idioma solicitado.
	 * <p>
	 * El proceso realiza los siguientes pasos de integración:
	 * 1. Recupera la especie del Pokémon desde la PokeAPI.
	 * 2. Extrae el texto descriptivo base en inglés ("en").
	 * 3. Consume el microservicio de PopCat a través del manejador para traducir dicho texto al idioma destino.
	 * </p>
	 * 
	 * @param id     El identificador único del Pokémon local.
	 * @param idioma El código abreviado del idioma de destino para la traducción (ej. "es", "fr").
	 * @return Un {@link ResponseEntity} que contiene la cadena del texto histórico completamente traducida y estado {@link HttpStatus#OK} (200),
	 *          o estado {@link HttpStatus#NOT_FOUND} (404) si el Pokémon no se encuentra registrado de forma local.
	 */
	@GetMapping("/{id}/historia")
	public ResponseEntity<String> obtenerHistoriaTraducida(@PathVariable Long id, @RequestParam String idioma) {
		PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
		if (pokemonLocal == null) return ResponseEntity.notFound().build();

		EspeciePokemonDTO especie = PokemonHTTPRequestHandler.obtenerEspeciePokemon(pokemonLocal.getPokeApiId());
		
		String textoBase = PokemonHTTPRequestHandler.extraerTextoPorIdioma((ArrayList<DescripcionDTO>) especie.getListaDescripciones(), "en");
		
		String textoTraducido = PokemonHTTPRequestHandler.traducirTexto(textoBase, idioma);
		
		return ResponseEntity.ok(textoTraducido);
	}

	/**
	 * Método auxiliar interno utilizado para realizar búsquedas secuenciales en la lista general de Pokémon.
	 * <p>
	 * Filtra el flujo de datos del servicio buscando una coincidencia exacta con el ID primario.
	 * </p>
	 * 
	 * @param id El identificador único del Pokémon buscado.
	 * @return El objeto {@link PokemonDTO} coincidente, o {@code null} si ningún elemento coincide con el ID provisto.
	 */
	private PokemonDTO obtenerPokemonLocal(Long id) {
		return pokemonService.getAll().stream()
				.filter(p -> p.getId() == id)
				.findFirst()
				.orElse(null);
	}
}