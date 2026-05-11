package co.edu.unbosque.pokemon.dto;

import java.util.Objects;

public class UsuarioDTO {

	private long id;
	private String username;
	private String contrasenia;
	private String correo;
	private String rol;
	private String idiomaPreferido;
	private int dinero;
	
	public UsuarioDTO() {

	}

	public UsuarioDTO(long id, String username, String contrasenia, String correo, String rol, String idiomaPreferido,
			int dinero) {
		super();
		this.id = id;
		this.username = username;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		return Objects.hash(contrasenia, correo, dinero, id, idiomaPreferido, rol, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDTO other = (UsuarioDTO) obj;
		return Objects.equals(contrasenia, other.contrasenia) && Objects.equals(correo, other.correo)
				&& dinero == other.dinero && id == other.id && Objects.equals(idiomaPreferido, other.idiomaPreferido)
				&& Objects.equals(rol, other.rol) && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", username=" + username + ", contrasenia=" + contrasenia + ", correo=" + correo
				+ ", rol=" + rol + ", idiomaPreferido=" + idiomaPreferido + ", dinero=" + dinero + "]";
	}
	
}
