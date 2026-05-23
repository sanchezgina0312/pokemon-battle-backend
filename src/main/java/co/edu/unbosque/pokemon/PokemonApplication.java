package co.edu.unbosque.pokemon;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Clase principal de la aplicación Pokémon.
 * 
 * Esta clase inicializa el contexto de Spring Boot
 * y configura los beans necesarios para el funcionamiento
 * de la aplicación.
 * 
 * @version 1.0
 */
@SpringBootApplication
public class PokemonApplication {

	/**
	 * Método principal que inicia la aplicación Spring Boot.
	 * 
	 * @param args argumentos de ejecución de la aplicación.
	 */
	public static void main(String[] args) {
		SpringApplication.run(PokemonApplication.class, args);
	}

	/**
	 * Registra un bean de ModelMapper en el contexto de Spring.
	 * 
	 * ModelMapper se utiliza para realizar conversiones
	 * automáticas entre entidades y DTOs.
	 * 
	 * @return instancia configurada de ModelMapper.
	 */
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

}