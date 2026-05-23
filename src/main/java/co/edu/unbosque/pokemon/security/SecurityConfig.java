package co.edu.unbosque.pokemon.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuración principal de seguridad de la aplicación.
 * <p>
 * Esta clase define:
 * </p>
 * <ul>
 *     <li>La autenticación mediante JWT.</li>
 *     <li>La autorización de endpoints según roles.</li>
 *     <li>La configuración de CORS.</li>
 *     <li>La política de sesiones stateless.</li>
 *     <li>El proveedor de autenticación y codificación de contraseñas.</li>
 * </ul>
 *
 * <p>
 * Se utiliza Spring Security para proteger los recursos de la API.
 * </p>
 *
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Filtro encargado de validar el token JWT en cada petición.
     */
    private final JwtAuthenticationFilter jwtAuthFilter;

    /**
     * Servicio encargado de cargar los detalles del usuario.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Constructor de la configuración de seguridad.
     *
     * @param jwtAuthFilter filtro JWT utilizado para autenticar usuarios.
     * @param userDetailsService servicio para obtener información de usuarios.
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configura la cadena de filtros de seguridad de Spring Security.
     * <p>
     * Define:
     * </p>
     * <ul>
     *     <li>Desactivación de CSRF.</li>
     *     <li>Configuración de CORS.</li>
     *     <li>Permisos de acceso a endpoints públicos.</li>
     *     <li>Restricciones de acceso según rol.</li>
     *     <li>Uso de sesiones stateless.</li>
     *     <li>Registro del filtro JWT.</li>
     * </ul>
     *
     * @param http objeto de configuración de seguridad HTTP.
     * @return cadena de filtros de seguridad configurada.
     * @throws Exception si ocurre un error durante la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/pokemon/auth/**",
                    "/usuario/login",
                    "/usuario/crear",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/usuario/traducir",
                    "/pokemon/auth/enviar-codigo"
                ).permitAll()
                .anyRequest().access((authentication, context) -> {
                    var authObj = authentication.get();

                    if (authObj == null || !authObj.isAuthenticated() ||
                        authObj instanceof org.springframework.security.authentication.AnonymousAuthenticationToken) {
                        return new AuthorizationDecision(false);
                    }

                    boolean isAdmin = authObj.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRADOR")
                                    || a.getAuthority().equals("ADMINISTRADOR"));

                    if (isAdmin) return new AuthorizationDecision(true);

                    String path = context.getRequest().getServletPath();

                    // Endpoints exclusivos del ADMINISTRADOR
                    boolean isAdminOnly = path.equals("/ataque/banear")
                        || path.equals("/pokemon/cargar")
                        || path.equals("/pokemon/cargarbd")
                        || path.equals("/usuario/mostrartodo")
                        || path.equals("/usuario/buscarpornombre")
                        || path.equals("/usuario/buscarporcorreo")
                        || path.equals("/usuario/actualizar")
                        || path.equals("/usuario/eliminar")
                        || path.equals("/auditoria/mostrartodo");

                    if (isAdminOnly) return new AuthorizationDecision(false);

                    // Endpoints permitidos para usuarios comunes
                    boolean isUsuarioAllowed = path.startsWith("/captura/")
                        || path.startsWith("/combate/")
                        || path.startsWith("/centropokemon/")
                        || path.startsWith("/tienda/")
                        || path.startsWith("/inventario/")
                        || path.startsWith("/item/")
                        || path.startsWith("/pokemon/")
                        || path.startsWith("/usuario/genero")
                        || path.startsWith("/centropokemon");


                    return new AuthorizationDecision(isUsuarioAllowed);
                }))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configura el proveedor de autenticación de Spring Security.
     * <p>
     * Utiliza un {@link DaoAuthenticationProvider} con el servicio
     * de usuarios y el codificador BCrypt.
     * </p>
     *
     * @return proveedor de autenticación configurado.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Obtiene el administrador de autenticación de Spring Security.
     *
     * @param config configuración de autenticación.
     * @return administrador de autenticación.
     * @throws Exception si ocurre un error al obtenerlo.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Define el codificador de contraseñas utilizado por la aplicación.
     * <p>
     * Se utiliza BCrypt para almacenar contraseñas de forma segura.
     * </p>
     *
     * @return codificador de contraseñas BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura la política CORS de la aplicación.
     * <p>
     * Permite solicitudes desde el frontend Angular
     * ejecutándose en localhost:4200.
     * </p>
     *
     * @return configuración de CORS.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}