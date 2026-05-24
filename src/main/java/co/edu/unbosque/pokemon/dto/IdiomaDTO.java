package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que representa la información de un idioma en el sistema.
 * <p>
 * Se utiliza principalmente en los procesos de deserialización de la API externa (PokeAPI) 
 * para identificar y filtrar los recursos de texto (como descripciones o nombres) según el 
 * lenguaje correspondiente (por ejemplo, español o inglés).
 * </p>
 * 
 * @version 1.0
 */
public class IdiomaDTO {

	/**
	 * El código o identificador textual del idioma (por ejemplo, "es" para español o "en" para inglés).
	 * <p>
	 * La anotación {@code @SerializedName("name")} vincula este campo con la propiedad 
	 * "name" dentro del nodo de idioma del JSON externo procesado por Gson.
	 * </p>
	 */
	@SerializedName("name")
	private String nombreIdioma; // "es" o "en"

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code IdiomaDTO}.
	 */
	public IdiomaDTO() {

	}

	/**
	 * Constructor parametrizado de la clase.
	 * Inicializa una nueva instancia de {@code IdiomaDTO} asignando el identificador del idioma provisto.
	 *
	 * @param nombreIdioma El código o nombre del idioma (ej. "es", "en").
	 */
	public IdiomaDTO(String nombreIdioma) {
		super();
		this.nombreIdioma = nombreIdioma;
	}

	/**
	 * Obtiene el código o identificador del idioma.
	 *
	 * @return Una cadena de caracteres con el código del idioma (ej. "es" o "en").
	 */
	public String getNombreIdioma() {
		return nombreIdioma;
	}

	/**
	 * Establece el código o identificador del idioma.
	 *
	 * @param nombreIdioma La nueva cadena de caracteres con el código del idioma a asignar.
	 */
	public void setNombreIdioma(String nombreIdioma) {
		this.nombreIdioma = nombreIdioma;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva que detalla el valor almacenado en {@code nombreIdioma}.
	 */
	@Override
	public String toString() {
		return "IdiomaDTO [nombreIdioma=" + nombreIdioma + "]";
	}

}