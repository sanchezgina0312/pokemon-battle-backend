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
 * Permite capturar, actualizar estadísticas de batalla y gestionar
 * los equipos de los usuarios.
 */
@RestController
@RequestMapping("/pokemon")
@CrossOrigin(origins = { "http://localhost:8080", "*" })
public class PokemonController {

	@Autowired
	private PokemonService pokemonService;

	public PokemonController() {
	}

	/**
	 * Registra un nuevo Pokémon capturado por un usuario.
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
	 * Retorna todos los Pokémon registrados en la base de datos global.
	 */
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<PokemonDTO>> mostrarTodo() {
		List<PokemonDTO> lista = pokemonService.getAll();
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Actualiza las estadísticas de un Pokémon (usado tras una batalla o nivel subido).
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
	 * Libera un Pokémon (lo elimina de la base de datos).
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
	 * BUSQUEDA CLAVE: Busca todos los Pokémon que pertenecen a un mismo jugador.
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
	 * Busca un Pokémon por su apodo.
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
	
	@GetMapping("/{id}/sprites/front")
	public ResponseEntity<SpriteItemDTO> getSpriteFrente(@PathVariable Long id) {
	    PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
	    if (pokemonLocal == null) return ResponseEntity.notFound().build();

	    InformacionPokemonDTO detalleAPI = PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(pokemonLocal.getPokeApiId()));

	    if (detalleAPI != null && detalleAPI.getImagenes() != null) {
	        // Devuelve la URL de 'frente' envuelta en un SpriteItemDTO
	        return ResponseEntity.ok(detalleAPI.getImagenes().getFrontDefault()); 
	    }
	    
	    return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}/sprites/back")
	public ResponseEntity<SpriteItemDTO> getSpriteAtras(@PathVariable Long id) {
	    PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
	    if (pokemonLocal == null) return ResponseEntity.notFound().build();

	    InformacionPokemonDTO detalleAPI = PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(pokemonLocal.getPokeApiId()));

	    if (detalleAPI != null && detalleAPI.getImagenes() != null) {
	        // Devuelve la URL de 'espalda' envuelta en un SpriteItemDTO
	        return ResponseEntity.ok(detalleAPI.getImagenes().getBackDefault()); 
	    }
	    
	    return ResponseEntity.noContent().build();
	}
	

    /**
     * Endpoint 2: Obtener el grito del Pokémon.
     * Basado en la lógica de URL de tu manejador.
     */
    @GetMapping("/{id}/grito")
    public ResponseEntity<GritoPokemonDTO> getGrito(@PathVariable Long id) {
        PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
        if (pokemonLocal == null) return ResponseEntity.notFound().build();

        // Construimos el grito usando el PokeApiId almacenado localmente
        GritoPokemonDTO grito = new GritoPokemonDTO();
        grito.setGritoPokemon("https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/legacy/" 
                               + pokemonLocal.getPokeApiId() + ".ogg");
        
        return ResponseEntity.ok(grito);
    }

    /**
     * Endpoint Extra: Traducir información del Pokémon.
     * Utiliza la API de PopCat integrada en tu manejador.
     */
    @GetMapping("/{id}/historia")
    public ResponseEntity<String> obtenerHistoriaTraducida(@PathVariable Long id, @RequestParam String idioma) {
        PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
        if (pokemonLocal == null) return ResponseEntity.notFound().build();

        // 1. Obtener especie de la PokeAPI
        EspeciePokemonDTO especie = PokemonHTTPRequestHandler.obtenerEspeciePokemon(pokemonLocal.getPokeApiId());
        
        // 2. Extraer texto base (por ejemplo en inglés)
        String textoBase = PokemonHTTPRequestHandler.extraerTextoPorIdioma((ArrayList<DescripcionDTO>) especie.getListaDescripciones(), "en");
        
        // 3. Traducir usando tu método de PopCat
        String textoTraducido = PokemonHTTPRequestHandler.traducirTexto(textoBase, idioma);
        
        return ResponseEntity.ok(textoTraducido);
    }

    // Método auxiliar interno para buscar en el service
    private PokemonDTO obtenerPokemonLocal(Long id) {
        return pokemonService.getAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}