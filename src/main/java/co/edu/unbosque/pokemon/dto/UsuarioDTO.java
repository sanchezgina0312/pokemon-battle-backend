package co.edu.unbosque.pokemon.dto;

import java.util.Objects;

/**
 * Objeto de Transferencia de Datos (DTO) que representa a un usuario dentro del sistema.
 * <p>
 * Esta clase condensa las credenciales de acceso, la información de perfil, las preferencias de 
 * localización (idioma), el rol asignado (ej. "ENTRENADOR", "ADMINISTRADOR") y los fondos económicos 
 * (dinero) disponibles del usuario para interactuar con los módulos de combate y tienda.
 * </p>
 * 
 * @version 1.0
 */
public class UsuarioDTO {

	/**
	 * Identificador único y auto-incremental del usuario en la base de datos local.
	 */
	private long id;
	
	/**
	 * Nombre de usuario o alias de perfil elegido por el jugador para identificarse en el sistema.
	 */
	private String nombre;
	
	/**
	 * Contraseña encriptada o credencial secreta de autenticación asociada a la cuenta del usuario.
	 */
	private String contrasenia;
	
	/**
	 * Dirección de correo electrónico registrada por el usuario para notificaciones o recuperación de cuenta.
	 */
	private String correo;
	
	/**
	 * Rol o nivel de acceso asignado al usuario dentro del ecosistema (ej. "ROLE_USER", "ROLE_ADMIN").
	 */
	private String rol;
	
	/**
	 * Código o identificador del idioma de preferencia del usuario para la traducción de los textos (ej. "es", "en").
	 */
	private String idiomaPreferido;
	
	/**
	 * Cantidad de saldo o fondos económicos digitales que posee el usuario para realizar compras en la tienda.
	 */
	private int dinero;
	
	/**
	 * Identificador de personaje para el usuario
	 */
	private String genero;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code UsuarioDTO}.
	 */

	public UsuarioDTO() {

	}

	/**
	 * Constructor parametrizado de la clase (excluyendo el ID local autogenerado).
	 * Permite instanciar un nuevo {@code UsuarioDTO} plenamente inicializado para flujos de registro o actualización.
	 *
	 * @param nombre          El nombre único de usuario.
	 * @param contrasenia     La clave o credencial de seguridad.
	 * @param correo          La dirección de correo electrónico vinculada.
	 * @param rol             El perfil de permisos asignado.
	 * @param idiomaPreferido El idioma seleccionado para la interfaz y descripciones.
	 * @param dinero          Los fondos o saldo económico inicial.
	 */
	public UsuarioDTO(String nombre, String contrasenia, String correo, String rol, String idiomaPreferido,
			int dinero) {
		super();
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.correo = correo;
		this.rol = rol;
		this.idiomaPreferido = idiomaPreferido;
		this.dinero = dinero;
	}

	/**
	 * Obtiene el identificador único del usuario en el sistema local.
	 *
	 * @return El ID numérico del registro.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del usuario en el sistema local.
	 *
	 * @param id El nuevo ID para asignar al registro.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre identificador del usuario.
	 *
	 * @return Una cadena de caracteres con el nombre de usuario.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre identificador del usuario.
	 *
	 * @param nombre El nuevo nombre de usuario a asignar.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la contraseña o clave secreta de autenticación.
	 *
	 * @return Una cadena de caracteres con la contraseña.
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * Establece la contraseña o clave secreta de autenticación.
	 *
	 * @param contrasenia La nueva contraseña a asignar.
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * Obtiene la dirección de correo electrónico del usuario.
	 *
	 * @return Una cadena de caracteres con el correo electrónico.
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece la dirección de correo electrónico del usuario.
	 *
	 * @param correo El nuevo correo electrónico a asignar.
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene el rol o nivel de seguridad asignado al usuario.
	 *
	 * @return Una cadena de caracteres con el nombre del rol.
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Establece el rol o nivel de seguridad asignado al usuario.
	 *
	 * @param rol El nuevo rol de permisos a asignar.
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Obtiene el código identificador del idioma preferido del usuario.
	 *
	 * @return Una cadena de caracteres correspondiente a las siglas del idioma.
	 */
	public String getIdiomaPreferido() {
		return idiomaPreferido;
	}

	/**
	 * Establece el código identificador del idioma preferido del usuario.
	 *
	 * @param idiomaPreferido El nuevo código de idioma a asignar.
	 */
	public void setIdiomaPreferido(String idiomaPreferido) {
		this.idiomaPreferido = idiomaPreferido;
	}

	/**
	 * Obtiene el saldo o dinero total acumulado por el usuario.
	 *
	 * @return Un entero con el dinero disponible.
	 */
	public int getDinero() {
		return dinero;
	}

	/**
	 * Establece el saldo o dinero total acumulado por el usuario.
	 *
	 * @param dinero El nuevo monto de dinero a asignar.
	 */
	public void setDinero(int dinero) {
		this.dinero = dinero;
	}
	
	

	/**
	 * Obtiene el personaje elegido por el usuario.
	 *
	 * @return Un string con el genero seleccionado.
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * Obtiene el personaje elegido por el usuario.
	 *
	 * @param genero El genero a asignar.
	 */

	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * Genera un código hash único para la instancia actual basado en sus campos.
	 *
	 * @return El código hash calculado para este objeto.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(contrasenia, correo, dinero, id, idiomaPreferido, nombre, rol);
	}

	/**
	 * Compara de forma estructural la igualdad de este objeto frente a otro.
	 * <p>
	 * Dos instancias se evalúan como idénticas si y solo si coinciden en su totalidad 
	 * todos los valores lógicos, textuales, financieros y de identidad del usuario.
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
		UsuarioDTO other = (UsuarioDTO) obj;
		return Objects.equals(contrasenia, other.contrasenia) && Objects.equals(correo, other.correo)
				&& dinero == other.dinero && id == other.id && Objects.equals(idiomaPreferido, other.idiomaPreferido)
				&& Objects.equals(nombre, other.nombre) && Objects.equals(rol, other.rol);
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva de los valores de la instancia.
	 */
	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", nombre=" + nombre + ", contrasenia=" + contrasenia + ", correo=" + correo
				+ ", rol=" + rol + ", idiomaPreferido=" + idiomaPreferido + ", dinero=" + dinero + "]";
	}

}