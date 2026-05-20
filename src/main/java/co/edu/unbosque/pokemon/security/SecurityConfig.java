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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, UserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            // Habilita la configuración de CORS usando el Bean definido abajo
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

                    // Si no está autenticado en absoluto, denegar de inmediato
                    if (authObj == null || !authObj.isAuthenticated() || 
                        authObj instanceof org.springframework.security.authentication.AnonymousAuthenticationToken) {
                        return new AuthorizationDecision(false);
                    }

                    // Validar el rol tanto con el prefijo "ROLE_" como sin él para evitar fallos de mapeo
                    boolean isAdmin = authObj.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRADOR") 
                                    || a.getAuthority().equals("ADMINISTRADOR"));
                    
                    // ADMINISTRADOR tiene acceso a absolutamente todo
                    if (isAdmin) return new AuthorizationDecision(true);

                    String path = context.getRequest().getServletPath();

                    // Endpoints exclusivos del ADMINISTRADOR — denegar a USUARIO
                    boolean isAdminOnly = path.equals("/ataque/banear")
                        || path.equals("/pokemon/cargar")
                        || path.equals("/pokemon/cargarbd")
                        || path.equals("/usuario/mostrartodo")
                        || path.equals("/usuario/buscarpornombre")
                        || path.equals("/usuario/buscarporcorreo")
                        || path.equals("/usuario/actualizar")
                        || path.equals("/usuario/eliminar");

                    if (isAdminOnly) return new AuthorizationDecision(false);

                 // Endpoints permitidos para USUARIO común
                    boolean isUsuarioAllowed = path.startsWith("/captura/")
                        || path.startsWith("/combate/")
                        || path.startsWith("/centropokemon/")
                        || path.startsWith("/tienda/")
                        || path.startsWith("/inventario/")
                        || path.startsWith("/item/")
                        || path.startsWith("/pokemon/")
                        || path.startsWith("/usuario/genero");
          
                    return new AuthorizationDecision(isUsuarioAllowed);
                }))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Define las reglas globales de CORS requeridas por Spring Security.
     * Intercepta las solicitudes previas (OPTIONS) de Angular permitiendo el flujo de datos.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Autoriza explícitamente el origen de tu servidor de desarrollo en Angular
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        
        // Permite los verbos HTTP necesarios para operar la app y las tablas
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Permite cualquier cabecera (fundamental para recibir el token JWT en 'Authorization')
        configuration.setAllowedHeaders(List.of("*"));
        
        // Permite compartir credenciales o cookies entre puertos locales si aplica
        configuration.setAllowCredentials(true);
        
        // Aplica este filtro a todas las rutas de la API de manera uniforme
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}