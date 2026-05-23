package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que representa una entrada de descripción textual de un Pokémon.
 * <p>
 * Se utiliza principalmente en la deserialización de las cadenas de texto informativas (historias o datos de la Pokédex)
 * obtenidas desde la API externa, vinculando el contenido del texto con su respectivo idioma de origen.
 * </p>
 * 
 * @version 1.0
 */
public class DescripcionDTO {

	/**
	 * El texto descriptivo, histórico o de ambientación del Pokémon (conocido en PokeAPI como flavor text).
	 * <p>
	 * La anotación {@code @SerializedName("flavor_text")} vincula este campo con la propiedad 
	 * "flavor_text" del JSON durante la conversión con Gson.
	 * </p>
	 */
	@SerializedName("flavor_text")
	private String textoDescripcion;

	/**
	 * Objeto que contiene los detalles del idioma en el que está escrita esta descripción en particular.
	 * <p>
	 * La anotación {@code @SerializedName("language")} mapea este atributo con el nodo 
	 * "language" del JSON externo.
	 * </p>
	 */
	@SerializedName("language")
	private IdiomaDTO idioma;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code DescripcionDTO}.
	 */
	public DescripcionDTO() {

	}

	/**
	 * Constructor parametrizado de la clase.
	 * Permite instanciar un nuevo {@code DescripcionDTO} asignando el texto descriptivo y su idioma correspondiente.
	 *
	 * @param textoDescripcion El texto narrativo de la descripción del Pokémon.
	 * @param idioma           El objeto {@link IdiomaDTO} que define el lenguaje del texto.
	 */
	public DescripcionDTO(String textoDescripcion, IdiomaDTO idioma) {
		super();
		this.textoDescripcion = textoDescripcion;
		this.idioma = idioma;
	}

	/**
	 * Obtiene el texto descriptivo o historia del Pokémon.
	 *
	 * @return Una cadena de caracteres con la descripción.
	 */
	public String getTextoDescripcion() {
		return textoDescripcion;
	}

	/**
	 * Establece el texto descriptivo o historia del Pokémon.
	 *
	 * @param textoDescripcion La nueva cadena de texto descriptiva a asignar.
	 */
	public void setTextoDescripcion(String textoDescripcion) {
		this.textoDescripcion = textoDescripcion;
	}

	/**
	 * Obtiene el objeto que representa el idioma de la descripción actual.
	 *
	 * @return Una instancia de {@link IdiomaDTO} con los datos del idioma.
	 */
	public IdiomaDTO getIdioma() {
		return idioma;
	}

	/**
	 * Establece el objeto que representa el idioma de la descripción actual.
	 *
	 * @param idioma El nuevo objeto {@link IdiomaDTO} a asignar.
	 */
	public void setIdioma(IdiomaDTO idioma) {
		this.idioma = idioma;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva de los valores de la instancia.
	 */
	@Override
	public String toString() {
		return "DescripcionDTO [textoDescripcion=" + textoDescripcion + ", idioma=" + idioma + "]";
	}

}