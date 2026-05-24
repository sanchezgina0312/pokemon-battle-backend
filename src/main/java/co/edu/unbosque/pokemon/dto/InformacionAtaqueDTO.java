package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que contiene la información básica de referencia para un ataque.
 * <p>
 * Se utiliza en la capa de integración con servicios externos para mapear y deserializar los nodos de información 
 * elemental de un movimiento (como su nombre identificador y el enlace de consulta para obtener sus detalles)
 * provenientes de la PokeAPI.
 * </p>
 * 
 * @version 1.0
 */
public class InformacionAtaqueDTO {

	/**
	 * El nombre oficial e identificador del ataque o movimiento en la API externa.
	 * <p>
	 * La anotación {@code @SerializedName("name")} vincula este campo con la propiedad 
	 * "name" del JSON externo procesado por la librería Gson.
	 * </p>
	 */
	@SerializedName("name")
	private String nombre;

	/**
	 * La dirección URL que apunta al recurso detallado del ataque dentro de los servidores externos.
	 * <p>
	 * La anotación {@code @SerializedName("url")} vincula este campo con la propiedad 
	 * "url" del JSON externo procesado por la librería Gson.
	 * </p>
	 */
	@SerializedName("url")
	private String urlAtaque;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code InformacionAtaqueDTO}.
	 */
	public InformacionAtaqueDTO() {

	}

	/**
	 * Constructor parametrizado de la clase.
	 * Inicializa una nueva instancia de {@code InformacionAtaqueDTO} asignando el nombre y la URL base provistas.
	 *
	 * @param nombre    El nombre identificador del ataque.
	 * @param urlAtaque La dirección URL de consulta para el recurso del ataque.
	 */
	public InformacionAtaqueDTO(String nombre, String urlAtaque) {
		super();
		this.nombre = nombre;
		this.urlAtaque = urlAtaque;
	}

	/**
	 * Obtiene el nombre oficial del ataque.
	 *
	 * @return Una cadena de caracteres con el nombre del movimiento.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece o modifica el nombre oficial del ataque.
	 *
	 * @param nombre La nueva cadena de caracteres con el nombre a asignar.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la dirección URL del recurso detallado del ataque.
	 *
	 * @return Una cadena de caracteres con la URL de consulta.
	 */
	public String getUrlAtaque() {
		return urlAtaque;
	}

	/**
	 * Establece o modifica la dirección URL del recurso detallado del ataque.
	 *
	 * @param urlAtaque La nueva cadena de caracteres con la URL a asignar.
	 */
	public void setUrlAtaque(String urlAtaque) {
		this.urlAtaque = urlAtaque;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva que detalla los valores de {@code nombre} y {@code urlAtaque}.
	 */
	@Override
	public String toString() {
		return "InformacionAtaqueDTO [nombre=" + nombre + ", urlAtaque=" + urlAtaque + "]";
	}

}