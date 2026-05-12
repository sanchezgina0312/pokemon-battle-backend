package co.edu.unbosque.pokemon.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	private String nombre;
	private String contrasenia;
	private String correo;
	private String rol;
	private String idiomaPreferido;
	private int dinero;

	public Usuario() {

	}

	public Usuario(String nombre, String contrasenia, String correo, String rol, String idiomaPreferido, int dinero) {
		super();
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.correo = correo;
		this.rol = rol;
		this.idiomaPreferido = idiomaPreferido;
		this.dinero = dinero;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
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

	public String getIdiomaPreferido() {
		return idiomaPreferido;
	}

	public void setIdiomaPreferido(String idiomaPreferido) {
		this.idiomaPreferido = idiomaPreferido;
	}

	public int getDinero() {
		return dinero;
	}

	public void setDinero(int dinero) {
		this.dinero = dinero;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contrasenia, correo, dinero, id, idiomaPreferido, nombre, rol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(contrasenia, other.contrasenia) && Objects.equals(correo, other.correo)
				&& dinero == other.dinero && id == other.id && Objects.equals(idiomaPreferido, other.idiomaPreferido)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(rol, other.rol);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", contrasenia=" + contrasenia + ", correo=" + correo
				+ ", rol=" + rol + ", idiomaPreferido=" + idiomaPreferido + ", dinero=" + dinero + "]";
	}

}