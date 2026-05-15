package co.edu.unbosque.pokemon.controller; // Ajusta el paquete según tu proyecto

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

import co.edu.unbosque.pokemon.dto.UsuarioDTO; // Asegúrate de que el DTO exista
import co.edu.unbosque.pokemon.exception.ContraseniaInvalidaException;
import co.edu.unbosque.pokemon.exception.CorreoInvalidoException;
import co.edu.unbosque.pokemon.exception.IdInvalidoException;
import co.edu.unbosque.pokemon.exception.NombreInvalidoException;
import co.edu.unbosque.pokemon.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST que expone los endpoints para la gestión de Usuarios
 * dentro del sistema (jugadores y administradores).
 *
 * <p>
 * Provee operaciones CRUD completas y búsquedas por diferentes criterios. Las
 * respuestas se retornan en formato JSON y los errores son gestionados mediante
 * excepciones personalizadas.
 * </p>
 *
 * @version 1.0
 */
@RestController
@RequestMapping("/pokemon/usuarios")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081"})
@Tag(name = "Usuario", description = "Controlador para la gestión completa de usuarios de pokemon")
public class UsuarioController {

	/**
	 * Servicio de lógica de negocio para la gestión de usuarios.
	 */
	@Autowired
	private UsuarioService usuarioService;

	/**
	 * Constructor por defecto.
	 */
	public UsuarioController() {
	}

	/**
	 * Valida las credenciales del usuario para iniciar sesión.
	 * Se utiliza el correo electrónico como identificador único.
	 *
	 * @param correo      Correo del usuario.
	 * @param contrasenia Contraseña del usuario.
	 * @return El objeto UsuarioDTO si es válido, o un error de autorización.
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestParam String correo, @RequestParam String contrasenia) {
		List<UsuarioDTO> lista = usuarioService.findByCorreo(correo);

		if (lista.isEmpty()) {
			return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
		}

		UsuarioDTO usuario = lista.get(0);

		if (usuario.getContrasenia().equals(contrasenia)) {
			return new ResponseEntity<>(usuario, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Contraseña incorrecta", HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * Crea un nuevo usuario en el sistema.
	 *
	 * <p>
	 * Endpoint: {@code POST /usuario/crear}
	 * </p>
	 *
	 * @param nombre          Nombre o nickname del usuario.
	 * @param contrasenia     Clave de seguridad para el acceso.
	 * @param correo          Dirección de correo electrónico.
	 * @param rol             Rol en el sistema (ej. Administrador, Jugador).
	 * @param idiomaPreferido Idioma seleccionado por el usuario.
	 * @param dinero          Cantidad inicial de dinero (monedas del juego).
	 * @return {@link ResponseEntity} con el estado de la operación.
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crearUsuario(@RequestParam String nombre, @RequestParam String contrasenia,
			@RequestParam String correo, @RequestParam String rol, @RequestParam String idiomaPreferido,
			@RequestParam int dinero) {

		try {
			UsuarioDTO nuevoUsuario = new UsuarioDTO();
			nuevoUsuario.setNombre(nombre);
			nuevoUsuario.setContrasenia(contrasenia);
			nuevoUsuario.setCorreo(correo);
			nuevoUsuario.setRol(rol);
			nuevoUsuario.setIdiomaPreferido(idiomaPreferido);
			nuevoUsuario.setDinero(dinero);

			int status = usuarioService.create(nuevoUsuario);

			if (status == 0) {
				return new ResponseEntity<>("Usuario creado con éxito", HttpStatus.CREATED);
			} else if (status == 1) {
				return new ResponseEntity<>("El correo ya se encuentra registrado", HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<>("Error al crear el Usuario", HttpStatus.BAD_REQUEST);
			}
		} catch (NombreInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (CorreoInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (ContraseniaInvalidaException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retorna la lista completa de usuarios registrados.
	 *
	 * @return Lista de {@link UsuarioDTO} con estado HTTP.
	 */
	@GetMapping("/mostrartodo")
	public ResponseEntity<List<UsuarioDTO>> mostrarTodo() {
		List<UsuarioDTO> usuarios = usuarioService.getAll();
		if (!usuarios.isEmpty()) {
			return new ResponseEntity<>(usuarios, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(usuarios, HttpStatus.NO_CONTENT); // Cambiado a NO_CONTENT para listas vacías
		}
	}

	/**
	 * Elimina un usuario según su ID.
	 *
	 * @param id Identificador único del usuario.
	 * @return Mensaje descriptivo con el estado de la eliminación.
	 */
	@DeleteMapping("/eliminar")
	public ResponseEntity<String> eliminarUsuario(@RequestParam Long id) {
		try {
			int status = usuarioService.deleteById(id);
			if (status == 0) {
				return new ResponseEntity<>("Usuario eliminado correctamente.", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("No se encontró el usuario con el ID ingresado: " + id,
						HttpStatus.NOT_FOUND);
			}
		} catch (IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Actualiza los datos de un usuario identificado por su ID.
	 *
	 * @param id              Identificador del usuario a actualizar.
	 * @param nombre          Nuevo nombre.
	 * @param contrasenia     Nueva contraseña.
	 * @param correo          Nuevo correo electrónico.
	 * @param rol             Nuevo rol en el sistema.
	 * @param idiomaPreferido Nuevo idioma.
	 * @param dinero          Saldo actual.
	 * @return Respuesta con el estado de la actualización.
	 */
	@PutMapping("/actualizar")
	public ResponseEntity<String> actualizarUsuario(@RequestParam Long id, @RequestParam String nombre,
			@RequestParam String contrasenia, @RequestParam String correo, @RequestParam String rol,
			@RequestParam String idiomaPreferido, @RequestParam int dinero) {
		try {
			UsuarioDTO usuarioActualizado = new UsuarioDTO();
			usuarioActualizado.setNombre(nombre);
			usuarioActualizado.setContrasenia(contrasenia);
			usuarioActualizado.setCorreo(correo);
			usuarioActualizado.setRol(rol);
			usuarioActualizado.setIdiomaPreferido(idiomaPreferido);
			usuarioActualizado.setDinero(dinero);

			int status = usuarioService.updateById(id, usuarioActualizado);

			if (status == 0) {
				return new ResponseEntity<>("Usuario actualizado correctamente.", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<>("Error al actualizar el usuario.", HttpStatus.BAD_REQUEST);
			}
		} catch (NombreInvalidoException | CorreoInvalidoException | ContraseniaInvalidaException | IdInvalidoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>("Error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Busca usuarios por nombre o nickname.
	 *
	 * @param nombre Nombre a buscar.
	 * @return Lista de coincidencias encontradas.
	 */
	@GetMapping("/buscarpornombre")
	public ResponseEntity<List<UsuarioDTO>> buscarPorNombre(@RequestParam String nombre) {
		List<UsuarioDTO> lista = usuarioService.findByNombre(nombre);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca un usuario por su correo electrónico.
	 *
	 * @param correo Correo a buscar.
	 * @return Lista con el usuario encontrado o vacío.
	 */
	@GetMapping("/buscarporcorreo")
	public ResponseEntity<List<UsuarioDTO>> buscarPorCorreo(@RequestParam String correo) {
		List<UsuarioDTO> lista = usuarioService.findByCorreo(correo);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Busca usuarios filtrando por su rol (Ej. "Jugador", "Admin").
	 *
	 * @param rol Rol a buscar.
	 * @return Lista de coincidencias.
	 */
	@GetMapping("/buscarporrol")
	public ResponseEntity<List<UsuarioDTO>> buscarPorRol(@RequestParam String rol) {
		List<UsuarioDTO> lista = usuarioService.findByRol(rol);
		if (!lista.isEmpty()) {
			return new ResponseEntity<>(lista, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(lista, HttpStatus.NO_CONTENT);
		}
	}
}