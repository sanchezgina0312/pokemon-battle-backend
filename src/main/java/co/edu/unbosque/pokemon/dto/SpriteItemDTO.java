package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que representa el recurso gráfico individual o ícono por defecto de un elemento.
 * <p>
 * Se utiliza en la capa de integración con la PokeAPI para capturar de forma específica la URL de la imagen 
 * estándar de los ítems de la tienda o como envoltura adaptada para los recursos visuales de los Pokémon.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
public class SpriteItemDTO {

	/**
	 * La dirección URL que apunta a la representación gráfica o sprite por defecto del elemento.
	 * <p>
	 * Dado que "default" es una palabra reservada en el lenguaje Java, la anotación 
	 * {@code @SerializedName("default")} es estrictamente necesaria para mapear de forma correcta 
	 * dicha propiedad desde el JSON externo procesado por Gson.
	 * </p>
	 */
	@SerializedName("default")
	private String imagenDefault;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code SpriteItemDTO}.
	 */
	public SpriteItemDTO() {

	}

	/**
	 * Constructor parametrizado de la clase.
	 * Inicializa una nueva instancia de {@code SpriteItemDTO} asignando la URL de la imagen provista.
	 *
	 * @param imagenDefault La URL de la ilustración o ícono por defecto del recurso.
	 */
	public SpriteItemDTO(String imagenDefault) {
		super();
		this.imagenDefault = imagenDefault;
	}

	/**
	 * Obtiene la dirección URL del sprite o ícono por defecto.
	 *
	 * @return Una cadena de caracteres con la URL de la imagen.
	 */
	public String getImagenDefault() {
		return imagenDefault;
	}

	/**
	 * Establece o modifica la dirección URL del sprite o ícono por defecto.
	 *
	 * @param imagenDefault La nueva cadena de caracteres con la URL a asignar.
	 */
	public void setImagenDefault(String imagenDefault) {
		this.imagenDefault = imagenDefault;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva que detalla el valor de {@code imagenDefault}.
	 */
	@Override
	public String toString() {
		return "SpriteItemDTO [imagenDefault=" + imagenDefault + "]";
	}

}