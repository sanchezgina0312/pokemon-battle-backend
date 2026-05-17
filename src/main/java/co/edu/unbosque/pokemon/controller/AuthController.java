package co.edu.unbosque.pokemon.controller;

import co.edu.unbosque.pokemon.dto.UsuarioDTO;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.security.JwtUtil;
import co.edu.unbosque.pokemon.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST encargado de los procesos de autenticación del sistema.
 * <p>
 * Expone los endpoints públicos para el inicio de sesión (Login) de
 * entrenadores y administradores, así como el registro automático de nuevas
 * cuentas.
 * </p>
 * * @author Gina Sánchez
 * 
 * @version 1.0
 */
@RestController
@RequestMapping("/pokemon/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Autenticación", description = "API para el ingreso y registro al sistema de Batallas Pokémon")
public class AuthController {

	/**
	 * Administrador de autenticación provisto por Spring Security.
	 */
	private final AuthenticationManager authenticationManager;

	/**
	 * Componente utilitario para la creación y validación de tokens JWT.
	 */
	private final JwtUtil jwtUtil;

	/**
	 * Servicio encargado de la lógica empresarial de los usuarios.
	 */
	private final UsuarioService userService;

	/**
	 * Constructor con inyección de dependencias para el controlador de
	 * autenticación.
	 *
	 * @param authenticationManager el manejador de autenticaciones de Spring
	 * @param jwtUtil               la utilidad para tokens criptográficos
	 * @param userService           el servicio de negocio de usuarios
	 */
	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userService = userService;
	}

	/**
	 * Procesa las credenciales de un entrenador para otorgarle un token de acceso
	 * JWT.
	 * <p>
	 * Utiliza una estructura optimizada (LoginRequest) que evita errores de mapeo
	 * por datos nulos.
	 * </p>
	 *
	 * @param loginRequest el objeto JSON que contiene estrictamente nombre y
	 *                     contrasenia
	 * @return ResponseEntity con la información del token y rol si es exitoso, o
	 *         401 si falla
	 */
	@Operation(summary = "Iniciar sesión", description = "Envía nombre de usuario y contraseña para recibir un Token JWT.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Exitoso", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
			@ApiResponse(responseCode = "401", description = "Credenciales incorrectas") })
	@PostMapping("/login")
	public ResponseEntity<?> login(
			@Parameter(description = "Credenciales esenciales del usuario", required = true, examples = @ExampleObject(value = "{\"nombre\": \"AdministradorPokedes\", \"contrasenia\": \"User2026*/\"}")) @RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getNombre(), loginRequest.getContrasenia()));

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String jwt = jwtUtil.generateToken(userDetails);

			String role = null;
			if (userDetails instanceof Usuario) {
				Usuario user = (Usuario) userDetails;
				role = user.getRol().name();
				return ResponseEntity.ok(new AuthResponse(jwt, role, user.getId(), user.getNombre()));
			}

			return ResponseEntity.ok(new AuthResponse(jwt, role, null, null));

		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body("Error: Nombre de usuario o contraseña incorrectos.");
		}
	}

	/**
	 * Permite el autoregistro de nuevos entrenadores asignándoles el rol
	 * predeterminado.
	 *
	 * @param registerRequest el DTO estructurado con los datos del nuevo entrenador
	 * @return ResponseEntity con el estado de la operación (210 creado, 409
	 *         conflicto o 400 error)
	 */
	@Operation(summary = "Registrar nuevo usuario", description = "Permite a un usuario crearse una cuenta (Rol USUARIO por defecto).")
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UsuarioDTO registerRequest) {
		if (userService.findUsernameAlreadyTaken(registerRequest.getNombre())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Ese nombre ya está en uso");
		}

		int result = userService.create(registerRequest);
		if (result == 0) {
			return ResponseEntity.status(HttpStatus.CREATED).body("¡Bienvenido! Usuario registrado.");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo completar el registro.");
		}
	}

	/**
	 * Estructura de datos (DTO Interno) utilizada para mapear estrictamente los
	 * datos del formulario de Login.
	 * <p>
	 * Previene errores de deserialización al no contener campos primitivos
	 * obligatorios.
	 * </p>
	 */
	public static class LoginRequest {
		private String nombre;
		private String contrasenia;

		/**
		 * Obtiene el nombre de usuario provisto.
		 * 
		 * @return el nombre del usuario
		 */
		public String getNombre() {
			return nombre;
		}

		/**
		 * Establece el nombre de usuario para la validación.
		 * 
		 * @param nombre el nombre a asignar
		 */
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		/**
		 * Obtiene la contraseña provista sin encriptar.
		 * 
		 * @return la contraseña del usuario
		 */
		public String getContrasenia() {
			return contrasenia;
		}

		/**
		 * Establece la contraseña para el proceso de login.
		 * 
		 * @param contrasenia la contraseña a asignar
		 */
		public void setContrasenia(String contrasenia) {
			this.contrasenia = contrasenia;
		}
	}

	/**
	 * DTO interno encargado de estructurar la respuesta devuelta tras una
	 * autenticación exitosa.
	 */
	public static class AuthResponse {
		private String token;
		private String role;
		private Long id;
		private String nombre;

		/**
		 * Constructor completo de la respuesta de autenticación.
		 *
		 * @param token  el token JWT generado
		 * @param role   el rol del usuario autenticado
		 * @param id     el identificador único del usuario
		 * @param nombre el nombre del usuario
		 */
		public AuthResponse(String token, String role, Long id, String nombre) {
			this.token = token;
			this.role = role;
			this.id = id;
			this.nombre = nombre;
		}

		/** @return el token JWT */
		public String getToken() {
			return token;
		}

		/** @param token el token a asignar */
		public void setToken(String token) {
			this.token = token;
		}

		/** @return el rol asignado */
		public String getRole() {
			return role;
		}

		/** @param role el rol a asignar */
		public void setRole(String role) {
			this.role = role;
		}

		/** @return el identificador único */
		public Long getId() {
			return id;
		}

		/** @param id el identificador a asignar */
		public void setId(Long id) {
			this.id = id;
		}

		/** @return el nombre visible */
		public String getNombre() {
			return nombre;
		}

		/** @param nombre el nombre a asignar */
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
	}
}