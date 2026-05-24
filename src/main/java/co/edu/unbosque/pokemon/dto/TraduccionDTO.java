package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que contiene el resultado de una conversión lingüística o traducción.
 * <p>
 * Se utiliza en los procesos de integración con APIs de traducción o localización externas 
 * para mapear y extraer directamente el fragmento de texto ya procesado y localizado en el idioma de destino.
 * </p>
 * 
 * @version 1.0
 */
public class TraduccionDTO {

	/**
	 * El contenido textual ya traducido obtenido de la respuesta del servicio externo.
	 * <p>
	 * La anotación {@code @SerializedName("translated")} vincula este campo con la propiedad 
	 * "translated" del JSON externo procesado por la librería Gson.
	 * </p>
	 */
	@SerializedName("translated")
	private String textoTraducido;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code TraduccionDTO}.
	 */
	public TraduccionDTO() {
		
	}

	/**
	 * Constructor parametrizado de la clase.
	 * Inicializa una nueva instancia de {@code TraduccionDTO} asignando el texto traducido provisto.
	 *
	 * @param textoTraducido El fragmento de texto o cadena de caracteres ya traducida.
	 */
	public TraduccionDTO(String textoTraducido) {
		super();
		this.textoTraducido = textoTraducido;
	}

	/**
	 * Obtiene el contenido del texto traducido.
	 *
	 * @return Una cadena de caracteres con el texto localizado.
	 */
	public String getTextoTraducido() {
		return textoTraducido;
	}

	/**
	 * Establece o modifica el contenido del texto traducido.
	 *
	 * @param textoTraducido La nueva cadena de caracteres con el texto traducido a asignar.
	 */
	public void setTextoTraducido(String textoTraducido) {
		this.textoTraducido = textoTraducido;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva que detalla el valor almacenado en {@code textoTraducido}.
	 */
	@Override
	public String toString() {
		return "TraduccionDTO [textoTraducido=" + textoTraducido + "]";
	}

}