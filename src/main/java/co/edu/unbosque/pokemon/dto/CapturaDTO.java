package co.edu.unbosque.pokemon.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Objeto de Transferencia de Datos (DTO) que representa el registro de una captura de Pokémon.
 * <p>
 * Esta clase se utiliza para transportar los datos históricos de los Pokémon atrapados 
 * por los usuarios entre la capa de persistencia, los servicios de negocio y las respuestas 
 * de la API REST.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
public class CapturaDTO {

	/**
	 * Identificador único y auto-incremental del registro de la captura en la base de datos local.
	 */
	private long id;
	
	/**
	 * Identificador único del usuario (entrenador) que realizó la captura.
	 */
	private long idUsuario;
	
	/**
	 * El ID numérico correspondiente a la especie del Pokémon en la API externa (PokeAPI).
	 */
	private int pokeApiId;
	
	/**
	 * El nombre oficial de la especie del Pokémon capturado.
	 */
	private String nombrePokemon;
	
	/**
	 * La fecha y hora exacta en la que se consolidó el evento de la captura.
	 */
	private LocalDateTime fechaCaptura;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code CapturaDTO}.
	 */
	public CapturaDTO() {

	}

	/**
	 * Constructor parametrizado de la clase (excluyendo el ID autogenerado).
	 * Permite instanciar un nuevo {@code CapturaDTO} listo para ser procesado y guardado en el sistema.
	 *
	 * @param idUsuario     El identificador único del usuario propietario.
	 * @param pokeApiId     El ID de la especie en la PokeAPI.
	 * @param nombrePokemon El nombre oficial del Pokémon.
	 * @param fechaCaptura  La marca de tiempo de la captura.
	 */
	public CapturaDTO(long idUsuario, int pokeApiId, String nombrePokemon, LocalDateTime fechaCaptura) {
		super();
		this.idUsuario = idUsuario;
		this.pokeApiId = pokeApiId;
		this.nombrePokemon = nombrePokemon;
		this.fechaCaptura = fechaCaptura;
	}

	/**
	 * Obtiene el identificador único del registro de captura.
	 *
	 * @return El ID de la captura.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del registro de captura.
	 *
	 * @param id El nuevo ID para asignar al registro.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del usuario que realizó la captura.
	 *
	 * @return El ID del usuario propietario.
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el identificador del usuario que realizó la captura.
	 *
	 * @param idUsuario El nuevo ID del usuario a asignar.
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el ID asignado a la especie de este Pokémon en la PokeAPI.
	 *
	 * @return El ID numérico de PokeAPI.
	 */
	public int getPokeApiId() {
		return pokeApiId;
	}

	/**
	 * Establece el ID de la especie de este Pokémon correspondiente a la PokeAPI.
	 *
	 * @param pokeApiId El nuevo ID de PokeAPI a asignar.
	 */
	public void setPokeApiId(int pokeApiId) {
		this.pokeApiId = pokeApiId;
	}

	/**
	 * Obtiene el nombre oficial del Pokémon capturado.
	 *
	 * @return Una cadena con el nombre del Pokémon.
	 */
	public String getNombrePokemon() {
		return nombrePokemon;
	}

	/**
	 * Establece el nombre oficial del Pokémon capturado.
	 *
	 * @param nombrePokemon El nuevo nombre del Pokémon a asignar.
	 */
	public void setNombrePokemon(String nombrePokemon) {
		this.nombrePokemon = nombrePokemon;
	}

	/**
	 * Obtiene la fecha y hora en la que se efectuó la captura.
	 *
	 * @return Un objeto {@link LocalDateTime} con la estampa de tiempo del evento.
	 */
	public LocalDateTime getFechaCaptura() {
		return fechaCaptura;
	}

	/**
	 * Establece la fecha y hora en la que se efectuó la captura.
	 *
	 * @param fechaCaptura La nueva fecha y hora a asignar.
	 */
	public void setFechaCaptura(LocalDateTime fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}

	/**
	 * Genera un código hash único para la instancia actual basado en sus campos.
	 *
	 * @return El código hash calculado para este objeto.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(fechaCaptura, id, idUsuario, nombrePokemon, pokeApiId);
	}

	/**
	 * Compara de forma estructural la igualdad de este objeto frente a otro.
	 * <p>
	 * Dos instancias se evalúan como iguales si y solo si coinciden plenamente en sus valores 
	 * de {@code id}, {@code idUsuario}, {@code pokeApiId}, {@code nombrePokemon} y {@code fechaCaptura}.
	 * </p>
	 *
	 * @param obj El objeto con el cual realizar la comparación.
	 * @return {@code true} si los objetos son idénticos o equivalentes; {@code false} en caso contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CapturaDTO other = (CapturaDTO) obj;
		return Objects.equals(fechaCaptura, other.fechaCaptura) && id == other.id && idUsuario == other.idUsuario
				&& Objects.equals(nombrePokemon, other.nombrePokemon) && pokeApiId == other.pokeApiId;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva de los valores de la instancia.
	 */
	@Override
	public String toString() {
		return "CapturaDTO [id=" + id + ", idUsuario=" + idUsuario + ", pokeApiId=" + pokeApiId + ", nombrePokemon="
				+ nombrePokemon + ", fechaCaptura=" + fechaCaptura + "]";
	}

}