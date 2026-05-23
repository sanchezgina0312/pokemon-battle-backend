package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que actúa como envoltura o nodo intermedio 
 * para la información de un ataque de Pokémon.
 * <p>
 * Esta clase se utiliza principalmente en los procesos de deserialización de datos JSON 
 * provenientes de servicios web externos (como PokeAPI), donde la estructura de la API 
 * anida los detalles del movimiento dentro de una propiedad específica.
 * </p>
 * 
 * @version 1.0
 */
public class AtaquePokemonDTO {

	/**
	 * Contenedor con la información detallada del ataque o movimiento del Pokémon.
	 * <p>
	 * La anotación {@code @SerializedName("move")} vincula este atributo con la propiedad 
	 * "move" del JSON externo durante los procesos de conversión con la librería Gson.
	 * </p>
	 */
	@SerializedName("move")
	private InformacionAtaqueDTO informacionAtaque;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code AtaquePokemonDTO}.
	 */
	public AtaquePokemonDTO() {

	}

	/**
	 * Constructor parametrizado de la clase.
	 * Inicializa una nueva instancia de {@code AtaquePokemonDTO} asignando el objeto de información provisto.
	 *
	 * @param informacionAtaque El objeto {@link InformacionAtaqueDTO} que contiene los detalles del movimiento.
	 */
	public AtaquePokemonDTO(InformacionAtaqueDTO informacionAtaque) {
		super();
		this.informacionAtaque = informacionAtaque;
	}

	/**
	 * Obtiene el objeto que contiene la información detallada del ataque.
	 *
	 * @return Una instancia de {@link InformacionAtaqueDTO} con los datos del ataque.
	 */
	public InformacionAtaqueDTO getInformacionAtaque() {
		return informacionAtaque;
	}

	/**
	 * Establece o modifica el objeto que contiene la información detallada del ataque.
	 *
	 * @param informacionAtaque El nuevo objeto {@link InformacionAtaqueDTO} a asignar.
	 */
	public void setInformacionAtaque(InformacionAtaqueDTO informacionAtaque) {
		this.informacionAtaque = informacionAtaque;
	}

	/**
	 * Devuelve una representación en formato de texto con los valores de los atributos de este DTO.
	 *
	 * @return Una cadena de caracteres que detalla el contenido de {@code informacionAtaque}.
	 */
	@Override
	public String toString() {
		return "AtaquePokemonDTO [informacionAtaque=" + informacionAtaque + "]";
	}

}