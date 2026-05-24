package co.edu.unbosque.pokemon.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.unbosque.pokemon.dto.*;
import co.edu.unbosque.pokemon.exception.IdInvalidoException;
import co.edu.unbosque.pokemon.service.PokemonHTTPRequestHandler;
import co.edu.unbosque.pokemon.service.PokemonService;

/**
 * Controlador encargado de gestionar todas las operaciones relacionadas con Pokémon.
 * 
 * Incluye funcionalidades de captura, consulta, actualización, eliminación,
 * integración con la PokeAPI, gestión de sprites, gritos, historia y selección de starter.
 */
@RestController
@RequestMapping("/pokemon")
@CrossOrigin(origins = { "http://localhost:8080/", "http://localhost:8081", "http://localhost:4200" })
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    public PokemonController() {
    }

    /**
     * Permite capturar (crear) un nuevo Pokémon en el sistema.
     *
     * @param nuevoPokemon datos del Pokémon a registrar
     * @return mensaje de éxito o error
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
            return new ResponseEntity<>("Error interno al capturar: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Obtiene todos los Pokémon registrados o, si no hay, los carga desde la PokeAPI.
     *
     * @return lista de Pokémon
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
     * Actualiza completamente las estadísticas de un Pokémon por su ID.
     *
     * @param id identificador del Pokémon
     * @return mensaje de estado
     */
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarPokemon(
            @RequestParam Long id,
            @RequestParam String apodo,
            @RequestParam int nivel,
            @RequestParam int experienciaAcumulada,
            @RequestParam int saludActual,
            @RequestParam int saludMaxima,
            @RequestParam String nombreAtaque1,
            @RequestParam String nombreAtaque2,
            @RequestParam String nombreAtaque3,
            @RequestParam String nombreAtaque4,
            @RequestParam String estado) {

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
            }
            return new ResponseEntity<>("No se pudo actualizar el Pokémon.", HttpStatus.BAD_REQUEST);

        } catch (IdInvalidoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina (libera) un Pokémon del sistema.
     *
     * @param id identificador del Pokémon
     * @return estado de la operación
     */
    @DeleteMapping("/liberar")
    public ResponseEntity<String> eliminarPokemon(@RequestParam Long id) {
        try {
            int status = pokemonService.deleteById(id);
            if (status == 0) {
                return new ResponseEntity<>("El Pokémon ha sido liberado.", HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>("ID de Pokémon no encontrado.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Busca Pokémon por entrenador.
     */
    @GetMapping("/buscarporentrenador")
    public ResponseEntity<List<PokemonDTO>> buscarPorEntrenador(@RequestParam Long idUsuarioPropietario) {
        List<PokemonDTO> lista = pokemonService.findByPropietario(idUsuarioPropietario);
        return lista.isEmpty()
                ? new ResponseEntity<>(lista, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(lista, HttpStatus.OK);
    }

    /**
     * Busca Pokémon por apodo.
     */
    @GetMapping("/buscarporapodo")
    public ResponseEntity<List<PokemonDTO>> buscarPorApodo(@RequestParam String apodo) {
        List<PokemonDTO> lista = pokemonService.findByApodo(apodo);
        return lista.isEmpty()
                ? new ResponseEntity<>(lista, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(lista, HttpStatus.OK);
    }

    /**
     * Busca un Pokémon por ID.
     */
    @GetMapping("/buscarporid")
    public ResponseEntity<PokemonDTO> buscarPorId(@RequestParam Long id) {
        PokemonDTO p = obtenerPokemonLocal(id);
        return (p != null)
                ? new ResponseEntity<>(p, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Obtiene sprite frontal del Pokémon.
     */
    @GetMapping("/{id}/sprites/front")
    public ResponseEntity<SpriteItemDTO> getSpriteFrente(@PathVariable Long id) {
        PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
        if (pokemonLocal == null) return ResponseEntity.notFound().build();

        InformacionPokemonDTO detalleAPI =
                PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(pokemonLocal.getPokeApiId()));

        return (detalleAPI != null && detalleAPI.getImagenes() != null)
                ? ResponseEntity.ok(detalleAPI.getImagenes().getFrontDefault())
                : ResponseEntity.noContent().build();
    }

    /**
     * Obtiene sprite trasero del Pokémon.
     */
    @GetMapping("/{id}/sprites/back")
    public ResponseEntity<SpriteItemDTO> getSpriteAtras(@PathVariable Long id) {
        PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
        if (pokemonLocal == null) return ResponseEntity.notFound().build();

        InformacionPokemonDTO detalleAPI =
                PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(pokemonLocal.getPokeApiId()));

        return (detalleAPI != null && detalleAPI.getImagenes() != null)
                ? ResponseEntity.ok(detalleAPI.getImagenes().getBackDefault())
                : ResponseEntity.noContent().build();
    }

    /**
     * Obtiene el sonido (grito) del Pokémon.
     */
    @GetMapping("/{id}/grito")
    public ResponseEntity<GritoPokemonDTO> getGrito(@PathVariable Long id) {
        PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
        if (pokemonLocal == null) return ResponseEntity.notFound().build();

        GritoPokemonDTO grito = new GritoPokemonDTO();
        grito.setGritoPokemon(
                "https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/legacy/"
                        + pokemonLocal.getPokeApiId() + ".ogg"
        );

        return ResponseEntity.ok(grito);
    }

    /**
     * Obtiene historia del Pokémon traducida.
     */
    @GetMapping("/{id}/historia")
    public ResponseEntity<String> obtenerHistoriaTraducida(@PathVariable Long id, @RequestParam String idioma) {
        PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
        if (pokemonLocal == null) return ResponseEntity.notFound().build();

        EspeciePokemonDTO especie =
                PokemonHTTPRequestHandler.obtenerEspeciePokemon(pokemonLocal.getPokeApiId());

        String textoBase =
                PokemonHTTPRequestHandler.extraerTextoPorIdioma(
                        (ArrayList<DescripcionDTO>) especie.getListaDescripciones(), "en");

        String textoTraducido =
                PokemonHTTPRequestHandler.traducirTexto(textoBase, idioma);

        return ResponseEntity.ok(textoTraducido);
    }

    /**
     * Obtiene Pokémon salvaje desde la API externa.
     */
    @GetMapping("/salvaje/{id}")
    public ResponseEntity<InformacionPokemonDTO> obtenerPokemonSalvaje(@PathVariable String id) {
        InformacionPokemonDTO detalle = PokemonHTTPRequestHandler.obtenerDetallePokemon(id);
        return (detalle != null)
                ? new ResponseEntity<>(detalle, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Método auxiliar para obtener un Pokémon local por ID.
     */
    private PokemonDTO obtenerPokemonLocal(Long id) {
        return pokemonService.getAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
}