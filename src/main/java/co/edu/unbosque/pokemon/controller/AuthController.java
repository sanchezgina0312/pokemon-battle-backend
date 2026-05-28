package co.edu.unbosque.pokemon.controller;

import co.edu.unbosque.pokemon.dto.UsuarioDTO;
import co.edu.unbosque.pokemon.entity.Usuario;
import co.edu.unbosque.pokemon.security.JwtUtil;
import co.edu.unbosque.pokemon.service.EmailService;
import co.edu.unbosque.pokemon.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador encargado de manejar la autenticación, registro de usuarios y
 * servicios relacionados como el envío de códigos de verificación por correo. *
 * Expone endpoints para login, registro y envío de códigos de verificación.
 */
@RestController
@RequestMapping("/pokemon/auth")
@Tag(name = "Autenticación", description = "API para el ingreso y registro al sistema de Batallas Pokémon")
public class AuthController {

	/** Gestor encargado de la autenticación de los usuarios. */
	private final AuthenticationManager authenticationManager;

	/** Utilidad para la generación y validación de tokens JWT. */
	private final JwtUtil jwtUtil;

	/** Servicio que contiene la lógica de negocio para la gestión de usuarios. */
	private final UsuarioService userService;

	/** Servicio encargado del envío de correos electrónicos. */
	@Autowired
	private EmailService emailService;

	/**
	 * Constructor para inyectar las dependencias necesarias. * @param
	 * authenticationManager Gestor de autenticación de Spring Security
	 * 
	 * @param jwtUtil     Utilidad para la gestión de tokens JWT
	 * @param userService Servicio para la gestión de datos de usuario
	 */
	public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService userService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.userService = userService;
	}

	/**
	 * Permite iniciar sesión en el sistema y obtener un token JWT. * @param
	 * loginRequest credenciales del usuario (correo y contraseña)
	 * 
	 * @return token JWT junto con información básica del usuario si las
	 *         credenciales son válidas, o un error 401 si son incorrectas
	 */
	@Operation(summary = "Iniciar sesión", description = "Envía correo y contraseña para recibir un Token JWT.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Exitoso"),
			@ApiResponse(responseCode = "401", description = "Credenciales incorrectas") })
	@PostMapping("/login")
	public ResponseEntity<?> login(
			@Parameter(description = "Credenciales del usuario", required = true, examples = @ExampleObject(value = "{\"correo\": \"administrador@gmail.com\", \"contrasenia\": \"User2026*/\"}")) @RequestBody LoginRequest loginRequest) {

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getContrasenia()));

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String jwt = jwtUtil.generateToken(userDetails);

			if (userDetails instanceof Usuario user) {
				return ResponseEntity.ok(
						new AuthResponse(jwt, user.getRol().name(), user.getId(), user.getNombre(), user.getGenero()));
			}

			return ResponseEntity.ok(new AuthResponse(jwt, null, null, null, null));

		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Correo o contraseña incorrectos.");
		}
	}

	/**
	 * Registra un nuevo usuario en el sistema. El rol por defecto es USUARIO.
	 * * @param registerRequest datos del usuario a registrar
	 * 
	 * @return mensaje de éxito o error según el resultado del proceso
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
	 * Envía un código de verificación al correo del usuario. * @param correo correo
	 * del usuario
	 * 
	 * @param nombre nombre del usuario
	 * @return código generado o mensaje de error si falla el envío
	 */
	@PostMapping("/enviar-codigo")
	public ResponseEntity<String> enviarCodigoVerificacion(@RequestParam String correo, @RequestParam String nombre) {

		try {
			String codigo = emailService.generarCodigoVerificacion();
			emailService.enviarCorreoCodigo(correo, codigo, nombre);
			return new ResponseEntity<>(codigo, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al enviar el correo", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Objeto que representa las credenciales de inicio de sesión.
	 */
	public static class LoginRequest {
		private String correo;
		private String contrasenia;

		/** @return el correo electrónico del usuario */
		public String getCorreo() {
			return correo;
		}

		/** @param correo el correo electrónico a establecer */
		public void setCorreo(String correo) {
			this.correo = correo;
		}

		/** @return la contraseña del usuario */
		public String getContrasenia() {
			return contrasenia;
		}

		/** @param contrasenia la contraseña a establecer */
		public void setContrasenia(String contrasenia) {
			this.contrasenia = contrasenia;
		}
	}

	/**
	 * Respuesta de autenticación que contiene el token JWT y datos del usuario.
	 */
	public static class AuthResponse {
		private String token;
		private String role;
		private Long id;
		private String nombre;
		private String genero;

		/**
		 * Constructor de la respuesta de autenticación. * @param token token generado
		 * 
		 * @param role   rol del usuario
		 * @param id     identificador del usuario
		 * @param nombre nombre del usuario
		 * @param genero género del usuario
		 */
		public AuthResponse(String token, String role, Long id, String nombre, String genero) {
			this.token = token;
			this.role = role;
			this.id = id;
			this.nombre = nombre;
			this.genero = genero;
		}

		/** @return el token JWT */
		public String getToken() {
			return token;
		}

		/** @param token el token JWT a establecer */
		public void setToken(String token) {
			this.token = token;
		}

		/** @return el rol del usuario */
		public String getRole() {
			return role;
		}

		/** @param role el rol del usuario a establecer */
		public void setRole(String role) {
			this.role = role;
		}

		/** @return el identificador del usuario */
		public Long getId() {
			return id;
		}

		/** @param id el identificador del usuario a establecer */
		public void setId(Long id) {
			this.id = id;
		}

		/** @return el género del usuario */
		public String getGenero() {
			return genero;
		}

		/** @param genero el género del usuario a establecer */
		public void setGenero(String genero) {
			this.genero = genero;
		}

		/** @return el nombre del usuario */
		public String getNombre() {
			return nombre;
		}

		/** @param nombre el nombre del usuario a establecer */
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}
	}
}