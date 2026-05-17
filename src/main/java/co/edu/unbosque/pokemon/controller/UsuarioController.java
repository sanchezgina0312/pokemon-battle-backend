package co.edu.unbosque.pokemon.controller;

import co.edu.unbosque.pokemon.dto.UsuarioDTO;
import co.edu.unbosque.pokemon.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST encargado de exponer los endpoints para la gestión de
 * usuarios.
 * <p>
 * Proporciona operaciones para listar, buscar, crear, actualizar, eliminar y
 * autenticar a los entrenadores y administradores del juego utilizando
 * estructuras basadas en DTOs.
 * </p>
 * * @author Gina Sánchez
 * 
 * @version 1.0
 */
@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = { "http://localhost:8080", "http://localhost:8081" })
@Tag(name = "Usuario", description = "Controlador para la gestión de entrenadores y administradores del juego utilizando DTOs")
public class UsuarioController {

	/**
	 * Servicio encargado de la lógica de negocio de la entidad Usuario.
	 */
	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Obtiene la lista completa de todos los usuarios registrados en el sistema.
	 *
	 * @return un objeto ResponseEntity que contiene la lista de UsuarioDTO y el
	 *         estado HTTP ACCEPTED
	 */
	@Operation(summary = "Obtener todos los usuarios", description = "Retorna la lista completa de usuarios registrados en el sistema.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Lista de usuarios aceptada y retornada") })
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<UsuarioDTO>> mostrarTodo() {
		List<UsuarioDTO> lista = usuarioService.getAll();
		return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
	}

	/**
	 * Busca usuarios filtrando por su nombre o nickname.
	 *
	 * @param nombre el nombre o nickname del usuario a buscar
	 * @return un objeto ResponseEntity con la lista de coincidencias y estado HTTP
	 *         ACCEPTED, o estado NO_CONTENT si la lista está vacía
	 */
	@Operation(summary = "Buscar usuario por nombre", description = "Filtra y retorna los usuarios cuyo nombre coincida con el parámetro.")
	@GetMapping("/buscarpornombre")
	public ResponseEntity<List<UsuarioDTO>> buscarPorNombre(
			@Parameter(description = "Nombre o nickname del usuario", required = true, example = "AshKetchum") @RequestParam String nombre) {
		List<UsuarioDTO> lista = usuarioService.findByNombre(nombre);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca un usuario en el sistema mediante su dirección de correo electrónico.
	 *
	 * @param correo la dirección de correo electrónico del usuario a buscar
	 * @return un objeto ResponseEntity con el usuario encontrado y estado HTTP
	 *         ACCEPTED, o estado NO_CONTENT si no existe
	 */
	@Operation(summary = "Buscar usuario por correo", description = "Retorna el usuario asociado al correo electrónico ingresado.")
	@GetMapping("/buscarporcorreo")
	public ResponseEntity<List<UsuarioDTO>> buscarPorCorreo(
			@Parameter(description = "Correo electrónico del usuario", required = true, example = "ash@paleta.com") @RequestParam String correo) {
		List<UsuarioDTO> lista = usuarioService.findByCorreo(correo);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Registra un nuevo entrenador o administrador a partir de un DTO enviado en el
	 * cuerpo de la petición.
	 *
	 * @param usuarioDTO el objeto que contiene toda la información del nuevo
	 *                   usuario
	 * @return un objeto ResponseEntity con el mensaje de éxito y estado CREATED, o
	 *         un mensaje de error con estado BAD_REQUEST si falla la validación
	 */
	@Operation(summary = "Crear nuevo usuario", description = "Registra un nuevo entrenador o administrador pasando un objeto UsuarioDTO estructurado en el cuerpo de la petición.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuario creado exitosamente", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "Usuario creado con éxito"))),
			@ApiResponse(responseCode = "400", description = "Error en los datos estructurados recibidos o validación fallida") })
	@PostMapping("/crear")
	public ResponseEntity<String> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		try {
			usuarioService.create(usuarioDTO);
			return new ResponseEntity<>("Usuario creado con éxito", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al crear usuario: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Actualiza los datos de un usuario existente mediante el envío del DTO
	 * estructurado en el cuerpo de la petición.
	 *
	 * @param usuarioDTO el objeto que contiene las modificaciones y el
	 *                   identificador único del usuario
	 * @return un objeto ResponseEntity con mensaje de éxito y estado ACCEPTED, o
	 *         mensaje de error con estado NOT_FOUND si el usuario no existe
	 */
	@Operation(summary = "Actualizar usuario existente", description = "Modifica los datos de un usuario enviando el DTO completo con los datos modificados en el cuerpo de la petición.")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Usuario actualizado correctamente"),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado o error en los identificadores") })
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		int status = usuarioService.updateById(usuarioDTO.getId(), usuarioDTO);
		if (status == 0) {
			return new ResponseEntity<>("Usuario actualizado exitosamente", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Usuario no encontrado o error al actualizar", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Elimina de manera permanente un usuario del sistema utilizando su
	 * identificador único.
	 *
	 * @param id el identificador único del usuario a eliminar
	 * @return un objeto ResponseEntity con mensaje de éxito y estado ACCEPTED, o
	 *         mensaje de error con estado NOT_FOUND si no existe el identificador
	 */
	@Operation(summary = "Eliminar usuario por ID", description = "Borra permanentemente un usuario de la base de datos usando su ID único pasado como parámetro.")
	@ApiResponses(value = { @ApiResponse(responseCode = "202", description = "Usuario eliminado correctamente"),
			@ApiResponse(responseCode = "404", description = "Usuario no encontrado") })
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarUsuario(
			@Parameter(description = "ID único del usuario a eliminar", required = true, example = "1") @RequestParam Long id) {
		int status = usuarioService.deleteById(id);
		if (status == 0) {
			return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>("Error al eliminar, usuario no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Procesa el inicio de sesión validando el nombre de usuario y la contraseña
	 * provistos.
	 *
	 * @param nombre      el nombre de usuario o nickname del entrenador
	 * @param contrasenia la contraseña sin encriptar provista en el formulario de
	 *                    login
	 * @return un objeto ResponseEntity con mensaje de éxito y estado OK, o mensaje
	 *         de error con estado UNAUTHORIZED si las credenciales son incorrectas
	 */
	@Operation(summary = "Inicio de sesión", description = "Valida las credenciales de un entrenador mediante su nombre de usuario y contraseña.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Login exitoso"),
			@ApiResponse(responseCode = "401", description = "Credenciales inválidas") })
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestParam String nombre, @RequestParam String contrasenia) {
		int resultado = usuarioService.validateCredentials(nombre, contrasenia);
		if (resultado == 0) {
			return new ResponseEntity<>("Login exitoso", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Credenciales inválidas", HttpStatus.UNAUTHORIZED);
		}
	}
}