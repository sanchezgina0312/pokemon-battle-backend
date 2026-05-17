package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que representa las imágenes o recursos gráficos (sprites) de un Pokémon.
 * <p>
 * Se utiliza en el proceso de deserialización del JSON de la PokeAPI para capturar las URLs que apuntan 
 * a las representaciones visuales estándar de la criatura en sus perspectivas de frente y de espalda.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
public class SpriteDTO {

	/**
	 * La dirección URL que apunta a la imagen o sprite del Pokémon visto desde el frente.
	 * <p>
	 * La anotación {@code @SerializedName("front_default")} vincula este campo con la propiedad 
	 * "front_default" del JSON externo procesado por Gson.
	 * </p>
	 */
	@SerializedName("front_default")
	private String frente;

	/**
	 * La dirección URL que apunta a la imagen o sprite del Pokémon visto desde la espalda.
	 * <p>
	 * La anotación {@code @SerializedName("back_default")} vincula este campo con la propiedad 
	 * "back_default" del JSON externo procesado por Gson.
	 * </p>
	 */
	@SerializedName("back_default")
	private String espalda;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code SpriteDTO}.
	 */
	public SpriteDTO() {

	}

	/**
	 * Constructor parametrizado de la clase.
	 * Inicializa una nueva instancia de {@code SpriteDTO} asignando las URLs de frente y espalda provistas.
	 *
	 * @param frente  La URL de la ilustración frontal por defecto.
	 * @param espalda La URL de la ilustración trasera por defecto.
	 */
	public SpriteDTO(String frente, String espalda) {
		super();
		this.frente = frente;
		this.espalda = espalda;
	}

	/**
	 * Obtiene la dirección URL del sprite frontal del Pokémon.
	 *
	 * @return Una cadena de caracteres con la URL de la imagen frontal.
	 */
	public String getFrente() {
		return frente;
	}

	/**
	 * Establece o modifica la dirección URL del sprite frontal del Pokémon.
	 *
	 * @param frente La nueva cadena de caracteres con la URL frontal a asignar.
	 */
	public void setFrente(String frente) {
		this.frente = frente;
	}

	/**
	 * Obtiene la dirección URL del sprite trasero del Pokémon.
	 *
	 * @return Una cadena de caracteres con la URL de la imagen de espalda.
	 */
	public String getEspalda() {
		return espalda;
	}

	/**
	 * Establece o modifica la dirección URL del sprite trasero del Pokémon.
	 *
	 * @param espalda La nueva cadena de caracteres con la URL de espalda a asignar.
	 */
	public void setEspalda(String espalda) {
		this.espalda = espalda;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva que detalla las URLs almacenadas en {@code frente} y {@code espalda}.
	 */
	@Override
	public String toString() {
		return "SpriteDTO [frente=" + frente + ", espalda=" + espalda + "]";
	}
	
	/**
	 * Método utilitario que adapta y envuelve la dirección de la imagen frontal dentro de un nuevo objeto contenedor.
	 *
	 * @return Una nueva instancia de {@link SpriteItemDTO} inicializada con la URL de la propiedad {@code frente}.
	 */
	public SpriteItemDTO getFrontDefault() {
	    return new SpriteItemDTO(this.frente);
	}

	/**
	 * Método utilitario que adapta y envuelve la dirección de la imagen trasera dentro de un nuevo objeto contenedor.
	 *
	 * @return Una nueva instancia de {@link SpriteItemDTO} inicializada con la URL de la propiedad {@code espalda}.
	 */
	public SpriteItemDTO getBackDefault() {
	    return new SpriteItemDTO(this.espalda);
	}

}