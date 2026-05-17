package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que representa la envoltura de un tipo elemental de un Pokémon.
 * <p>
 * En la estructura JSON de la PokeAPI, las afinidades elementales de un Pokémon no vienen como un arreglo directo 
 * de cadenas, sino como una lista de objetos anidados. Esta clase actúa como ese contenedor intermedio necesario 
 * para aislar y deserializar correctamente la propiedad que almacena los detalles específicos del tipo.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
public class TipoPokemonDTO {

	/**
	 * Contenedor de la información detallada e identificadora del tipo elemental (ej. nombre del tipo).
	 * <p>
	 * La anotación {@code @SerializedName("type")} vincula este campo con el objeto anidado 
	 * "type" del JSON externo procesado por la librería Gson, abstrayéndolo mediante {@link InfoTipoDTO}.
	 * </p>
	 */
	@SerializedName("type")
	private InfoTipoDTO informacionTipo;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code TipoPokemonDTO}.
	 */
	public TipoPokemonDTO() {

	}

	/**
	 * Constructor parametrizado de la clase.
	 * Inicializa una nueva instancia de {@code TipoPokemonDTO} asignando el objeto de detalles del tipo elemental.
	 *
	 * @param informacionTipo El objeto {@link InfoTipoDTO} que contiene los metadatos del tipo.
	 */
	public TipoPokemonDTO(InfoTipoDTO informacionTipo) {
		super();
		this.informacionTipo = informacionTipo;
	}

	/**
	 * Obtiene el objeto con los detalles e información elemental del tipo.
	 *
	 * @return Una instancia de {@link InfoTipoDTO} con los datos del tipo.
	 */
	public InfoTipoDTO getInformacionTipo() {
		return informacionTipo;
	}

	/**
	 * Establece o modifica el objeto con los detalles e información elemental del tipo.
	 *
	 * @param informacionTipo El nuevo objeto {@link InfoTipoDTO} a asignar.
	 */
	public void setInformacionTipo(InfoTipoDTO informacionTipo) {
		this.informacionTipo = informacionTipo;
	}
	
	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva que detalla la instancia interna de {@code informacionTipo}.
	 */
	@Override
	public String toString() {
		return "TipoPokemonDTO [informacionTipo=" + informacionTipo + "]";
	}

}