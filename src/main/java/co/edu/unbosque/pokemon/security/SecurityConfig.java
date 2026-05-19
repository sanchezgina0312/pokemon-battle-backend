package co.edu.unbosque.pokemon.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

                    // ADMINISTRADOR tiene acceso a absolutamente todo
                    boolean isAdmin = authObj.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRADOR"));
                    if (isAdmin) return new AuthorizationDecision(true);

                    // Si no está autenticado, denegar
                    if (!authObj.isAuthenticated()) return new AuthorizationDecision(false);

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

                    // Endpoints permitidos para USUARIO y ADMINISTRADOR
                    boolean isUsuarioAllowed = path.startsWith("/captura/")
                        || path.startsWith("/combate/")
                        || path.startsWith("/centropokemon/")
                        || path.startsWith("/tienda/")
                        || path.startsWith("/inventario/")
                        || path.startsWith("/item/")
                        || path.startsWith("/pokemon/");

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
}