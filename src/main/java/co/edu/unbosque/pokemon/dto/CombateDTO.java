package co.edu.unbosque.pokemon.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Objeto de Transferencia de Datos (DTO) que representa el registro de un combate Pokémon.
 * <p>
 * Esta clase se encarga de transportar la información histórica y los resultados de las 
 * batallas entre la capa de datos, la lógica de negocio y los controladores REST que exponen 
 * el historial de peleas de los usuarios.
 * </p>
 * 
 * @version 1.0
 */
public class CombateDTO {

	/**
	 * Identificador único y auto-incremental del registro del combate en la base de datos local.
	 */
	private long id;
	
	/**
	 * Identificador único del usuario (entrenador) que participó en el combate.
	 */
	private long idUsuarioJugador;
	
	/**
	 * ID de la especie del Pokémon aliado en la API externa (PokeAPI).
	 */
	private int idPokeApiAliado;
	
	/**
	 * ID de la especie del Pokémon rival en la API externa (PokeAPI).
	 */
	private int idPokeApiRival;
	
	/**
	 * Puntos de salud (HP) con los que terminó el Pokémon aliado al finalizar el enfrentamiento.
	 */
	private int saludFinalAliado;
	
	/**
	 * Puntos de salud (HP) con los que terminó el Pokémon rival al finalizar el enfrentamiento.
	 */
	private int saludFinalRival;
	
	/**
	 * El desenlace o conclusión de la batalla (ej. "VICTORIA", "DERROTA", "EMPATE").
	 */
	private String resultado;
	
	/**
	 * La fecha y hora exacta en la que se llevó a cabo el combate.
	 */
	private LocalDateTime fechaCombate;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code CombateDTO}.
	 */
	public CombateDTO() {

	}

	/**
	 * Constructor parametrizado de la clase (excluyendo el ID autogenerado).
	 * Permite instanciar un nuevo {@code CombateDTO} con todos los detalles finales de la batalla.
	 *
	 * @param idUsuarioJugador  El identificador único del entrenador.
	 * @param idPokeApiAliado   El ID de PokeAPI del Pokémon propio.
	 * @param idPokeApiRival    El ID de PokeAPI del Pokémon oponente.
	 * @param saludFinalAliado  Los puntos de salud restantes del Pokémon propio.
	 * @param saludFinalRival   Los puntos de salud restantes del Pokémon oponente.
	 * @param resultado         El veredicto final de la pelea.
	 * @param fechaCombate      La marca de tiempo del enfrentamiento.
	 */
	public CombateDTO(long idUsuarioJugador, int idPokeApiAliado, int idPokeApiRival, int saludFinalAliado,
			int saludFinalRival, String resultado, LocalDateTime fechaCombate) {
		super();
		this.idUsuarioJugador = idUsuarioJugador;
		this.idPokeApiAliado = idPokeApiAliado;
		this.idPokeApiRival = idPokeApiRival;
		this.saludFinalAliado = saludFinalAliado;
		this.saludFinalRival = saludFinalRival;
		this.resultado = resultado;
		this.fechaCombate = fechaCombate;
	}

	/**
	 * Obtiene el identificador único del registro del combate.
	 *
	 * @return El ID de la batalla en base de datos.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del registro del combate.
	 *
	 * @param id El nuevo ID para asignar al registro.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del usuario que disputó el encuentro.
	 *
	 * @return El ID del jugador.
	 */
	public long getIdUsuarioJugador() {
		return idUsuarioJugador;
	}

	/**
	 * Establece el identificador del usuario que disputó el encuentro.
	 *
	 * @param idUsuarioJugador El nuevo ID del jugador a asignar.
	 */
	public void setIdUsuarioJugador(long idUsuarioJugador) {
		this.idUsuarioJugador = idUsuarioJugador;
	}

	/**
	 * Obtiene el ID de PokeAPI correspondiente al Pokémon aliado.
	 *
	 * @return El ID numérico de la especie aliada.
	 */
	public int getIdPokeApiAliado() {
		return idPokeApiAliado;
	}

	/**
	 * Establece el ID de PokeAPI correspondiente al Pokémon aliado.
	 *
	 * @param idPokeApiAliado El nuevo ID de la especie aliada a asignar.
	 */
	public void setIdPokeApiAliado(int idPokeApiAliado) {
		this.idPokeApiAliado = idPokeApiAliado;
	}

	/**
	 * Obtiene el ID de PokeAPI correspondiente al Pokémon rival.
	 *
	 * @return El ID numérico de la especie oponente.
	 */
	public int getIdPokeApiRival() {
		return idPokeApiRival;
	}

	/**
	 * Establece el ID de PokeAPI correspondiente al Pokémon rival.
	 *
	 * @param idPokeApiRival El nuevo ID de la especie oponente a asignar.
	 */
	public void setIdPokeApiRival(int idPokeApiRival) {
		this.idPokeApiRival = idPokeApiRival;
	}

	/**
	 * Obtiene la salud final con la que concluyó el Pokémon aliado.
	 *
	 * @return Los puntos de salud finales del aliado.
	 */
	public int getSaludFinalAliado() {
		return saludFinalAliado;
	}

	/**
	 * Establece la salud final con la que concluyó el Pokémon aliado.
	 *
	 * @param saludFinalAliado Los nuevos puntos de salud finales del aliado.
	 */
	public void setSaludFinalAliado(int saludFinalAliado) {
		this.saludFinalAliado = saludFinalAliado;
	}

	/**
	 * Obtiene la salud final con la que concluyó el Pokémon rival.
	 *
	 * @return Los puntos de salud finales del rival.
	 */
	public int getSaludFinalRival() {
		return saludFinalRival;
	}

	/**
	 * Establece la salud final con la que concluyó el Pokémon rival.
	 *
	 * @param saludFinalRival Los nuevos puntos de salud finales del rival.
	 */
	public void setSaludFinalRival(int saludFinalRival) {
		this.saludFinalRival = saludFinalRival;
	}

	/**
	 * Obtiene la cadena de texto con la conclusión o veredicto del combate.
	 *
	 * @return El resultado del encuentro.
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * Establece la conclusión o veredicto del combate.
	 *
	 * @param resultado El nuevo resultado a asignar (ej. "VICTORIA").
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	/**
	 * Obtiene la fecha y hora en la que se disputó el encuentro.
	 *
	 * @return Un objeto {@link LocalDateTime} con la marca temporal de la batalla.
	 */
	public LocalDateTime getFechaCombate() {
		return fechaCombate;
	}

	/**
	 * Establece la fecha y hora en la que se disputó el encuentro.
	 *
	 * @param fechaCombate La nueva fecha y hora a asignar.
	 */
	public void setFechaCombate(LocalDateTime fechaCombate) {
		this.fechaCombate = fechaCombate;
	}

	/**
	 * Genera un código hash único para la instancia actual basado en sus campos.
	 *
	 * @return El código hash calculado para este objeto.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(fechaCombate, id, idPokeApiAliado, idPokeApiRival, idUsuarioJugador, resultado,
				saludFinalAliado, saludFinalRival);
	}

	/**
	 * Compara de forma estructural la igualdad de este objeto frente a otro.
	 * <p>
	 * Dos instancias se evalúan como iguales si y solo si coinciden plenamente en todos sus
	 * identificadores, estadísticas finales de salud, texto de resultado y marcas temporales.
	 * </p>
	 *
	 * @param obj El objeto con el cual realizar la comparación.
	 * @return {@code true} si los objetos son estructuralmente equivalentes; {@code false} en caso contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CombateDTO other = (CombateDTO) obj;
		return Objects.equals(fechaCombate, other.fechaCombate) && id == other.id
				&& idPokeApiAliado == other.idPokeApiAliado && idPokeApiRival == other.idPokeApiRival
				&& idUsuarioJugador == other.idUsuarioJugador && Objects.equals(resultado, other.resultado)
				&& saludFinalAliado == other.saludFinalAliado && saludFinalRival == other.saludFinalRival;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva de los valores de la instancia.
	 */
	@Override
	public String toString() {
		return "CombateDTO [id=" + id + ", idUsuarioJugador=" + idUsuarioJugador + ", idPokeApiAliado="
				+ idPokeApiAliado + ", idPokeApiRival=" + idPokeApiRival + ", saludFinalAliado=" + saludFinalAliado
				+ ", saludFinalRival=" + saludFinalRival + ", resultado=" + resultado + ", fechaCombate=" + fechaCombate
				+ "]";
	}

}