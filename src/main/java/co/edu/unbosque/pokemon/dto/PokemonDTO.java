package co.edu.unbosque.pokemon.dto;

import java.util.List;
import java.util.Objects;

/**
 * Objeto de Transferencia de Datos (DTO) que representa la instancia de un Pokémon dentro del juego.
 * <p>
 * A diferencia de los DTOs de consulta externa, esta clase transporta el estado dinámico, 
 * personalizado y persistente de una criatura perteneciente a un entrenador en el sistema local, 
 * incluyendo sus estadísticas de combate activas, su nivel, experiencia, los movimientos asignados 
 * y su condición actual.
 * </p>
 * * @author Integrantes del Proyecto
 * @version 1.0
 */
public class PokemonDTO {

	/**
	 * Identificador único y auto-incremental de la instancia del Pokémon en la base de datos local.
	 */
	private long id;

	/**
	 * Identificador de la especie del Pokémon en la API externa (PokeAPI). 
	 * Vincula esta instancia con sus datos base globales (imágenes, tipos, etc.).
	 */
	private Integer pokeApiId;

	/**
	 * Nombre personalizado o alias asignado al Pokémon por su entrenador.
	 */
	private String apodo;

	/**
	 * Nivel actual de experiencia y poder en el que se encuentra el Pokémon.
	 */
	private int nivel;

	/**
	 * Cantidad de puntos de experiencia acumulados en el nivel actual o a lo largo de su progreso.
	 */
	private int experienciaAcumulada;

	/**
	 * Puntos de salud (HP) actuales que posee el Pokémon en tiempo real. 
	 * Varía durante los combates o al usar ítems curativos.
	 */
	private int saludActual;

	/**
	 * Cantidad máxima de puntos de salud (HP) que la criatura puede alcanzar en su nivel actual.
	 */
	private int saludMaxima;

	/**
	 * Nombre del primer ataque o movimiento equipado en el set activo de combate.
	 */
	private String nombreAtaque1;

	/**
	 * Nombre del segundo ataque o movimiento equipado en el set activo de combate.
	 */
	private String nombreAtaque2;

	/**
	 * Nombre del tercer ataque o movimiento equipado en el set activo de combate.
	 */
	private String nombreAtaque3;

	/**
	 * Nombre del cuarto ataque o movimiento equipado en el set activo de combate.
	 */
	private String nombreAtaque4;

	/**
	 * Identificador único del usuario (entrenador) que es propietario legítimo de este Pokémon.
	 */
	private Long idUsuarioPropietario;

	/**
	 * Estado vital o condición alterada actual del Pokémon (ej. "ACTIVO", "DEBILITADO", "EN_EQUIPO").
	 */
	private String estado;

	/**
	 * Lista con los nombres de los tipos elementales del Pokémon (ej. ["GRASS", "POISON"]).
	 * Se llena dinámicamente consultando la PokéAPI en la capa de servicio.
	 */
	private List<String> tipos;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code PokemonDTO}.
	 */
	public PokemonDTO() {

	}

	/**
	 * Constructor parametrizado de la clase (excluyendo el ID local autogenerado).
	 * Permite instanciar un nuevo {@code PokemonDTO} con los atributos iniciales o de carga del juego.
	 *
	 * @param pokeApiId            ID de referencia en la PokeAPI.
	 * @param apodo                Nombre personalizado o alias de la criatura.
	 * @param nivel                Nivel de combate inicial o actual.
	 * @param experienciaAcumulada Puntos de experiencia ganados.
	 * @param saludActual          Puntos de vida con los que cuenta en el momento.
	 * @param saludMaxima          Límite máximo de puntos de vida permitidos.
	 * @param nombreAtaque1        Primer movimiento del set.
	 * @param nombreAtaque2        Segundo movimiento del set.
	 * @param nombreAtaque3        Tercer movimiento del set.
	 * @param nombreAtaque4        Cuarto movimiento del set.
	 * @param idUsuarioPropietario ID del entrenador dueño.
	 * @param estado               Condición vital o de almacenamiento del Pokémon.
	 */
	public PokemonDTO(Integer pokeApiId, String apodo, int nivel, int experienciaAcumulada, int saludActual,
			int saludMaxima, String nombreAtaque1, String nombreAtaque2, String nombreAtaque3, String nombreAtaque4,
			Long idUsuarioPropietario, String estado) {
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
	}

	/**
	 * Obtiene el identificador único de la instancia del Pokémon en el sistema local.
	 *
	 * @return El ID numérico del registro.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador único de la instancia del Pokémon en el sistema local.
	 *
	 * @param id El nuevo ID para asignar al registro.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el ID correspondiente a la especie del Pokémon en la PokeAPI.
	 *
	 * @return Un entero con el ID externo de la especie.
	 */
	public Integer getPokeApiId() {
		return pokeApiId;
	}

	/**
	 * Establece el ID correspondiente a la especie del Pokémon en la PokeAPI.
	 *
	 * @param pokeApiId El nuevo ID externo a asignar.
	 */
	public void setPokeApiId(Integer pokeApiId) {
		this.pokeApiId = pokeApiId;
	}

	/**
	 * Obtiene el apodo o alias personalizado de la criatura.
	 *
	 * @return El apodo del Pokémon.
	 */
	public String getApodo() {
		return apodo;
	}

	/**
	 * Establece un apodo o alias personalizado para la criatura.
	 *
	 * @param apodo El nuevo alias a asignar.
	 */
	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	/**
	 * Obtiene el nivel actual del Pokémon.
	 *
	 * @return El nivel numérico de la criatura.
	 */
	public int getNivel() {
		return nivel;
	}

	/**
	 * Establece el nivel actual del Pokémon.
	 *
	 * @param nivel El nuevo nivel numérico a asignar.
	 */
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	/**
	 * Obtiene los puntos de experiencia acumulados.
	 *
	 * @return Un entero con la experiencia acumulada.
	 */
	public int getExperienciaAcumulada() {
		return experienciaAcumulada;
	}

	/**
	 * Establece los puntos de experiencia acumulados.
	 *
	 * @param experienciaAcumulada El nuevo valor de experiencia acumulada.
	 */
	public void setExperienciaAcumulada(int experienciaAcumulada) {
		this.experienciaAcumulada = experienciaAcumulada;
	}

	/**
	 * Obtiene los puntos de salud (HP) actuales de la instancia.
	 *
	 * @return La salud actual.
	 */
	public int getSaludActual() {
		return saludActual;
	}

	/**
	 * Establece los puntos de salud (HP) actuales de la instancia.
	 *
	 * @param saludActual La nueva salud actual a asignar.
	 */
	public void setSaludActual(int saludActual) {
		this.saludActual = saludActual;
	}

	/**
	 * Obtiene el valor máximo de salud (HP) que puede tener el Pokémon.
	 *
	 * @return La salud máxima permitida.
	 */
	public int getSaludMaxima() {
		return saludMaxima;
	}

	/**
	 * Establece el valor máximo de salud (HP) que puede tener el Pokémon.
	 *
	 * @param saludMaxima La nueva salud máxima a asignar.
	 */
	public void setSaludMaxima(int saludMaxima) {
		this.saludMaxima = saludMaxima;
	}

	/**
	 * Obtiene el nombre del primer ataque asignado.
	 *
	 * @return El nombre del ataque 1.
	 */
	public String getNombreAtaque1() {
		return nombreAtaque1;
	}

	/**
	 * Establece el nombre del primer ataque asignado.
	 *
	 * @param nombreAtaque1 El nuevo nombre para el ataque 1.
	 */
	public void setNombreAtaque1(String nombreAtaque1) {
		this.nombreAtaque1 = nombreAtaque1;
	}

	/**
	 * Obtiene el nombre del segundo ataque asignado.
	 *
	 * @return El nombre del ataque 2.
	 */
	public String getNombreAtaque2() {
		return nombreAtaque2;
	}

	/**
	 * Establece el nombre del segundo ataque asignado.
	 *
	 * @param nombreAtaque2 El nuevo nombre para el ataque 2.
	 */
	public void setNombreAtaque2(String nombreAtaque2) {
		this.nombreAtaque2 = nombreAtaque2;
	}

	/**
	 * Obtiene el nombre del tercer ataque asignado.
	 *
	 * @return El nombre del ataque 3.
	 */
	public String getNombreAtaque3() {
		return nombreAtaque3;
	}

	/**
	 * Establece el nombre del tercer ataque asignado.
	 *
	 * @param nombreAtaque3 El nuevo nombre para el ataque 3.
	 */
	public void setNombreAtaque3(String nombreAtaque3) {
		this.nombreAtaque3 = nombreAtaque3;
	}

	/**
	 * Obtiene el nombre del cuarto ataque asignado.
	 *
	 * @return El nombre del ataque 4.
	 */
	public String getNombreAtaque4() {
		return nombreAtaque4;
	}

	/**
	 * Establece el nombre del cuarto ataque asignado.
	 *
	 * @param nombreAtaque4 El nuevo nombre para el ataque 4.
	 */
	public void setNombreAtaque4(String nombreAtaque4) {
		this.nombreAtaque4 = nombreAtaque4;
	}

	/**
	 * Obtiene el identificador único del usuario propietario de este Pokémon.
	 *
	 * @return El ID del usuario propietario.
	 */
	public Long getIdUsuarioPropietario() {
		return idUsuarioPropietario;
	}

	/**
	 * Establece el identificador único del usuario propietario de este Pokémon.
	 *
	 * @param idUsuarioPropietario El nuevo ID del usuario propietario.
	 */
	public void setIdUsuarioPropietario(Long idUsuarioPropietario) {
		this.idUsuarioPropietario = idUsuarioPropietario;
	}

	/**
	 * Obtiene la condición o estado actual de la criatura.
	 *
	 * @return Una cadena de caracteres con el estado.
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * Establece el estado o condición actual de la criatura.
	 *
	 * @param estado El nuevo estado a asignar (ej. "ACTIVO").
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Obtiene la lista de tipos elementales asignados a esta criatura.
	 *
	 * @return Una lista de cadenas con los nombres de los tipos.
	 */
	public List<String> getTipos() {
		return tipos;
	}

	/**
	 * Establece la lista de tipos elementales obtenidos desde la API externa.
	 *
	 * @param tipos La nueva lista de nombres de tipos a asignar.
	 */
	public void setTipos(List<String> tipos) {
		this.tipos = tipos;
	}

	/**
	 * Genera un código hash único para la instancia actual basado en todos sus campos.
	 *
	 * @return El código hash calculado para este objeto.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(apodo, estado, experienciaAcumulada, id, idUsuarioPropietario, nivel, nombreAtaque1,
				nombreAtaque2, nombreAtaque3, nombreAtaque4, pokeApiId, saludActual, saludMaxima, tipos);
	}

	/**
	 * Compara de forma estructural la igualdad de este objeto frente a otro.
	 *
	 * @param obj El objeto con el cual realizar la comparación.
	 * @return {@code true} si los objetos son estructuralmente equivalentes; {@code false} en caso contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PokemonDTO other = (PokemonDTO) obj;
		return Objects.equals(apodo, other.apodo) && Objects.equals(estado, other.estado)
				&& experienciaAcumulada == other.experienciaAcumulada && id == other.id
				&& Objects.equals(idUsuarioPropietario, other.idUsuarioPropietario) && nivel == other.nivel
				&& Objects.equals(nombreAtaque1, other.nombreAtaque1)
				&& Objects.equals(nombreAtaque2, other.nombreAtaque2)
				&& Objects.equals(nombreAtaque3, other.nombreAtaque3)
				&& Objects.equals(nombreAtaque4, other.nombreAtaque4) && Objects.equals(pokeApiId, other.pokeApiId)
				&& saludActual == other.saludActual && saludMaxima == other.saludMaxima
				&& Objects.equals(tipos, other.tipos);
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva de los valores de la instancia.
	 */
	@Override
	public String toString() {
		return "PokemonDTO [id=" + id + ", pokeApiId=" + pokeApiId + ", apodo=" + apodo + ", nivel=" + nivel
				+ ", experienciaAcumulada=" + experienciaAcumulada + ", saludActual=" + saludActual + ", saludMaxima="
				+ saludMaxima + ", nombreAtaque1=" + nombreAtaque1 + ", nombreAtaque2=" + nombreAtaque2
				+ ", nombreAtaque3=" + nombreAtaque3 + ", nombreAtaque4=" + nombreAtaque4 + ", idUsuarioPropietario="
				+ idUsuarioPropietario + ", estado=" + estado + ", tipos=" + tipos + "]";
	}

}