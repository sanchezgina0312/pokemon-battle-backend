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

@RestController
@RequestMapping("/pokemon/auth")
@CrossOrigin(origins = "*")
@Tag(name = "Autenticación", description = "API para el ingreso y registro al sistema de Batallas Pokémon")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    /**
     * Login usando CORREO y contraseña — el correo es el username en Spring Security.
     */
    @Operation(summary = "Iniciar sesión", description = "Envía correo y contraseña para recibir un Token JWT.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exitoso", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas") })
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Parameter(description = "Credenciales del usuario", required = true,
                examples = @ExampleObject(value = "{\"correo\": \"administrador@gmail.com\", \"contrasenia\": \"User2026*/\"}"))
            @RequestBody LoginRequest loginRequest) {
        try {
            // Autenticar con CORREO (getUsername() retorna correo en la entidad Usuario)
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getCorreo(),      // ← correo como username
                            loginRequest.getContrasenia()
                    ));

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(userDetails);

            if (userDetails instanceof Usuario) {
                Usuario user = (Usuario) userDetails;
                return ResponseEntity.ok(new AuthResponse(jwt, user.getRol().name(), user.getId(), user.getNombre()));
            }

            return ResponseEntity.ok(new AuthResponse(jwt, null, null, null));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Error: Correo o contraseña incorrectos.");
        }
    }

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

    // ─── DTOs internos ────────────────────────────────────────────────────────

    public static class LoginRequest {
        private String correo;       // ← cambiado de nombre a correo
        private String contrasenia;

        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }

        public String getContrasenia() { return contrasenia; }
        public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }
    }

    public static class AuthResponse {
        private String token;
        private String role;
        private Long id;
        private String nombre;

        public AuthResponse(String token, String role, Long id, String nombre) {
            this.token = token;
            this.role = role;
            this.id = id;
            this.nombre = nombre;
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
    }
}