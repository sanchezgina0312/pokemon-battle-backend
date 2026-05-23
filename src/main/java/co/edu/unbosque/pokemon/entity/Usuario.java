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

/**
 * Entidad que representa un usuario dentro del sistema.
 * 
 * Contiene información relacionada con autenticación,
 * autorización, preferencias y estado de la cuenta.
 * 
 * Esta entidad implementa la interfaz UserDetails
 * de Spring Security para el manejo de seguridad
 * y autenticación de usuarios.
 * 
 * @version 1.0
 */
@Entity
public class Usuario implements UserDetails {

	/**
	 * Identificador de serialización de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador único del usuario.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

	/**
	 * Nombre del usuario.
	 */
	private String nombre;

	/**
	 * Contraseña del usuario.
	 */
	private String contrasenia;

	/**
	 * Correo electrónico único del usuario.
	 */
	@Column(unique = true)
	private String correo;

	/**
	 * Rol asignado al usuario.
	 */
	@Enumerated(EnumType.STRING)
	private Role rol;

	/**
	 * Idioma preferido del usuario.
	 */
	private String idiomaPreferido;

	/**
	 * Cantidad de dinero disponible del usuario.
	 */
	private int dinero;

	/**
	 * Indica si la cuenta está expirada.
	 */
	private boolean cuentaExpirada;

	/**
	 * Indica si la cuenta está bloqueada.
	 */
	private boolean cuentaBloqueada;

	/**
	 * Indica si las credenciales están expiradas.
	 */
	private boolean credencialExpirada;

	/**
	 * Indica si la cuenta está activada.
	 */
	private boolean activado;

	/**
	 * Género del usuario.
	 */
	private String genero;

	/**
	 * Constructor vacío requerido por JPA.
	 * 
	 * Inicializa valores predeterminados para la cuenta.
	 */
	public Usuario() {
		this.cuentaExpirada = false;
		this.cuentaBloqueada = false;
		this.activado = false;
		this.activado = true;
		this.rol = Role.USUARIO;
		this.dinero = 3000;
	}

	/**
	 * Constructor con todos los atributos principales.
	 * 
	 * @param nombre nombre del usuario.
	 * @param contrasenia contraseña del usuario.
	 * @param correo correo electrónico.
	 * @param rol rol asignado.
	 * @param idiomaPreferido idioma preferido.
	 * @param dinero dinero inicial del usuario.
	 */
	public Usuario(String nombre, String contrasenia, String correo, Role rol, String idiomaPreferido, int dinero) {
		super();
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.correo = correo;
		this.rol = rol;
		this.idiomaPreferido = idiomaPreferido;
		this.dinero = dinero;
	}

	/**
	 * Constructor para crear un usuario estándar.
	 * 
	 * @param nombre nombre del usuario.
	 * @param contrasenia contraseña del usuario.
	 * @param correo correo electrónico.
	 * @param idiomaPreferido idioma preferido.
	 */
	public Usuario(String nombre, String contrasenia, String correo, String idiomaPreferido) {
		super();
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.correo = correo;
		this.idiomaPreferido = idiomaPreferido;
		this.dinero = 3000;
	}

	/**
	 * Constructor para crear un usuario con rol específico.
	 * 
	 * @param nombre nombre del usuario.
	 * @param contrasenia contraseña del usuario.
	 * @param correo correo electrónico.
	 * @param rol rol asignado.
	 */
	public Usuario(String nombre, String contrasenia, String correo, Role rol) {
		super();
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.correo = correo;
		this.rol = rol;
		this.dinero = 3000;
	}

	/**
	 * Obtiene los permisos asociados al rol del usuario.
	 * 
	 * @return colección de autoridades del usuario.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
	}

	/**
	 * Enumeración que representa los roles disponibles
	 * dentro del sistema.
	 */
	public enum Role {

		/**
		 * Rol con permisos administrativos.
		 */
		ADMINISTRADOR,

		/**
		 * Rol estándar de usuario.
		 */
		USUARIO
	}

	/**
	 * Obtiene el identificador del usuario.
	 * 
	 * @return identificador del usuario.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador del usuario.
	 * 
	 * @param id nuevo identificador.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del usuario.
	 * 
	 * @return nombre del usuario.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del usuario.
	 * 
	 * @param nombre nuevo nombre.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la contraseña del usuario.
	 * 
	 * @return contraseña del usuario.
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * Establece la contraseña del usuario.
	 * 
	 * @param contrasenia nueva contraseña.
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * Obtiene el correo electrónico del usuario.
	 * 
	 * @return correo electrónico.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo electrónico del usuario.
	 * 
	 * @param correo nuevo correo electrónico.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene el rol del usuario.
	 * 
	 * @return rol asignado.
	 */
	public Role getRol() {
		return rol;
	}

	/**
	 * Establece el rol del usuario.
	 * 
	 * @param rol nuevo rol.
	 */
	public void setRol(Role rol) {
		this.rol = rol;
	}

	/**
	 * Verifica si la cuenta está expirada.
	 * 
	 * @return true si está expirada, false en caso contrario.
	 */
	public boolean isCuentaExpirada() {
		return cuentaExpirada;
	}

	/**
	 * Establece el estado de expiración de la cuenta.
	 * 
	 * @param cuentaExpirada nuevo estado.
	 */
	public void setCuentaExpirada(boolean cuentaExpirada) {
		this.cuentaExpirada = cuentaExpirada;
	}

	/**
	 * Verifica si la cuenta está bloqueada.
	 * 
	 * @return true si está bloqueada, false en caso contrario.
	 */
	public boolean isCuentaBloqueada() {
		return cuentaBloqueada;
	}

	/**
	 * Establece el estado de bloqueo de la cuenta.
	 * 
	 * @param cuentaBloqueada nuevo estado.
	 */
	public void setCuentaBloqueada(boolean cuentaBloqueada) {
		this.cuentaBloqueada = cuentaBloqueada;
	}

	/**
	 * Verifica si las credenciales están expiradas.
	 * 
	 * @return true si están expiradas, false en caso contrario.
	 */
	public boolean isCredencialExpirada() {
		return credencialExpirada;
	}

	/**
	 * Establece el estado de expiración de las credenciales.
	 * 
	 * @param credencialExpirada nuevo estado.
	 */
	public void setCredencialExpirada(boolean credencialExpirada) {
		this.credencialExpirada = credencialExpirada;
	}

	/**
	 * Verifica si la cuenta está activada.
	 * 
	 * @return true si está activada, false en caso contrario.
	 */
	public boolean isActivado() {
		return activado;
	}

	/**
	 * Establece el estado de activación de la cuenta.
	 * 
	 * @param activado nuevo estado.
	 */
	public void setActivado(boolean activado) {
		this.activado = activado;
	}

	/**
	 * Obtiene el identificador de serialización.
	 * 
	 * @return identificador de serialización.
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Obtiene el idioma preferido del usuario.
	 * 
	 * @return idioma preferido.
	 */
	public String getIdiomaPreferido() {
		return idiomaPreferido;
	}

	/**
	 * Establece el idioma preferido del usuario.
	 * 
	 * @param idiomaPreferido nuevo idioma preferido.
	 */
	public void setIdiomaPreferido(String idiomaPreferido) {
		this.idiomaPreferido = idiomaPreferido;
	}

	/**
	 * Obtiene la cantidad de dinero del usuario.
	 * 
	 * @return dinero disponible.
	 */
	public int getDinero() {
		return dinero;
	}

	/**
	 * Establece la cantidad de dinero del usuario.
	 * 
	 * @param dinero nueva cantidad de dinero.
	 */
	public void setDinero(int dinero) {
		this.dinero = dinero;
	}

	/**
	 * Genera el código hash del objeto Usuario.
	 * 
	 * @return valor hash generado.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(activado, contrasenia, correo, credencialExpirada, cuentaBloqueada, cuentaExpirada, dinero,
				id, idiomaPreferido, nombre, rol);
	}

	/**
	 * Obtiene la contraseña utilizada para autenticación.
	 * 
	 * @return contraseña del usuario.
	 */
	@Override
	public String getPassword() {
		return contrasenia;
	}

	/**
	 * Obtiene el nombre de usuario utilizado para autenticación.
	 * 
	 * En este caso corresponde al correo electrónico.
	 * 
	 * @return correo electrónico del usuario.
	 */
	@Override
	public String getUsername() {
		return correo;
	}

	/**
	 * Indica si la cuenta no está expirada.
	 * 
	 * @return true si la cuenta es válida.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return !cuentaExpirada;
	}

	/**
	 * Indica si la cuenta no está bloqueada.
	 * 
	 * @return true si la cuenta no está bloqueada.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return !cuentaBloqueada;
	}

	/**
	 * Indica si las credenciales no están expiradas.
	 * 
	 * @return true si las credenciales son válidas.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return !credencialExpirada;
	}

	/**
	 * Indica si la cuenta está habilitada.
	 * 
	 * @return true si la cuenta está activada.
	 */
	@Override
	public boolean isEnabled() {
		return activado;
	}

	/**
	 * Obtiene el género del usuario.
	 * 
	 * @return género del usuario.
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * Establece el género del usuario.
	 * 
	 * @param genero nuevo género.
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * Compara este usuario con otro objeto.
	 * 
	 * @param obj objeto a comparar.
	 * @return true si ambos objetos son iguales,
	 *         false en caso contrario.
	 */
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

	/**
	 * Retorna una representación en texto del objeto Usuario.
	 * 
	 * @return información del usuario en formato String.
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", contrasenia=" + contrasenia + ", correo=" + correo
				+ ", rol=" + rol + ", idiomaPreferido=" + idiomaPreferido + ", dinero=" + dinero
				+ ", cuentaExpirada=" + cuentaExpirada + ", cuentaBloqueada=" + cuentaBloqueada
				+ ", credencialExpirada=" + credencialExpirada + ", activado=" + activado + "]";
	}

}