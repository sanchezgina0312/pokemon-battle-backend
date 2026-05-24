package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Objeto de Transferencia de Datos (DTO) que representa la especie de un Pokémon.
 * <p>
 * Se utiliza principalmente en el proceso de deserialización de datos JSON provenientes 
 * de la PokeAPI, actuando como un contenedor para agrupar todas las entradas de texto 
 * informativo e histórico (Pokédex) que posee un Pokémon en sus diferentes versiones e idiomas.
 * </p>
 * 
 * @version 1.0
 */
public class EspeciePokemonDTO {

	/**
	 * Listado de todas las descripciones históricas y de ambientación de la especie.
	 * <p>
	 * La anotación {@code @SerializedName("flavor_text_entries")} mapea este atributo 
	 * con el arreglo "flavor_text_entries" del JSON externo procesado por Gson.
	 * </p>
	 */
	@SerializedName("flavor_text_entries")
	private List<DescripcionDTO> listaDescripciones;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code EspeciePokemonDTO}.
	 */
	public EspeciePokemonDTO() {

	}

	/**
	 * Constructor parametrizado de la clase.
	 * Inicializa una nueva instancia de {@code EspeciePokemonDTO} asignando la lista de descripciones provista.
	 *
	 * @param listaDescripciones Una lista de objetos {@link DescripcionDTO} con las entradas de texto.
	 */
	public EspeciePokemonDTO(List<DescripcionDTO> listaDescripciones) {
		super();
		this.listaDescripciones = listaDescripciones;
	}

	/**
	 * Obtiene la lista de descripciones asociadas a la especie de este Pokémon.
	 *
	 * @return Un {@link List} que contiene los objetos {@link DescripcionDTO}.
	 */
	public List<DescripcionDTO> getListaDescripciones() {
		return listaDescripciones;
	}

	/**
	 * Establece o modifica la lista de descripciones asociadas a la especie de este Pokémon.
	 *
	 * @param listaDescripciones La nueva lista de {@link DescripcionDTO} para asignar.
	 */
	public void setListaDescripciones(List<DescripcionDTO> listaDescripciones) {
		this.listaDescripciones = listaDescripciones;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva que detalla los elementos de la {@code listaDescripciones}.
	 */
	@Override
	public String toString() {
		return "EspeciePokemonDTO [listaDescripciones=" + listaDescripciones + "]";
	}

}