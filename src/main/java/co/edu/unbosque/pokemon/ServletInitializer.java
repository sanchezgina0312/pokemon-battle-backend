package co.edu.unbosque.pokemon;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Clase encargada de configurar el despliegue
 * de la aplicación Spring Boot en un contenedor
 * de servlets externo.
 * 
 * Extiende SpringBootServletInitializer para permitir
 * la inicialización de la aplicación como un archivo WAR.
 * 
 * @version 1.0
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Configura las fuentes principales de la aplicación
	 * para el contenedor de servlets.
	 * 
	 * @param application constructor de la aplicación Spring.
	 * @return configuración de la aplicación.
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PokemonApplication.class);
	}

}