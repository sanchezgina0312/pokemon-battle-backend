package co.edu.unbosque.pokemon.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "auditoria")
public class Auditoria {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	private Long idUsuario;
	private String correo;
	private String rol;
	private String accion;
	private String entidad;
	private LocalDateTime fecha;

	public Auditoria() {
	}

	public Auditoria(Long idUsuario, String correo, String rol, String accion, String entidad, LocalDateTime fecha) {
		this.idUsuario = idUsuario;
		this.correo = correo;
		this.rol = rol;
		this.accion = accion;
		this.entidad = entidad;
		this.fecha = fecha;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idUsuario, correo, rol, accion, entidad, fecha, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Auditoria other = (Auditoria) obj;
		return Objects.equals(idUsuario, other.idUsuario) && Objects.equals(correo, other.correo)
				&& Objects.equals(rol, other.rol) && Objects.equals(accion, other.accion)
				&& Objects.equals(entidad, other.entidad) && Objects.equals(fecha, other.fecha) && id == other.id;
	}

	@Override
	public String toString() {
		return "Auditoria [id=" + id + ", idUsuario=" + idUsuario + ", correo=" + correo + ", rol=" + rol + ", accion="
				+ accion + ", entidad=" + entidad + ", fecha=" + fecha + "]";
	}
}