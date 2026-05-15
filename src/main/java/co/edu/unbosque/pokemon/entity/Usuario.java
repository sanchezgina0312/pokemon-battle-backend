package co.edu.unbosque.pokemon.entity;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	private String nombre;
	private String contrasenia;
	@Column(unique = true)
	private String correo;
	@Enumerated(EnumType.STRING)
	private Role rol;
	private String idiomaPreferido;
	private int dinero;
	private boolean cuentaExpirada;
	private boolean cuentaBloqueada;
	private boolean credencialExpirada;
	private boolean activado;

	public Usuario() {
		this.cuentaExpirada = false;
		this.cuentaBloqueada = false;
		this.activado = false;
		this.activado = true;
		this.rol = Role.USUARIO;
		this.dinero = 3000;
	}

	public Usuario(String nombre, String contrasenia, String correo, Role rol, String idiomaPreferido, int dinero) {
		super();
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.correo = correo;
		this.rol = rol;
		this.idiomaPreferido = idiomaPreferido;
		this.dinero = dinero;
	}

	public Usuario(String nombre, String contrasenia, String correo, String idiomaPreferido) {
		super();
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.correo = correo;
		this.idiomaPreferido = idiomaPreferido;
		this.dinero = 3000;
	}

	public Usuario(String nombre, String contrasenia, String correo, Role rol) {
		super();
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.correo = correo;
		this.rol = rol;
		this.dinero = 3000;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
	}

	public enum Role {
		ADMINISTRADOR, USUARIO
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

	public Role getRol() {
		return rol;
	}

	public void setRol(Role rol) {
		this.rol = rol;
	}

	public boolean isCuentaExpirada() {
		return cuentaExpirada;
	}

	public void setCuentaExpirada(boolean cuentaExpirada) {
		this.cuentaExpirada = cuentaExpirada;
	}

	public boolean isCuentaBloqueada() {
		return cuentaBloqueada;
	}

	public void setCuentaBloqueada(boolean cuentaBloqueada) {
		this.cuentaBloqueada = cuentaBloqueada;
	}

	public boolean isCredencialExpirada() {
		return credencialExpirada;
	}

	public void setCredencialExpirada(boolean credencialExpirada) {
		this.credencialExpirada = credencialExpirada;
	}

	public boolean isActivado() {
		return activado;
	}

	public void setActivado(boolean activado) {
		this.activado = activado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		return Objects.hash(activado, contrasenia, correo, credencialExpirada, cuentaBloqueada, cuentaExpirada, dinero,
				id, idiomaPreferido, nombre, rol);
	}

	@Override
	public String getPassword() {
		return contrasenia;
	}

	@Override
	public String getUsername() {
		return correo;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !cuentaExpirada;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !cuentaBloqueada;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !credencialExpirada;
	}

	@Override
	public boolean isEnabled() {
		return activado;
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
		return activado == other.activado && Objects.equals(contrasenia, other.contrasenia)
				&& Objects.equals(correo, other.correo) && credencialExpirada == other.credencialExpirada
				&& cuentaBloqueada == other.cuentaBloqueada && cuentaExpirada == other.cuentaExpirada
				&& dinero == other.dinero && id == other.id && Objects.equals(idiomaPreferido, other.idiomaPreferido)
				&& Objects.equals(nombre, other.nombre) && rol == other.rol;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", contrasenia=" + contrasenia + ", correo=" + correo
				+ ", rol=" + rol + ", idiomaPreferido=" + idiomaPreferido + ", dinero=" + dinero + ", cuentaExpirada="
				+ cuentaExpirada + ", cuentaBloqueada=" + cuentaBloqueada + ", credencialExpirada=" + credencialExpirada
				+ ", activado=" + activado + "]";
	}

}