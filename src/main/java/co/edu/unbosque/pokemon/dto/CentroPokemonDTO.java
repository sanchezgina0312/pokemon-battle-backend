package co.edu.unbosque.pokemon.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Objeto de Transferencia de Datos (DTO) que representa el registro de una visita al Centro Pokémon.
 * <p>
 * Se utiliza para transportar los datos del historial de atención y curación de los Pokémon 
 * pertenecientes a los usuarios, facilitando el flujo de información entre las capas de persistencia, 
 * lógica de negocio y presentación.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
public class CentroPokemonDTO {

	/**
	 * Identificador único y auto-incremental del registro de atención en la base de datos local.
	 */
	private long id;
	
	/**
	 * Identificador único del usuario (entrenador) que acudió al Centro Pokémon.
	 */
	private long idUsuario;
	
	/**
	 * Identificador único del Pokémon específico que recibió la atención médica o curación.
	 */
	private long idPokemonCurado;
	
	/**
	 * La fecha y hora exacta en la que se consolidó la visita y el proceso de restauración de salud.
	 */
	private LocalDateTime fechaVisita;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code CentroPokemonDTO}.
	 */
	public CentroPokemonDTO() {

	}

	/**
	 * Constructor parametrizado de la clase (excluyendo el ID autogenerado).
	 * Permite instanciar un nuevo {@code CentroPokemonDTO} con los datos requeridos para ser persistidos.
	 *
	 * @param idUsuario        El identificador único del usuario que solicita la curación.
	 * @param idPokemonCurado  El identificador único del Pokémon que será restaurado.
	 * @param fechaVisita      La marca de tiempo que indica cuándo se realiza la atención.
	 */
	public CentroPokemonDTO(long idUsuario, long idPokemonCurado, LocalDateTime fechaVisita) {
		super();
		this.idUsuario = idUsuario;
		this.idPokemonCurado = idPokemonCurado;
		this.fechaVisita = fechaVisita;
	}

	/**
	 * Obtiene el identificador único del registro de la visita.
	 *
	 * @return El ID de la transacción médica.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del registro de la visita.
	 *
	 * @param id El nuevo ID para asignar al registro.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del usuario que asistió al centro.
	 *
	 * @return El ID del usuario o entrenador.
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el identificador del usuario que asistió al centro.
	 *
	 * @param idUsuario El nuevo ID del usuario a asignar.
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el identificador único del Pokémon que fue curado.
	 *
	 * @return El ID local del Pokémon restaurado.
	 */
	public long getIdPokemonCurado() {
		return idPokemonCurado;
	}

	/**
	 * Establece el identificador único del Pokémon que fue curado.
	 *
	 * @param idPokemonCurado El nuevo ID del Pokémon a asignar.
	 */
	public void setIdPokemonCurado(long idPokemonCurado) {
		this.idPokemonCurado = idPokemonCurado;
	}

	/**
	 * Obtiene la fecha y hora en la que se efectuó la curación.
	 *
	 * @return Un objeto {@link LocalDateTime} con la estampa de tiempo del evento.
	 */
	public LocalDateTime getFechaVisita() {
		return fechaVisita;
	}

	/**
	 * Establece la fecha y hora en la que se efectuó la curación.
	 *
	 * @param fechaVisita La nueva fecha y hora de la visita a asignar.
	 */
	public void setFechaVisita(LocalDateTime fechaVisita) {
		this.fechaVisita = fechaVisita;
	}

	/**
	 * Genera un código hash único para la instancia actual basado en sus campos.
	 *
	 * @return El código hash calculado para este objeto.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(fechaVisita, id, idPokemonCurado, idUsuario);
	}

	/**
	 * Compara de forma estructural la igualdad de este objeto frente a otro.
	 * <p>
	 * Dos instancias se evalúan como iguales si y solo si coinciden plenamente en sus valores 
	 * de {@code id}, {@code idUsuario}, {@code idPokemonCurado} y {@code fechaVisita}.
	 * </p>
	 *
	 * @param obj El objeto con el cual realizar la comparación.
	 * @return {@code true} si los objetos son estructuralmente idénticos; {@code false} en caso contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CentroPokemonDTO other = (CentroPokemonDTO) obj;
		return Objects.equals(fechaVisita, other.fechaVisita) && id == other.id
				&& idPokemonCurado == other.idPokemonCurado && idUsuario == other.idUsuario;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva de los valores de la instancia.
	 */
	@Override
	public String toString() {
		return "CentroPokemonDTO [id=" + id + ", idUsuario=" + idUsuario + ", idPokemonCurado=" + idPokemonCurado
				+ ", fechaVisita=" + fechaVisita + "]";
	}

}