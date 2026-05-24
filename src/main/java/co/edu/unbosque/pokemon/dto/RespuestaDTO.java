package co.edu.unbosque.pokemon.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que actúa como envoltura para las respuestas de listados de la API.
 * <p>
 * Se utiliza en la capa de integración para deserializar el nodo raíz de las respuestas paginadas 
 * provenientes de la PokeAPI, capturando la colección central de registros y abstrayéndolos en una 
 * estructura de datos manejable para el sistema local.
 * </p>
 * 
 * @version 1.0
 */
public class RespuestaDTO {

	/**
	 * Listado estructurado que contiene los resultados indexados y simplificados de la consulta.
	 * <p>
	 * La anotación {@code @SerializedName("results")} vincula este atributo con el arreglo 
	 * "results" del JSON externo procesado por la librería Gson.
	 * </p>
	 */
	@SerializedName("results")
	private List<ReferenciaPokemonDTO> listaResultados;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code RespuestaDTO}.
	 */
	public RespuestaDTO() {

	}

	/**
	 * Constructor parametrizado de la clase.
	 * Inicializa una nueva instancia de {@code RespuestaDTO} asignando la lista de resultados de referencia.
	 *
	 * @param listaResultados Una colección de tipo {@link List} que contiene los objetos {@link ReferenciaPokemonDTO}.
	 */
	public RespuestaDTO(List<ReferenciaPokemonDTO> listaResultados) {
		super();
		this.listaResultados = listaResultados;
	}

	/**
	 * Obtiene la lista de resultados y referencias devueltas por la consulta.
	 *
	 * @return Un {@link List} conteniendo instancias de {@link ReferenciaPokemonDTO}.
	 */
	public List<ReferenciaPokemonDTO> getListaResultados() {
		return listaResultados;
	}

	/**
	 * Establece o modifica la lista de resultados de la respuesta.
	 *
	 * @param listaResultados La nueva lista de {@link ReferenciaPokemonDTO} a asignar.
	 */
	public void setListaResultados(List<ReferenciaPokemonDTO> listaResultados) {
		this.listaResultados = listaResultados;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva que detalla los elementos integrados en {@code listaResultados}.
	 */
	@Override
	public String toString() {
		return "RespuestaDTO [listaResultados=" + listaResultados + "]";
	}

}