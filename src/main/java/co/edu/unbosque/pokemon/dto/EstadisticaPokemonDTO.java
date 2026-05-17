package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que representa el valor numérico de una estadística base de un Pokémon.
 * <p>
 * Se utiliza en la capa de integración con servicios externos para mapear y deserializar los componentes
 * individuales del arreglo de estadísticas (como puntos de salud, ataque, defensa, entre otros) provenientes de la PokeAPI.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
public class EstadisticaPokemonDTO {

	/**
	 * El valor numérico base correspondiente a la estadística específica del Pokémon.
	 * <p>
	 * La anotación {@code @SerializedName("base_stat")} vincula este campo con la propiedad 
	 * "base_stat" del JSON externo procesado por la librería Gson.
	 * </p>
	 */
	@SerializedName("base_stat")
	private int valorDeLaEstadistica;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code EstadisticaPokemonDTO}.
	 */
	public EstadisticaPokemonDTO() {

	}

	/**
	 * Constructor parametrizado de la clase.
	 * Inicializa una nueva instancia de {@code EstadisticaPokemonDTO} asignando el valor numérico base provisto.
	 *
	 * @param valorDeLaEstadistica El valor entero de la estadística base.
	 */
	public EstadisticaPokemonDTO(int valorDeLaEstadistica) {
		super();
		this.valorDeLaEstadistica = valorDeLaEstadistica;
	}

	/**
	 * Obtiene el valor numérico de la estadística base del Pokémon.
	 *
	 * @return Un entero que representa el valor de la estadística.
	 */
	public int getValorDeLaEstadistica() {
		return valorDeLaEstadistica;
	}

	/**
	 * Establece o modifica el valor numérico de la estadística base del Pokémon.
	 *
	 * @param valorDeLaEstadistica El nuevo valor entero a asignar.
	 */
	public void setValorDeLaEstadistica(int valorDeLaEstadistica) {
		this.valorDeLaEstadistica = valorDeLaEstadistica;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva que detalla el valor de {@code valorDeLaEstadistica}.
	 */
	@Override
	public String toString() {
		return "EstadisticaPokemonDTO [valorDeLaEstadistica=" + valorDeLaEstadistica + "]";
	}

}