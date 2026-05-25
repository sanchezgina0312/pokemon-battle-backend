package co.edu.unbosque.pokemon.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad que representa un Pokémon dentro del sistema.
 * 
 * Almacena información relacionada con las estadísticas,
 * ataques, experiencia, estado y propietario de un Pokémon.
 * 
 * Esta entidad es gestionada mediante JPA y se almacena
 * en la base de datos.
 * 
 * @version 1.0
 */
@Entity
public class Pokemon {

	/**
	 * Identificador único del Pokémon.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

	/**
	 * Identificador del Pokémon en la PokéAPI.
	 */
	private Integer pokeApiId;

	/**
	 * Apodo personalizado asignado al Pokémon.
	 */
	private String apodo;

	/**
	 * Nivel actual del Pokémon.
	 */
	private int nivel;

	/**
	 * Experiencia total acumulada por el Pokémon.
	 */
	private int experienciaAcumulada;

	/**
	 * Cantidad de salud actual del Pokémon.
	 */
	private int saludActual;

	/**
	 * Cantidad máxima de salud del Pokémon.
	 */
	private int saludMaxima;

	/**
	 * Nombre del primer ataque del Pokémon.
	 */
	private String nombreAtaque1;

	/**
	 * Nombre del segundo ataque del Pokémon.
	 */
	private String nombreAtaque2;

	/**
	 * Nombre del tercer ataque del Pokémon.
	 */
	private String nombreAtaque3;

	/**
	 * Nombre del cuarto ataque del Pokémon.
	 */
	private String nombreAtaque4;

	/**
	 * Identificador del usuario propietario del Pokémon.
	 */
	private Long idUsuarioPropietario;

	/**
	 * Estado general actual del Pokémon.
	 */
	private String estado;

	/**
	 * Estadística de ataque del Pokémon.
	 */
	private int ataque;

	/**
	 * Estadística de defensa del Pokémon.
	 */
	private int defensa;

	/**
	 * Estadística de velocidad del Pokémon.
	 */
	private int velocidad;

	/**
	 * Estado alterado actual del Pokémon.
	 */
	private String estadoAlterado;

	/**
	 * Constructor vacío requerido por JPA.
	 */
	public Pokemon() {

	}

	/**
	 * Constructor que inicializa todos los atributos principales
	 * del Pokémon.
	 * 
	 * @param pokeApiId identificador del Pokémon en la PokéAPI.
	 * @param apodo apodo personalizado del Pokémon.
	 * @param nivel nivel actual del Pokémon.
	 * @param experienciaAcumulada experiencia acumulada.
	 * @param saludActual salud actual del Pokémon.
	 * @param saludMaxima salud máxima del Pokémon.
	 * @param nombreAtaque1 nombre del primer ataque.
	 * @param nombreAtaque2 nombre del segundo ataque.
	 * @param nombreAtaque3 nombre del tercer ataque.
	 * @param nombreAtaque4 nombre del cuarto ataque.
	 * @param idUsuarioPropietario identificador del usuario propietario.
	 * @param estado estado general del Pokémon.
	 * @param ataque estadística de ataque.
	 * @param defensa estadística de defensa.
	 * @param velocidad estadística de velocidad.
	 * @param estadoAlterado estado alterado actual.
	 */
	public Pokemon(Integer pokeApiId, String apodo, int nivel, int experienciaAcumulada, int saludActual,
			int saludMaxima, String nombreAtaque1, String nombreAtaque2, String nombreAtaque3, String nombreAtaque4,
			Long idUsuarioPropietario, String estado, int ataque, int defensa, int velocidad, String estadoAlterado) {
		super();
		this.pokeApiId = pokeApiId;
		this.apodo = apodo;
		this.nivel = nivel;
		this.experienciaAcumulada = experienciaAcumulada;
		this.saludActual = saludActual;
		this.saludMaxima = saludMaxima;
		this.nombreAtaque1 = nombreAtaque1;
		this.nombreAtaque2 = nombreAtaque2;
		this.nombreAtaque3 = nombreAtaque3;
		this.nombreAtaque4 = nombreAtaque4;
		this.idUsuarioPropietario = idUsuarioPropietario;
		this.estado = estado;
		this.ataque = ataque;
		this.defensa = defensa;
		this.velocidad = velocidad;
		this.estadoAlterado = estadoAlterado;
	}

	/**
	 * Obtiene el valor de ataque del Pokémon.
	 * 
	 * @return valor de ataque.
	 */
	public int getAtaque() {
		return ataque;
	}

	/**
	 * Establece el valor de ataque del Pokémon.
	 * 
	 * @param ataque nuevo valor de ataque.
	 */
	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	/**
	 * Obtiene el valor de defensa del Pokémon.
	 * 
	 * @return valor de defensa.
	 */
	public int getDefensa() {
		return defensa;
	}

	/**
	 * Establece el valor de defensa del Pokémon.
	 * 
	 * @param defensa nuevo valor de defensa.
	 */
	public void setDefensa(int defensa) {
		this.defensa = defensa;
	}

	/**
	 * Obtiene el valor de velocidad del Pokémon.
	 * 
	 * @return valor de velocidad.
	 */
	public int getVelocidad() {
		return velocidad;
	}

	/**
	 * Establece el valor de velocidad del Pokémon.
	 * 
	 * @param velocidad nuevo valor de velocidad.
	 */
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}

	/**
	 * Obtiene el estado alterado actual del Pokémon.
	 * 
	 * @return estado alterado.
	 */
	public String getEstadoAlterado() {
		return estadoAlterado;
	}

	/**
	 * Establece el estado alterado del Pokémon.
	 * 
	 * @param estadoAlterado nuevo estado alterado.
	 */
	public void setEstadoAlterado(String estadoAlterado) {
		this.estadoAlterado = estadoAlterado;
	}

	/**
	 * Obtiene el identificador único del Pokémon.
	 * 
	 * @return identificador del Pokémon.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del Pokémon.
	 * 
	 * @param id nuevo identificador.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del Pokémon en la PokéAPI.
	 * 
	 * @return identificador de la PokéAPI.
	 */
	public Integer getPokeApiId() {
		return pokeApiId;
	}

	/**
	 * Establece el identificador del Pokémon en la PokéAPI.
	 * 
	 * @param pokeApiId nuevo identificador.
	 */
	public void setPokeApiId(Integer pokeApiId) {
		this.pokeApiId = pokeApiId;
	}

	/**
	 * Obtiene el apodo del Pokémon.
	 * 
	 * @return apodo del Pokémon.
	 */
	public String getApodo() {
		return apodo;
	}

	/**
	 * Establece el apodo del Pokémon.
	 * 
	 * @param apodo nuevo apodo.
	 */
	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	/**
	 * Obtiene el nivel actual del Pokémon.
	 * 
	 * @return nivel actual.
	 */
	public int getNivel() {
		return nivel;
	}

	/**
	 * Establece el nivel del Pokémon.
	 * 
	 * @param nivel nuevo nivel.
	 */
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	/**
	 * Obtiene la experiencia acumulada del Pokémon.
	 * 
	 * @return experiencia acumulada.
	 */
	public int getExperienciaAcumulada() {
		return experienciaAcumulada;
	}

	/**
	 * Establece la experiencia acumulada del Pokémon.
	 * 
	 * @param experienciaAcumulada nueva experiencia acumulada.
	 */
	public void setExperienciaAcumulada(int experienciaAcumulada) {
		this.experienciaAcumulada = experienciaAcumulada;
	}

	/**
	 * Obtiene la salud actual del Pokémon.
	 * 
	 * @return salud actual.
	 */
	public int getSaludActual() {
		return saludActual;
	}

	/**
	 * Establece la salud actual del Pokémon.
	 * 
	 * @param saludActual nueva salud actual.
	 */
	public void setSaludActual(int saludActual) {
		this.saludActual = saludActual;
	}

	/**
	 * Obtiene la salud máxima del Pokémon.
	 * 
	 * @return salud máxima.
	 */
	public int getSaludMaxima() {
		return saludMaxima;
	}

	/**
	 * Establece la salud máxima del Pokémon.
	 * 
	 * @param saludMaxima nueva salud máxima.
	 */
	public void setSaludMaxima(int saludMaxima) {
		this.saludMaxima = saludMaxima;
	}

	/**
	 * Obtiene el nombre del primer ataque.
	 * 
	 * @return nombre del ataque.
	 */
	public String getNombreAtaque1() {
		return nombreAtaque1;
	}

	/**
	 * Establece el nombre del primer ataque.
	 * 
	 * @param nombreAtaque1 nuevo nombre del ataque.
	 */
	public void setNombreAtaque1(String nombreAtaque1) {
		this.nombreAtaque1 = nombreAtaque1;
	}

	/**
	 * Obtiene el nombre del segundo ataque.
	 * 
	 * @return nombre del ataque.
	 */
	public String getNombreAtaque2() {
		return nombreAtaque2;
	}

	/**
	 * Establece el nombre del segundo ataque.
	 * 
	 * @param nombreAtaque2 nuevo nombre del ataque.
	 */
	public void setNombreAtaque2(String nombreAtaque2) {
		this.nombreAtaque2 = nombreAtaque2;
	}

	/**
	 * Obtiene el nombre del tercer ataque.
	 * 
	 * @return nombre del ataque.
	 */
	public String getNombreAtaque3() {
		return nombreAtaque3;
	}

	/**
	 * Establece el nombre del tercer ataque.
	 * 
	 * @param nombreAtaque3 nuevo nombre del ataque.
	 */
	public void setNombreAtaque3(String nombreAtaque3) {
		this.nombreAtaque3 = nombreAtaque3;
	}

	/**
	 * Obtiene el nombre del cuarto ataque.
	 * 
	 * @return nombre del ataque.
	 */
	public String getNombreAtaque4() {
		return nombreAtaque4;
	}

	/**
	 * Establece el nombre del cuarto ataque.
	 * 
	 * @param nombreAtaque4 nuevo nombre del ataque.
	 */
	public void setNombreAtaque4(String nombreAtaque4) {
		this.nombreAtaque4 = nombreAtaque4;
	}

	/**
	 * Obtiene el identificador del usuario propietario.
	 * 
	 * @return identificador del usuario propietario.
	 */
	public Long getIdUsuarioPropietario() {
		return idUsuarioPropietario;
	}

	/**
	 * Establece el identificador del usuario propietario.
	 * 
	 * @param idUsuarioPropietario nuevo identificador del usuario.
	 */
	public void setIdUsuarioPropietario(Long idUsuarioPropietario) {
		this.idUsuarioPropietario = idUsuarioPropietario;
	}

	/**
	 * Obtiene el estado actual del Pokémon.
	 * 
	 * @return estado actual.
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Establece el estado del Pokémon.
	 * 
	 * @param estado nuevo estado.
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Genera el código hash del objeto Pokémon.
	 * 
	 * @return valor hash generado.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(apodo, ataque, defensa, estado, estadoAlterado, experienciaAcumulada, id,
				idUsuarioPropietario, nivel, nombreAtaque1, nombreAtaque2, nombreAtaque3, nombreAtaque4, pokeApiId,
				saludActual, saludMaxima, velocidad);
	}

	/**
	 * Compara este Pokémon con otro objeto.
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
		Pokemon other = (Pokemon) obj;
		return Objects.equals(apodo, other.apodo) && ataque == other.ataque && defensa == other.defensa
				&& Objects.equals(estado, other.estado) && Objects.equals(estadoAlterado, other.estadoAlterado)
				&& experienciaAcumulada == other.experienciaAcumulada && id == other.id
				&& Objects.equals(idUsuarioPropietario, other.idUsuarioPropietario) && nivel == other.nivel
				&& Objects.equals(nombreAtaque1, other.nombreAtaque1)
				&& Objects.equals(nombreAtaque2, other.nombreAtaque2)
				&& Objects.equals(nombreAtaque3, other.nombreAtaque3)
				&& Objects.equals(nombreAtaque4, other.nombreAtaque4) && Objects.equals(pokeApiId, other.pokeApiId)
				&& saludActual == other.saludActual && saludMaxima == other.saludMaxima && velocidad == other.velocidad;
	}

	/**
	 * Retorna una representación en texto del objeto Pokémon.
	 * 
	 * @return información completa del Pokémon en formato String.
	 */
	@Override
	public String toString() {
		return "Pokemon [id=" + id + ", pokeApiId=" + pokeApiId + ", apodo=" + apodo + ", nivel=" + nivel
				+ ", experienciaAcumulada=" + experienciaAcumulada + ", saludActual=" + saludActual + ", saludMaxima="
				+ saludMaxima + ", nombreAtaque1=" + nombreAtaque1 + ", nombreAtaque2=" + nombreAtaque2
				+ ", nombreAtaque3=" + nombreAtaque3 + ", nombreAtaque4=" + nombreAtaque4
				+ ", idUsuarioPropietario=" + idUsuarioPropietario + ", estado=" + estado + ", ataque=" + ataque
				+ ", defensa=" + defensa + ", velocidad=" + velocidad + ", estadoAlterado=" + estadoAlterado + "]";
	}

}