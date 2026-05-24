package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que contiene la información básica y de referencia de un Pokémon.
 * <p>
 * Se utiliza principalmente en los listados paginados o colecciones generales devueltas por la API externa (PokeAPI),
 * donde solo se provee el nombre del Pokémon y la URL correspondiente para realizar una consulta detallada 
 * de sus características.
 * </p>
 * 
 * @version 1.0
 */
public class ReferenciaPokemonDTO {

	/**
	 * El nombre oficial de la especie del Pokémon que sirve como identificador textual en la API.
	 * <p>
	 * La anotación {@code @SerializedName("name")} vincula este campo con la propiedad 
	 * "name" del JSON externo procesado por Gson.
	 * </p>
	 */
	@SerializedName("name")
	private String nombre;

	/**
	 * La dirección URL que apunta al recurso con el detalle completo del Pokémon dentro de los servidores externos.
	 * <p>
	 * La anotación {@code @SerializedName("url")} vincula este campo con la propiedad 
	 * "url" del JSON externo procesado por Gson.
	 * </p>
	 */
	@SerializedName("url")
	private String url;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code ReferenciaPokemonDTO}.
	 */
	public ReferenciaPokemonDTO() {

	}

	/**
	 * Constructor parametrizado de la clase.
	 * Inicializa una nueva instancia de {@code ReferenciaPokemonDTO} asignando el nombre y la URL de referencia.
	 *
	 * @param nombre El nombre oficial del Pokémon.
	 * @param url    La dirección URL para consultar los detalles del recurso.
	 */
	public ReferenciaPokemonDTO(String nombre, String url) {
		super();
		this.nombre = nombre;
		this.url = url;
	}

	/**
	 * Obtiene el nombre del Pokémon de referencia.
	 *
	 * @return Una cadena de caracteres con el nombre oficial del Pokémon.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del Pokémon de referencia.
	 *
	 * @param nombre La nueva cadena de caracteres con el nombre a asignar.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la dirección URL del recurso detallado del Pokémon.
	 *
	 * @return Una cadena de caracteres con la URL de consulta.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Establece la dirección URL del recurso detallado del Pokémon.
	 *
	 * @param url La nueva cadena de caracteres con la URL a asignar.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva que detalla los valores de {@code nombre} y {@code url}.
	 */
	@Override
	public String toString() {
		return "ReferenciaPokemonDTO [nombre=" + nombre + ", url=" + url + "]";
	}

}