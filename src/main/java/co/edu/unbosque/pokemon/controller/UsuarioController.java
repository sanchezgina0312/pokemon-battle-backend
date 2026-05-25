package co.edu.unbosque.pokemon.controller;

import co.edu.unbosque.pokemon.dto.UsuarioDTO;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.exception.CorreoInvalidoException;
import co.edu.unbosque.pokemon.service.PokemonHTTPRequestHandler;
import co.edu.unbosque.pokemon.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador encargado de la gestión de usuarios del sistema Pokémon.
 * 
 * Permite crear, actualizar, eliminar, consultar usuarios, así como
 * autenticación básica, traducción de texto y actualización de atributos
 * del usuario autenticado.
 */
@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuario", description = "Controlador para la gestión de entrenadores y administradores del juego utilizando DTOs")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Obtiene todos los usuarios registrados.
     */
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna la lista completa de usuarios registrados en el sistema.")
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<UsuarioDTO>> mostrarTodo() {
        List<UsuarioDTO> lista = usuarioService.getAll();
        return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
    }

    /**
     * Busca usuarios por nombre.
     */
    @Operation(summary = "Buscar usuario por nombre", description = "Filtra y retorna los usuarios cuyo nombre coincida con el parámetro.")
    @GetMapping("/buscarpornombre")
    public ResponseEntity<List<UsuarioDTO>> buscarPorNombre(
            @RequestParam String nombre) {

        List<UsuarioDTO> lista = usuarioService.findByNombre(nombre);

        return lista.isEmpty()
                ? new ResponseEntity<>(lista, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
    }

    /**
     * Busca usuarios por correo electrónico.
     */
    @Operation(summary = "Buscar usuario por correo", description = "Retorna el usuario asociado al correo electrónico ingresado.")
    @GetMapping("/buscarporcorreo")
    public ResponseEntity<List<UsuarioDTO>> buscarPorCorreo(
            @RequestParam String correo) {

        List<UsuarioDTO> lista = usuarioService.findByCorreo(correo);

        return lista.isEmpty()
                ? new ResponseEntity<>(lista, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
    }

    /**
     * Crea un nuevo usuario en el sistema.
     */
    @Operation(summary = "Crear nuevo usuario", description = "Registra un nuevo entrenador o administrador.")
    @PostMapping("/crear")
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            usuarioService.create(usuarioDTO);
            return new ResponseEntity<>("Usuario creado con éxito", HttpStatus.CREATED);

        } catch (CorreoInvalidoException e) {
            return new ResponseEntity<>(
                    "El correo ingresado ya se encuentra registrado, por favor intente con uno diferente",
                    HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Error al crear usuario: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Actualiza la información de un usuario existente.
     */
    @Operation(summary = "Actualizar usuario existente", description = "Modifica los datos de un usuario existente.")
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            int status = usuarioService.updateById(usuarioDTO.getId(), usuarioDTO);

            return (status == 0)
                    ? new ResponseEntity<>("Usuario actualizado exitosamente", HttpStatus.ACCEPTED)
                    : new ResponseEntity<>("Usuario no encontrado o error al actualizar", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Error al actualizar usuario: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Elimina un usuario por su ID.
     */
    @Operation(summary = "Eliminar usuario por ID", description = "Borra permanentemente un usuario usando su ID.")
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarUsuario(@RequestParam Long id) {
        int status = usuarioService.deleteById(id);

        return (status == 0)
                ? new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.ACCEPTED)
                : new ResponseEntity<>("Error al eliminar, usuario no encontrado", HttpStatus.NOT_FOUND);
    }

    /**
     * Realiza login básico de usuario.
     */
    @Operation(summary = "Login de usuario", description = "Valida correo y contraseña usando BCrypt.")
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String correo,
            @RequestParam String contrasenia) {

        int resultado = usuarioService.login(correo, contrasenia);

        return switch (resultado) {
            case 0 -> new ResponseEntity<>("Login exitoso", HttpStatus.OK);
            case 2 -> new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            default -> new ResponseEntity<>("Correo o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
        };
    }

    /**
     * Traduce un texto a otro idioma usando servicio externo.
     */
    @GetMapping("/traducir")
    public ResponseEntity<String> traducirTexto(@RequestParam String texto, @RequestParam String idioma) {
        String resultado = PokemonHTTPRequestHandler.traducirTexto(texto, idioma);
        return new ResponseEntity<>(resultado, HttpStatus.OK);
    }

    /**
     * Actualiza el género del usuario autenticado.
     */
    @PutMapping("/genero")
    public ResponseEntity<String> actualizarGenero(
            @RequestParam String genero,
            Authentication authentication) {

        Usuario usuarioAutenticado = (Usuario) authentication.getPrincipal();
        Long idSeguro = usuarioAutenticado.getId();

        int status = usuarioService.actualizarGenero(idSeguro, genero);

        return (status == 0)
                ? new ResponseEntity<>("Personaje guardado exitosamente", HttpStatus.ACCEPTED)
                : new ResponseEntity<>("Error al guardar personaje", HttpStatus.NOT_FOUND);
    }
}