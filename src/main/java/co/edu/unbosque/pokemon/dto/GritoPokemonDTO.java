package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que representa el recurso de audio del grito de un Pokémon.
 * <p>
 * Se utiliza en los procesos de deserialización de la API externa (PokeAPI) para capturar 
 * las URLs o rutas de los archivos de sonido correspondientes a los efectos de voz clásicos o antiguos
 * de cada criatura.
 * </p>
 * 
 * @version 1.0
 */
public class GritoPokemonDTO {
	
	/**
	 * La dirección URL o ruta del archivo de audio que contiene el grito heredado o clásico (legacy) del Pokémon.
	 * <p>
	 * La anotación {@code @SerializedName("legacy")} vincula este campo con la propiedad 
	 * "legacy" dentro del nodo de gritos ("cries") del JSON externo procesado por Gson.
	 * </p>
	 */
	@SerializedName("legacy")
    private String gritoPokemon;

    /**
     * Constructor por defecto de la clase.
     * Crea una nueva instancia vacía de {@code GritoPokemonDTO}.
     */
    public GritoPokemonDTO() {
    }

	/**
	 * Obtiene la URL o ruta del archivo de audio del grito clásico del Pokémon.
	 *
	 * @return Una cadena de caracteres con la dirección del recurso de audio.
	 */
	public String getGritoPokemon() {
		return gritoPokemon;
	}

	/**
	 * Establece la URL o ruta del archivo de audio del grito clásico del Pokémon.
	 *
	 * @param gritoPokemon La nueva cadena de caracteres con la dirección del recurso a asignar.
	 */
	public void setGritoPokemon(String gritoPokemon) {
		this.gritoPokemon = gritoPokemon;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva que detalla el valor o ruta almacenada en {@code gritoPokemon}.
	 */
	@Override
	public String toString() {
		return "GritoPokemonDTO [gritoPokemon=" + gritoPokemon + "]";
	}
	
}