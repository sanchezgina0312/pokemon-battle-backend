package co.edu.unbosque.pokemon.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa un registro de auditoría dentro del sistema.
 * <p>
 * Permite almacenar información sobre las acciones realizadas
 * por los usuarios, incluyendo su rol, la entidad afectada
 * y la fecha en la que ocurrió la acción.
 * </p>
 *
 * @version 1.0
 */
@Entity
@Table(name = "auditoria")
public class Auditoria {

	/**
	 * Identificador único del registro de auditoría.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

	/**
	 * Identificador del usuario que realizó la acción.
	 */
	private Long idUsuario;

	/**
	 * Correo electrónico del usuario.
	 */
	private String correo;

	/**
	 * Rol del usuario que realizó la acción.
	 */
	private String rol;

	/**
	 * Acción realizada dentro del sistema.
	 */
	private String accion;

	/**
	 * Entidad afectada por la acción.
	 */
	private String entidad;

	/**
	 * Fecha y hora en la que ocurrió la acción.
	 */
	private LocalDateTime fecha;

	/**
	 * Constructor vacío requerido por JPA.
	 */
	public Auditoria() {
	}

	/**
	 * Constructor con parámetros.
	 *
	 * @param idUsuario identificador del usuario.
	 * @param correo correo del usuario.
	 * @param rol rol del usuario.
	 * @param accion acción realizada.
	 * @param entidad entidad afectada.
	 * @param fecha fecha y hora de la acción.
	 */
	public Auditoria(Long idUsuario, String correo, String rol,
			String accion, String entidad, LocalDateTime fecha) {

		this.idUsuario = idUsuario;
		this.correo = correo;
		this.rol = rol;
		this.accion = accion;
		this.entidad = entidad;
		this.fecha = fecha;
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
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el identificador del usuario.
	 *
	 * @param idUsuario nuevo identificador del usuario.
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el correo del usuario.
	 *
	 * @return correo electrónico.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo del usuario.
	 *
	 * @param correo nuevo correo electrónico.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene el rol del usuario.
	 *
	 * @return rol del usuario.
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Establece el rol del usuario.
	 *
	 * @param rol nuevo rol.
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Obtiene la acción realizada.
	 *
	 * @return acción registrada.
	 */
	public String getAccion() {
		return accion;
	}

	/**
	 * Establece la acción realizada.
	 *
	 * @param accion nueva acción.
	 */
	public void setAccion(String accion) {
		this.accion = accion;
	}

	/**
	 * Obtiene la entidad afectada.
	 *
	 * @return entidad afectada.
	 */
	public String getEntidad() {
		return entidad;
	}

	/**
	 * Establece la entidad afectada.
	 *
	 * @param entidad nueva entidad afectada.
	 */
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	/**
	 * Obtiene la fecha y hora del registro.
	 *
	 * @return fecha del evento.
	 */
	public LocalDateTime getFecha() {
		return fecha;
	}

	/**
	 * Establece la fecha y hora del registro.
	 *
	 * @param fecha nueva fecha del evento.
	 */
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	/**
	 * Genera el código hash del objeto.
	 *
	 * @return valor hash del registro.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(idUsuario, correo, rol, accion, entidad, fecha, id);
	}

	/**
	 * Compara dos objetos Auditoria.
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

		Auditoria other = (Auditoria) obj;

		return Objects.equals(idUsuario, other.idUsuario)
				&& Objects.equals(correo, other.correo)
				&& Objects.equals(rol, other.rol)
				&& Objects.equals(accion, other.accion)
				&& Objects.equals(entidad, other.entidad)
				&& Objects.equals(fecha, other.fecha)
				&& id == other.id;
	}

	/**
	 * Retorna una representación en texto del registro de auditoría.
	 *
	 * @return cadena con la información del registro.
	 */
	@Override
	public String toString() {
		return "Auditoria [id=" + id
				+ ", idUsuario=" + idUsuario
				+ ", correo=" + correo
				+ ", rol=" + rol
				+ ", accion=" + accion
				+ ", entidad=" + entidad
				+ ", fecha=" + fecha + "]";
	}
}