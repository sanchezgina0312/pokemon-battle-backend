package co.edu.unbosque.pokemon.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import co.edu.unbosque.pokemon.dto.TipoPokemonDTO;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.exception.IdInvalidoException;
import co.edu.unbosque.pokemon.service.PokemonHTTPRequestHandler;
import co.edu.unbosque.pokemon.service.PokemonService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/pokemon")
@CrossOrigin(origins = { "http://localhost:8080/", "http://localhost:8081", "http://localhost:4200" })
public class PokemonController {

	@Autowired
	private PokemonService pokemonService;

	public PokemonController() {
	}

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

	@GetMapping("/buscarporentrenador")
	public ResponseEntity<List<PokemonDTO>> buscarPorEntrenador(@RequestParam Long idUsuarioPropietario) {
		List<PokemonDTO> lista = pokemonService.findByPropietario(idUsuarioPropietario);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

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
		if (pokemonLocal == null)
			return ResponseEntity.notFound().build();

		InformacionPokemonDTO detalleAPI = PokemonHTTPRequestHandler
				.obtenerDetallePokemon(String.valueOf(pokemonLocal.getPokeApiId()));

		if (detalleAPI != null && detalleAPI.getImagenes() != null) {
			return ResponseEntity.ok(detalleAPI.getImagenes().getFrontDefault());
		}
		return ResponseEntity.noContent().build();
	}

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

	@GetMapping("/{id}/historia")
	public ResponseEntity<String> obtenerHistoriaTraducida(@PathVariable Long id, @RequestParam String idioma) {
		PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
		if (pokemonLocal == null)
			return ResponseEntity.notFound().build();

		EspeciePokemonDTO especie = PokemonHTTPRequestHandler.obtenerEspeciePokemon(pokemonLocal.getPokeApiId());
		String textoBase = PokemonHTTPRequestHandler
				.extraerTextoPorIdioma((ArrayList<DescripcionDTO>) especie.getListaDescripciones(), "en");
		String textoTraducido = PokemonHTTPRequestHandler.traducirTexto(textoBase, idioma);
		return ResponseEntity.ok(textoTraducido);
	}

	private PokemonDTO obtenerPokemonLocal(Long id) {
		return pokemonService.getAll().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
	}

	@GetMapping("/buscarporid")
	public ResponseEntity<PokemonDTO> buscarPorId(@RequestParam Long id) {
		System.out.println("DEBUG: Petición recibida para buscar ID: " + id);
		PokemonDTO p = obtenerPokemonLocal(id);
		if (p != null) {
			return new ResponseEntity<>(p, HttpStatus.OK);
		} else {
			System.out.println("DEBUG: No se encontró el Pokémon con ID: " + id);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/salvaje/{id}")
	public ResponseEntity<InformacionPokemonDTO> obtenerPokemonSalvaje(@PathVariable String id) {
		InformacionPokemonDTO detalle = PokemonHTTPRequestHandler.obtenerDetallePokemon(id);
		if (detalle != null) {
			return new ResponseEntity<>(detalle, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	private List<PokemonDTO> listaAdminBase() {
		System.out.println("Iniciando carga de listaAdminBase...");
		List<PokemonDTO> listaAdmin = new ArrayList<>();

		var datosMemoria = co.edu.unbosque.pokemon.service.PokemonHTTPRequestHandler.getPokedexDatos();

		if (datosMemoria == null || datosMemoria.isEmpty()) {
			System.out.println("Datos en memoria vacíos. Forzando carga de Pokedex...");
			co.edu.unbosque.pokemon.service.PokemonHTTPRequestHandler.cargarPokedex();
			datosMemoria = co.edu.unbosque.pokemon.service.PokemonHTTPRequestHandler.getPokedexDatos();
		}

		if (datosMemoria != null && !datosMemoria.isEmpty()) {
			System.out.println("DEBUG: Se encontraron " + datosMemoria.size() + " especies. Procesando...");

			for (var info : datosMemoria) {
				PokemonDTO dto = new PokemonDTO();
				dto.setPokeApiId(info.getId());
				dto.setApodo(info.getNombre().toUpperCase());

				// 1. Mapeo de Tipos
				List<String> tipos = new ArrayList<>();
				if (info.getListaTipos() != null) {
					for (var t : info.getListaTipos()) {
						if (t.getInformacionTipo() != null) {
							tipos.add(t.getInformacionTipo().getNombreTipo().toUpperCase());
						}
					}
				}
				dto.setTipos(tipos);

				List<String> ataques = PokemonHTTPRequestHandler.extraerCuatroPrimerosAtaques(info.getListaAtaques());

				dto.setNombreAtaque1(ataques.size() > 0 ? ataques.get(0) : "---");
				dto.setNombreAtaque2(ataques.size() > 1 ? ataques.get(1) : "---");
				dto.setNombreAtaque3(ataques.size() > 2 ? ataques.get(2) : "---");
				dto.setNombreAtaque4(ataques.size() > 3 ? ataques.get(3) : "---");

				dto.setSaludMaxima(PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "hp"));
				dto.setAtaque(PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "attack"));
				dto.setDefensa(PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "defense"));
				dto.setVelocidad(PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "speed"));
				System.out.println("DEBUG: Procesando " + dto.getApodo() + " - Atk: " + dto.getAtaque() + " Def: "
						+ dto.getDefensa());
				listaAdmin.add(dto);
			}
		} else {
			System.out.println("ERROR CRÍTICO: La lista de datosMemoria sigue vacía después de cargar.");
		}

		System.out.println("Se ha generado una lista con " + listaAdmin.size() + " elementos.");
		return listaAdmin;
	}

	@GetMapping("/admin/todos")
	public ResponseEntity<List<PokemonDTO>> mostrarTodoAdmin() {
		return new ResponseEntity<>(listaAdminBase(), HttpStatus.OK);
	}

	@GetMapping("/admin/especie/{pokeApiId}")
	public ResponseEntity<PokemonDTO> obtenerEspecieAdmin(@PathVariable Integer pokeApiId) {
		PokemonDTO dto = pokemonService.obtenerEspecieParaAdmin(pokeApiId);
		return (dto != null) ? new ResponseEntity<>(dto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Métodos admin para detalles sin necesidad de búsqueda en BD local
	@GetMapping("/admin/especie/{pokeApiId}/sprites/front")
	public ResponseEntity<SpriteItemDTO> getSpriteFrenteAdmin(@PathVariable Integer pokeApiId) {
		InformacionPokemonDTO detalle = PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(pokeApiId));
		return (detalle != null && detalle.getImagenes() != null)
				? ResponseEntity.ok(detalle.getImagenes().getFrontDefault())
				: ResponseEntity.notFound().build();
	}

	@GetMapping("/admin/especie/{pokeApiId}/grito")
	public ResponseEntity<GritoPokemonDTO> getGritoAdmin(@PathVariable Integer pokeApiId) {
		GritoPokemonDTO grito = new GritoPokemonDTO();
		grito.setGritoPokemon(
				"https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/legacy/" + pokeApiId + ".ogg");
		return ResponseEntity.ok(grito);
	}

	@PostMapping("/starter")
	public ResponseEntity<String> elegirStarter(@RequestParam String tipo, Authentication authentication) {

		Usuario usuario = (Usuario) authentication.getPrincipal();

		int pokeApiId;

		switch (tipo.toLowerCase()) {
		case "planta":
			pokeApiId = 1; // Bulbasaur
			break;

		case "fuego":
			pokeApiId = 4; // Charmander
			break;

		default:
			pokeApiId = 7; // Squirtle
			break;
		}

		// TRAER DATOS REALES DE LA API
		InformacionPokemonDTO info = PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(pokeApiId));

		if (info == null) {
			return new ResponseEntity<>("No se pudo obtener información del Pokémon", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		PokemonDTO starter = new PokemonDTO();

		starter.setPokeApiId(info.getId());
		starter.setApodo(info.getNombre().toUpperCase());

		starter.setNivel(5);
		starter.setExperienciaAcumulada(0);

		// STATS REALES
		int hp = PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "hp");

		int atk = PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "attack");

		int def = PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "defense");

		int speed = PokemonHTTPRequestHandler.extraerStat(info.getListaEstadisticas(), "speed");

		starter.setSaludActual(hp);
		starter.setSaludMaxima(hp);

		starter.setAtaque(atk);
		starter.setDefensa(def);
		starter.setVelocidad(speed);

		// TIPOS
		List<String> tipos = new ArrayList<>();

		for (TipoPokemonDTO t : info.getListaTipos()) {
			tipos.add(t.getInformacionTipo().getNombreTipo().toUpperCase());
		}

		starter.setTipos(tipos);

		// ATAQUES REALES
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
}