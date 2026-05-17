package co.edu.unbosque.pokemon.controller;

import co.edu.unbosque.pokemon.dto.UsuarioDTO;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;  // ← Spring, NO Swagger
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081" })
@Tag(name = "Usuario", description = "Controlador para la gestión de entrenadores y administradores del juego utilizando DTOs")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Obtener todos los usuarios", description = "Retorna la lista completa de usuarios registrados en el sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Lista de usuarios aceptada y retornada") })
    @GetMapping("/mostrartodo")
    public ResponseEntity<List<UsuarioDTO>> mostrarTodo() {
        List<UsuarioDTO> lista = usuarioService.getAll();
        return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Buscar usuario por nombre", description = "Filtra y retorna los usuarios cuyo nombre coincida con el parámetro.")
    @GetMapping("/buscarpornombre")
    public ResponseEntity<List<UsuarioDTO>> buscarPorNombre(
            @Parameter(description = "Nombre o nickname del usuario", required = true, example = "AshKetchum")
            @RequestParam String nombre) {
        List<UsuarioDTO> lista = usuarioService.findByNombre(nombre);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Buscar usuario por correo", description = "Retorna el usuario asociado al correo electrónico ingresado.")
    @GetMapping("/buscarporcorreo")
    public ResponseEntity<List<UsuarioDTO>> buscarPorCorreo(
            @Parameter(description = "Correo electrónico del usuario", required = true, example = "ash@paleta.com")
            @RequestParam String correo) {
        List<UsuarioDTO> lista = usuarioService.findByCorreo(correo);
        if (!lista.isEmpty()) {
            return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Crear nuevo usuario", description = "Registra un nuevo entrenador o administrador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Usuario creado con éxito"))),
            @ApiResponse(responseCode = "400", description = "Error en los datos o validación fallida") })
    @PostMapping("/crear")
    public ResponseEntity<String> crearUsuario(
            @RequestBody UsuarioDTO usuarioDTO) {  // ← @RequestBody de Spring
        try {
            usuarioService.create(usuarioDTO);
            return new ResponseEntity<>("Usuario creado con éxito", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear usuario: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Actualizar usuario existente", description = "Modifica los datos de un usuario existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Usuario actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado") })
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarUsuario(
            @RequestBody UsuarioDTO usuarioDTO) {  // ← @RequestBody de Spring
        int status = usuarioService.updateById(usuarioDTO.getId(), usuarioDTO);
        if (status == 0) {
            return new ResponseEntity<>("Usuario actualizado exitosamente", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Usuario no encontrado o error al actualizar", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar usuario por ID", description = "Borra permanentemente un usuario usando su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Usuario eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado") })
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarUsuario(
            @Parameter(description = "ID único del usuario a eliminar", required = true, example = "1")
            @RequestParam Long id) {
        int status = usuarioService.deleteById(id);
        if (status == 0) {
            return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Error al eliminar, usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Inicio de sesión", description = "Valida las credenciales de un entrenador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas") })
    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String nombre,
            @RequestParam String contrasenia) {
        int resultado = usuarioService.validateCredentials(nombre, contrasenia);
        if (resultado == 0) {
            return new ResponseEntity<>("Login exitoso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Credenciales inválidas", HttpStatus.UNAUTHORIZED);
        }
    }
}