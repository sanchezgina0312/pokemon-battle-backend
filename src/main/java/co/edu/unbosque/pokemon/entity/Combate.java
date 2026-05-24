package co.edu.unbosque.pokemon.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad que representa un combate Pokémon dentro del sistema.
 * <p>
 * Almacena información sobre los Pokémon participantes,
 * el usuario jugador, el resultado del combate,
 * el modo de juego y la fecha en la que ocurrió.
 * </p>
 *
 * @version 1.0
 */
@Entity
public class Combate {

	/**
	 * Identificador único del combate.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

	/**
	 * Identificador del usuario jugador.
	 */
	private long idUsuarioJugador;

	/**
	 * Identificador del Pokémon aliado en la PokéAPI.
	 */
	private int idPokeApiAliado;

	/**
	 * Identificador del Pokémon rival en la PokéAPI.
	 */
	private int idPokeApiRival;

	/**
	 * Salud final del Pokémon aliado.
	 */
	private int saludFinalAliado;

	/**
	 * Salud final del Pokémon rival.
	 */
	private int saludFinalRival;

	/**
	 * Resultado del combate.
	 */
	private String resultado;

	/**
	 * Modo en el que se jugó el combate.
	 */
	private String modo;

	/**
	 * Fecha y hora del combate.
	 */
	private LocalDateTime fechaCombate;

	/**
	 * Constructor vacío requerido por JPA.
	 * <p>
	 * Inicializa automáticamente la fecha del combate
	 * con la fecha y hora actuales.
	 * </p>
	 */
	public Combate() {
		this.fechaCombate = LocalDateTime.now();
	}

	/**
	 * Constructor con parámetros.
	 *
	 * @param idUsuarioJugador identificador del usuario jugador.
	 * @param idPokeApiAliado identificador del Pokémon aliado.
	 * @param idPokeApiRival identificador del Pokémon rival.
	 * @param saludFinalAliado salud final del Pokémon aliado.
	 * @param saludFinalRival salud final del Pokémon rival.
	 * @param resultado resultado del combate.
	 * @param modo modo de juego del combate.
	 * @param fechaCombate fecha y hora del combate.
	 */
	public Combate(long idUsuarioJugador,
			int idPokeApiAliado,
			int idPokeApiRival,
			int saludFinalAliado,
			int saludFinalRival,
			String resultado,
			String modo,
			LocalDateTime fechaCombate) {

		super();
		this.idUsuarioJugador = idUsuarioJugador;
		this.idPokeApiAliado = idPokeApiAliado;
		this.idPokeApiRival = idPokeApiRival;
		this.saludFinalAliado = saludFinalAliado;
		this.saludFinalRival = saludFinalRival;
		this.resultado = resultado;
		this.modo = modo;
		this.fechaCombate = fechaCombate;
	}

	/**
	 * Obtiene el identificador del combate.
	 *
	 * @return identificador del combate.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador del combate.
	 *
	 * @param id nuevo identificador.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del usuario jugador.
	 *
	 * @return identificador del usuario.
	 */
	public long getIdUsuarioJugador() {
		return idUsuarioJugador;
	}

	/**
	 * Establece el identificador del usuario jugador.
	 *
	 * @param idUsuarioJugador nuevo identificador del usuario.
	 */
	public void setIdUsuarioJugador(long idUsuarioJugador) {
		this.idUsuarioJugador = idUsuarioJugador;
	}

	/**
	 * Obtiene el identificador del Pokémon aliado.
	 *
	 * @return identificador del Pokémon aliado.
	 */
	public int getIdPokeApiAliado() {
		return idPokeApiAliado;
	}

	/**
	 * Establece el identificador del Pokémon aliado.
	 *
	 * @param idPokeApiAliado nuevo identificador del Pokémon aliado.
	 */
	public void setIdPokeApiAliado(int idPokeApiAliado) {
		this.idPokeApiAliado = idPokeApiAliado;
	}

	/**
	 * Obtiene el identificador del Pokémon rival.
	 *
	 * @return identificador del Pokémon rival.
	 */
	public int getIdPokeApiRival() {
		return idPokeApiRival;
	}

	/**
	 * Establece el identificador del Pokémon rival.
	 *
	 * @param idPokeApiRival nuevo identificador del Pokémon rival.
	 */
	public void setIdPokeApiRival(int idPokeApiRival) {
		this.idPokeApiRival = idPokeApiRival;
	}

	/**
	 * Obtiene la salud final del Pokémon aliado.
	 *
	 * @return salud final del aliado.
	 */
	public int getSaludFinalAliado() {
		return saludFinalAliado;
	}

	/**
	 * Establece la salud final del Pokémon aliado.
	 *
	 * @param saludFinalAliado nueva salud final.
	 */
	public void setSaludFinalAliado(int saludFinalAliado) {
		this.saludFinalAliado = saludFinalAliado;
	}

	/**
	 * Obtiene la salud final del Pokémon rival.
	 *
	 * @return salud final del rival.
	 */
	public int getSaludFinalRival() {
		return saludFinalRival;
	}

	/**
	 * Establece la salud final del Pokémon rival.
	 *
	 * @param saludFinalRival nueva salud final.
	 */
	public void setSaludFinalRival(int saludFinalRival) {
		this.saludFinalRival = saludFinalRival;
	}

	/**
	 * Obtiene el resultado del combate.
	 *
	 * @return resultado del combate.
	 */
	public String getResultado() {
		return resultado;
	}

	/**
	 * Establece el resultado del combate.
	 *
	 * @param resultado nuevo resultado.
	 */
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	/**
	 * Obtiene el modo del combate.
	 *
	 * @return modo de juego.
	 */
	public String getModo() {
		return modo;
	}

	/**
	 * Establece el modo del combate.
	 *
	 * @param modo nuevo modo de juego.
	 */
	public void setModo(String modo) {
		this.modo = modo;
	}

	/**
	 * Obtiene la fecha y hora del combate.
	 *
	 * @return fecha del combate.
	 */
	public LocalDateTime getFechaCombate() {
		return fechaCombate;
	}

	/**
	 * Establece la fecha y hora del combate.
	 *
	 * @param fechaCombate nueva fecha del combate.
	 */
	public void setFechaCombate(LocalDateTime fechaCombate) {
		this.fechaCombate = fechaCombate;
	}

	/**
	 * Genera el código hash del objeto.
	 *
	 * @return valor hash del combate.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(
				fechaCombate,
				id,
				idPokeApiAliado,
				idPokeApiRival,
				idUsuarioJugador,
				modo,
				resultado,
				saludFinalAliado,
				saludFinalRival
		);
	}

	/**
	 * Compara dos objetos Combate.
	 *
	 * @param obj objeto a comparar.
	 * @return {@code true} si los objetos son iguales,
	 *         {@code false} en caso contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Combate other = (Combate) obj;

		return Objects.equals(fechaCombate, other.fechaCombate)
				&& id == other.id
				&& idPokeApiAliado == other.idPokeApiAliado
				&& idPokeApiRival == other.idPokeApiRival
				&& idUsuarioJugador == other.idUsuarioJugador
				&& Objects.equals(modo, other.modo)
				&& Objects.equals(resultado, other.resultado)
				&& saludFinalAliado == other.saludFinalAliado
				&& saludFinalRival == other.saludFinalRival;
	}

	/**
	 * Retorna una representación en texto del combate.
	 *
	 * @return cadena con la información del combate.
	 */
	@Override
	public String toString() {
		return "Combate [id=" + id
				+ ", idUsuarioJugador=" + idUsuarioJugador
				+ ", idPokeApiAliado=" + idPokeApiAliado
				+ ", idPokeApiRival=" + idPokeApiRival
				+ ", saludFinalAliado=" + saludFinalAliado
				+ ", saludFinalRival=" + saludFinalRival
				+ ", resultado=" + resultado
				+ ", modo=" + modo
				+ ", fechaCombate=" + fechaCombate + "]";
	}

}