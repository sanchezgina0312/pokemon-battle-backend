package co.edu.unbosque.pokemon.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad que representa una visita realizada a un Centro Pokémon.
 * <p>
 * Almacena información sobre el usuario que realizó la visita,
 * el Pokémon que fue curado y la fecha en la que ocurrió la atención.
 * </p>
 *
 * @version 1.0
 */
@Entity
public class CentroPokemon {

	/**
	 * Identificador único del registro.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

	/**
	 * Identificador del usuario que realizó la visita.
	 */
	private long idUsuario;

	/**
	 * Identificador del Pokémon curado.
	 */
	private long idPokemonCurado;

	/**
	 * Fecha y hora de la visita al Centro Pokémon.
	 */
	private LocalDateTime fechaVisita;

	/**
	 * Constructor vacío requerido por JPA.
	 */
	public CentroPokemon() {
		
	}

	/**
	 * Constructor con parámetros.
	 *
	 * @param idUsuario identificador del usuario.
	 * @param idPokemonCurado identificador del Pokémon curado.
	 * @param fechaVisita fecha y hora de la visita.
	 */
	public CentroPokemon(long idUsuario,
			long idPokemonCurado,
			LocalDateTime fechaVisita) {

		super();
		this.idUsuario = idUsuario;
		this.idPokemonCurado = idPokemonCurado;
		this.fechaVisita = fechaVisita;
	}

	/**
	 * Obtiene el identificador del registro.
	 *
	 * @return identificador del registro.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador del registro.
	 *
	 * @param id nuevo identificador.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del usuario.
	 *
	 * @return identificador del usuario.
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el identificador del usuario.
	 *
	 * @param idUsuario nuevo identificador del usuario.
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el identificador del Pokémon curado.
	 *
	 * @return identificador del Pokémon.
	 */
	public long getIdPokemonCurado() {
		return idPokemonCurado;
	}

	/**
	 * Establece el identificador del Pokémon curado.
	 *
	 * @param idPokemonCurado nuevo identificador del Pokémon.
	 */
	public void setIdPokemonCurado(long idPokemonCurado) {
		this.idPokemonCurado = idPokemonCurado;
	}

	/**
	 * Obtiene la fecha y hora de la visita.
	 *
	 * @return fecha de visita.
	 */
	public LocalDateTime getFechaVisita() {
		return fechaVisita;
	}

	/**
	 * Establece la fecha y hora de la visita.
	 *
	 * @param fechaVisita nueva fecha de visita.
	 */
	public void setFechaVisita(LocalDateTime fechaVisita) {
		this.fechaVisita = fechaVisita;
	}

	/**
	 * Genera el código hash del objeto.
	 *
	 * @return valor hash del registro.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(fechaVisita, id, idPokemonCurado, idUsuario);
	}

	/**
	 * Compara dos objetos CentroPokemon.
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

		CentroPokemon other = (CentroPokemon) obj;

		return Objects.equals(fechaVisita, other.fechaVisita)
				&& id == other.id
				&& idPokemonCurado == other.idPokemonCurado
				&& idUsuario == other.idUsuario;
	}

	/**
	 * Retorna una representación en texto del registro.
	 *
	 * @return cadena con la información del Centro Pokémon.
	 */
	@Override
	public String toString() {
		return "CentroPokemon [id=" + id
				+ ", idUsuario=" + idUsuario
				+ ", idPokemonCurado=" + idPokemonCurado
				+ ", fechaVisita=" + fechaVisita + "]";
	}

}