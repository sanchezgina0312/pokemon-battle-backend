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
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(
                    "/pokemon/auth/**",
                    "/usuario/login",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/usuario/traducir",
                    "/pokemon/auth/enviar-codigo"
                    // /usuario/crear ya NO está aquí — solo ADMIN puede crear
                ).permitAll()
                .anyRequest().access((authentication, context) -> {
                    var authObj = authentication.get();

                    // Log detallado de Nata: útil para depurar quién llega y con qué rol
                    if (authObj != null) {
                        System.out.println("Usuario: " + authObj.getName());
                        System.out.println("Autoridades detectadas: " + authObj.getAuthorities());
                    } else {
                        System.out.println("authObj es NULL (No hay usuario autenticado)");
                    }

                    // Sin sesión válida → denegar
                    if (authObj == null || !authObj.isAuthenticated() ||
                        authObj instanceof org.springframework.security.authentication.AnonymousAuthenticationToken) {
                        return new AuthorizationDecision(false);
                    }

                    // Log para depuración — puedes quitarlo en producción
                    System.out.println(">>> [SecurityConfig] Usuario: " + authObj.getName()
                        + " | Authorities: " + authObj.getAuthorities()
                        + " | Path: " + context.getRequest().getServletPath());

                    // ADMINISTRADOR tiene acceso a todo
                    // Se usa contains para cubrir variantes: ADMINISTRADOR, ROLE_ADMINISTRADOR
                    boolean isAdmin = authObj.getAuthorities().stream()
                        .anyMatch(a ->
                            a.getAuthority().equals("ROLE_ADMINISTRADOR") ||
                            a.getAuthority().equals("ADMINISTRADOR") ||
                            a.getAuthority().contains("ADMINISTRADOR")
                        );

                    if (isAdmin) {
                        System.out.println(">>> [SecurityConfig] Acceso concedido como ADMINISTRADOR");
                        return new AuthorizationDecision(true);
                    }

                    // Rutas exclusivas de ADMIN
                    String path = context.getRequest().getServletPath();
                    boolean isAdminOnly =
                            path.equals("/ataque/banear")
     
                        || path.equals("/pokemon/cargar")
                        || path.equals("/pokemon/cargarbd")
                        || path.equals("/usuario/mostrartodo")
                        || path.equals("/usuario/buscarpornombre")
                        || path.equals("/usuario/buscarporcorreo")
                        || path.equals("/usuario/buscarporrol")
                        || path.equals("/usuario/crear")
                        || path.equals("/usuario/actualizar")
                        || path.equals("/usuario/eliminar")
                        || path.equals("/auditoria/mostrartodo")
                        || path.equals("/pokemon/actualizar-configuracion")
                        || path.equals("/inventario/listar-todo");
                
                    System.out.println("DEBUG - Analizando ruta: " + path);
                    if (path.equals("/ataque/mostrartodo")) {
                        System.out.println("DEBUG - Validando ataque/mostrartodo específicamente");
                    }

                    if (isAdminOnly) {
                        System.out.println(">>> [SecurityConfig] Ruta admin-only denegada para USUARIO: " + path);
                        return new AuthorizationDecision(false);
                    }

                    // Rutas permitidas para USUARIO normal autenticado
                    boolean isUsuarioAllowed =
                            path.startsWith("/captura/")
                        || path.startsWith("/combate/")
                        || path.startsWith("/centropokemon/")
                        || path.startsWith("/tienda/")
                        || path.startsWith("/inventario/")
                        || path.startsWith("/item/")
                        || path.startsWith("/pokemon/")
                        || path.startsWith("/usuario/genero")
                        || path.startsWith("/centropokemon");

                    System.out.println(">>> [SecurityConfig] isUsuarioAllowed=" + isUsuarioAllowed + " para: " + path);
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