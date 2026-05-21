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
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.exception.IdInvalidoException;
import co.edu.unbosque.pokemon.service.PokemonHTTPRequestHandler;
import co.edu.unbosque.pokemon.service.PokemonService;

@RestController
@RequestMapping("/pokemon")
@CrossOrigin(origins = {"http://localhost:8080/", "http://localhost:8081", "http://localhost:4200"})
public class PokemonController {

    @Autowired
    private PokemonService pokemonService;

    public PokemonController() {
    }

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
        if (pokemonLocal == null) return ResponseEntity.notFound().build();

        InformacionPokemonDTO detalleAPI = PokemonHTTPRequestHandler.obtenerDetallePokemon(String.valueOf(pokemonLocal.getPokeApiId()));

        if (detalleAPI != null && detalleAPI.getImagenes() != null) {
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
            return ResponseEntity.ok(detalleAPI.getImagenes().getBackDefault()); 
        }
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}/grito")
    public ResponseEntity<GritoPokemonDTO> getGrito(@PathVariable Long id) {
        PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
        if (pokemonLocal == null) return ResponseEntity.notFound().build();

        GritoPokemonDTO grito = new GritoPokemonDTO();
        grito.setGritoPokemon("https://raw.githubusercontent.com/PokeAPI/cries/main/cries/pokemon/legacy/" 
                                + pokemonLocal.getPokeApiId() + ".ogg");
        return ResponseEntity.ok(grito);
    }

    @GetMapping("/{id}/historia")
    public ResponseEntity<String> obtenerHistoriaTraducida(@PathVariable Long id, @RequestParam String idioma) {
        PokemonDTO pokemonLocal = obtenerPokemonLocal(id);
        if (pokemonLocal == null) return ResponseEntity.notFound().build();

        EspeciePokemonDTO especie = PokemonHTTPRequestHandler.obtenerEspeciePokemon(pokemonLocal.getPokeApiId());
        String textoBase = PokemonHTTPRequestHandler.extraerTextoPorIdioma((ArrayList<DescripcionDTO>) especie.getListaDescripciones(), "en");
        String textoTraducido = PokemonHTTPRequestHandler.traducirTexto(textoBase, idioma);
        return ResponseEntity.ok(textoTraducido);
    }

    private PokemonDTO obtenerPokemonLocal(Long id) {
        return pokemonService.getAll().stream()
        		.filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
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
    
    
    @PostMapping("/starter")
    public ResponseEntity<String> elegirStarter(
            @RequestParam String tipo,
            Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();
        Long idUsuario = usuario.getId();
        PokemonDTO starter = new PokemonDTO();
        switch(tipo.toLowerCase()) {

            case "planta":
                starter.setPokeApiId(1);
                starter.setApodo("Bulbasaur");
                break;

            case "fuego":
                starter.setPokeApiId(4);
                starter.setApodo("Charmander");
                break;

            case "agua":
                starter.setPokeApiId(7);
                starter.setApodo("Squirtle");
                break;
            default:
                return new ResponseEntity<>("Starter inválido", HttpStatus.BAD_REQUEST);
        }

        starter.setNivel(5);
        starter.setExperienciaAcumulada(0);
        starter.setSaludActual(100);
        starter.setSaludMaxima(100);
        starter.setNombreAtaque1("Placaje");
        starter.setNombreAtaque2("Gruñido");
        starter.setNombreAtaque3("");
        starter.setNombreAtaque4("");
        starter.setEstado("OK");
        starter.setIdUsuarioPropietario(idUsuario);
        pokemonService.create(starter);
        return new ResponseEntity<>("Starter asignado", HttpStatus.CREATED);
    }
    @GetMapping("/admin/todos")
    public ResponseEntity<List<PokemonDTO>> mostrarTodoAdmin() {
            if (PokemonHTTPRequestHandler.getPokedexDatos().isEmpty()) {
            PokemonHTTPRequestHandler.cargarPokedex();
        }

        List<PokemonDTO> listaAdmin = new ArrayList<>();

        for (InformacionPokemonDTO info : PokemonHTTPRequestHandler.getPokedexDatos()) {
            PokemonDTO dto = new PokemonDTO();
            dto.setPokeApiId(info.getId());
            
            dto.setApodo(info.getNombre().toUpperCase()); 
            
            if (info.getListaTipos() != null && !info.getListaTipos().isEmpty()) {
                String tipo = info.getListaTipos().get(0).getInformacionTipo().getNombreTipo();
                dto.setEstado(tipo); 
            } else {
                dto.setEstado("NORMAL");
            }
            
            listaAdmin.add(dto);
        }
        return new ResponseEntity<>(listaAdmin, HttpStatus.OK);
    }
}