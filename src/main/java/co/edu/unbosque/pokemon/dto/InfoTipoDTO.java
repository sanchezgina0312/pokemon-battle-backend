package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que contiene la información básica de un tipo o categoría.
 * <p>
 * Se utiliza en la capa de integración con la API externa (PokeAPI) como un nodo genérico y 
 * reutilizable para extraer el nombre identificador de propiedades clasificatorias, tales como 
 * los tipos elementales del Pokémon o las clases de daño de sus movimientos (Físico, Especial, Estado).
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
public class InfoTipoDTO {

	/**
	 * El nombre oficial de la clasificación o tipo elemental en la API externa (ej. "fire", "water", "physical").
	 * <p>
	 * La anotación {@code @SerializedName("name")} vincula este campo con la propiedad 
	 * "name" del JSON externo procesado por la librería Gson.
	 * </p>
	 */
	@SerializedName("name")
	private String nombreTipo;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code InfoTipoDTO}.
	 */
	public InfoTipoDTO() {

	}

	/**
	 * Constructor parametrizado de la clase.
	 * Inicializa una nueva instancia de {@code InfoTipoDTO} asignando el nombre de la clasificación provisto.
	 *
	 * @param nombreTipo El nombre identificador del tipo o clase.
	 */
	public InfoTipoDTO(String nombreTipo) {
		super();
		this.nombreTipo = nombreTipo;
	}

	/**
	 * Obtiene el nombre del tipo o clasificación.
	 *
	 * @return Una cadena de caracteres con el nombre del tipo.
	 */
	public String getNombreTipo() {
		return nombreTipo;
	}

	/**
	 * Establece o modifica el nombre del tipo o clasificación.
	 *
	 * @param nombreTipo La nueva cadena de caracteres con el nombre a asignar.
	 */
	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva que detalla el valor almacenado en {@code nombreTipo}.
	 */
	@Override
	public String toString() {
		return "InfoTipoDTO [nombreTipo=" + nombreTipo + "]";
	}

}