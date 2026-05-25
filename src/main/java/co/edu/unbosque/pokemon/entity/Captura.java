package co.edu.unbosque.pokemon.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad que representa la captura de un Pokémon realizada por un usuario.
 * <p>
 * Almacena información sobre el Pokémon capturado,
 * el usuario que realizó la captura y la fecha
 * en la que ocurrió el evento.
 * </p>
 *
 * @version 1.0
 */
@Entity
public class Captura {

	/**
	 * Identificador único de la captura.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

	/**
	 * Identificador del usuario que realizó la captura.
	 */
	private long idUsuario;

	/**
	 * Identificador del Pokémon en la PokéAPI.
	 */
	private int pokeApiId;

	/**
	 * Nombre del Pokémon capturado.
	 */
	private String nombrePokemon;

	/**
	 * Fecha y hora de la captura.
	 */
	private LocalDateTime fechaCaptura;

	/**
	 * Constructor vacío requerido por JPA.
	 */
	public Captura() {

	}

	/**
	 * Constructor con parámetros.
	 *
	 * @param idUsuario identificador del usuario.
	 * @param pokeApiId identificador del Pokémon en la PokéAPI.
	 * @param nombrePokemon nombre del Pokémon capturado.
	 * @param fechaCaptura fecha y hora de la captura.
	 */
	public Captura(long idUsuario, int pokeApiId,
			String nombrePokemon, LocalDateTime fechaCaptura) {

		super();
		this.idUsuario = idUsuario;
		this.pokeApiId = pokeApiId;
		this.nombrePokemon = nombrePokemon;
		this.fechaCaptura = fechaCaptura;
	}

	/**
	 * Obtiene el identificador de la captura.
	 *
	 * @return identificador de la captura.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador de la captura.
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
	 * Obtiene el identificador del Pokémon en la PokéAPI.
	 *
	 * @return identificador del Pokémon.
	 */
	public int getPokeApiId() {
		return pokeApiId;
	}

	/**
	 * Establece el identificador del Pokémon en la PokéAPI.
	 *
	 * @param pokeApiId nuevo identificador del Pokémon.
	 */
	public void setPokeApiId(int pokeApiId) {
		this.pokeApiId = pokeApiId;
	}

	/**
	 * Obtiene el nombre del Pokémon capturado.
	 *
	 * @return nombre del Pokémon.
	 */
	public String getNombrePokemon() {
		return nombrePokemon;
	}

	/**
	 * Establece el nombre del Pokémon capturado.
	 *
	 * @param nombrePokemon nuevo nombre del Pokémon.
	 */
	public void setNombrePokemon(String nombrePokemon) {
		this.nombrePokemon = nombrePokemon;
	}

	/**
	 * Obtiene la fecha y hora de la captura.
	 *
	 * @return fecha de captura.
	 */
	public LocalDateTime getFechaCaptura() {
		return fechaCaptura;
	}

	/**
	 * Establece la fecha y hora de la captura.
	 *
	 * @param fechaCaptura nueva fecha de captura.
	 */
	public void setFechaCaptura(LocalDateTime fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}

	/**
	 * Genera el código hash del objeto.
	 *
	 * @return valor hash de la captura.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(fechaCaptura, id, idUsuario, nombrePokemon, pokeApiId);
	}

	/**
	 * Compara dos objetos Captura.
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

		Captura other = (Captura) obj;

		return Objects.equals(fechaCaptura, other.fechaCaptura)
				&& id == other.id
				&& idUsuario == other.idUsuario
				&& Objects.equals(nombrePokemon, other.nombrePokemon)
				&& pokeApiId == other.pokeApiId;
	}

	/**
	 * Retorna una representación en texto de la captura.
	 *
	 * @return cadena con la información de la captura.
	 */
	@Override
	public String toString() {
		return "Captura [id=" + id
				+ ", idUsuario=" + idUsuario
				+ ", pokeApiId=" + pokeApiId
				+ ", nombrePokemon=" + nombrePokemon
				+ ", fechaCaptura=" + fechaCaptura + "]";
	}

}