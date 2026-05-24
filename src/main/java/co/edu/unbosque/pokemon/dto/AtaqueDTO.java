package co.edu.unbosque.pokemon.dto;

import java.util.Objects;

/**
 * Objeto de Transferencia de Datos (DTO) que representa un ataque de Pokémon en el sistema.
 * <p>
 * Se utiliza para transportar la información de los movimientos o ataques entre las capas 
 * de la aplicación, incluyendo detalles sobre su penalización (baneo) y modificaciones de poder.
 * </p>
 * 
 * @version 1.0
 */
public class AtaqueDTO {
	
	/**
	 * El nombre identificador único del ataque.
	 */
	private String nombre;
	private Long id; 

	
	/**
	 * Indicador de si el ataque se encuentra restringido o baneado para su uso en combates.
	 */
	private boolean estaBaneado;
	
	/**
	 * El valor numérico del poder del ataque tras sufrir modificaciones o alteraciones de balanceo.
	 * Puede ser {@code null} si el ataque no posee un poder base asignado o modificado.
	 */
	private Integer poderModificado;
	
	/**
	 * Constructor por defecto de la clase.
	 * Crea una instancia vacía de {@code AtaqueDTO}.
	 */
	public AtaqueDTO() {
		
	}

	/**
	 * Constructor con todos los campos de la clase.
	 * Instancia un nuevo {@code AtaqueDTO} con los valores parametrizados.
	 *
	 * @param nombre          El nombre identificador del ataque.
	 * @param estaBaneado     {@code true} si el ataque está deshabilitado; {@code false} en caso contrario.
	 * @param poderModificado El valor del poder modificado del ataque.
	 */
	public AtaqueDTO(String nombre, boolean estaBaneado, Integer poderModificado) {
		super();
		this.nombre = nombre;
		this.estaBaneado = estaBaneado;
		this.poderModificado = poderModificado;
	}

	/**
	 * Obtiene el nombre del ataque.
	 *
	 * @return Una cadena de caracteres con el nombre del ataque.
	 */
	public String getNombre() {
		return nombre;
	}
		public Long getId() {
		    return id;
		}

		public void setId(Long id) {
		    this.id = id;
		}
	/**
	 * Establece el nombre del ataque.
	 *
	 * @param nombre El nuevo nombre para asignar al ataque.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Indica si el ataque está baneado del sistema.
	 *
	 * @return {@code true} si el ataque está baneado; {@code false} si está disponible.
	 */
	public boolean isEstaBaneado() {
		return estaBaneado;
	}

	/**
	 * Establece el estado de baneo del ataque.
	 *
	 * @param estaBaneado El nuevo estado de baneo a asignar.
	 */
	public void setEstaBaneado(boolean estaBaneado) {
		this.estaBaneado = estaBaneado;
	}

	/**
	 * Obtiene el valor del poder modificado del ataque.
	 *
	 * @return Un entero con el poder modificado, o {@code null} si no aplica.
	 */
	public Integer getPoderModificado() {
		return poderModificado;
	}

	/**
	 * Establece el valor del poder modificado del ataque.
	 *
	 * @param poderModificado El nuevo valor de poder modificado a asignar.
	 */
	public void setPoderModificado(Integer poderModificado) {
		this.poderModificado = poderModificado;
	}

	/**
	 * Genera el código hash para la instancia actual basado en sus atributos.
	 *
	 * @return El código hash calculado para este objeto.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(estaBaneado, nombre, poderModificado);
	}

	/**
	 * Compara el objeto actual con otro para determinar su igualdad estructural.
	 * <p>
	 * Dos instancias de {@code AtaqueDTO} se consideran iguales si y solo si comparten
	 * los mismos valores en sus atributos {@code nombre}, {@code estaBaneado} y {@code poderModificado}.
	 * </p>
	 *
	 * @param obj El objeto con el que se desea comparar esta instancia.
	 * @return {@code true} si los objetos son idénticos o estructuralmente iguales; 
	 *          {@code false} en caso contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AtaqueDTO other = (AtaqueDTO) obj;
		return estaBaneado == other.estaBaneado && Objects.equals(nombre, other.nombre)
				&& Objects.equals(poderModificado, other.poderModificado);
	}

	/**
	 * Devuelve una representación textual legible de los datos de la instancia de este DTO.
	 *
	 * @return Una cadena de caracteres con el estado detallado de los atributos del objeto.
	 */
	@Override
	public String toString() {
		return "AtaqueDTO [nombre=" + nombre + ", estaBaneado=" + estaBaneado + ", poderModificado=" + poderModificado
				+ "]";
	}
	
}