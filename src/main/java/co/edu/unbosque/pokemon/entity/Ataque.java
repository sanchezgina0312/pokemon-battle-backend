package co.edu.unbosque.pokemon.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Entidad que representa un ataque dentro del sistema Pokémon.
 * <p>
 * Cada ataque posee un nombre único, un estado de baneo
 * y un posible poder modificado.
 * </p>
 *
 * @author Angie
 * @version 1.0
 */
@Entity
public class Ataque {

	/**
	 * Nombre único del ataque.
	 */
	private @Id String nombre;

	/**
	 * Indica si el ataque se encuentra baneado.
	 */
	private boolean estaBaneado;

	/**
	 * Poder modificado del ataque.
	 */
	private Integer poderModificado;

	/**
	 * Constructor vacío requerido por JPA.
	 */
	private Long id; 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ataque() {

	}

	/**
	 * Constructor con parámetros.
	 *
	 * @param nombre nombre del ataque.
	 * @param estaBaneado indica si el ataque está baneado.
	 * @param poderModificado poder modificado del ataque.
	 */
	public Ataque(String nombre, boolean estaBaneado, Integer poderModificado) {
		super();
		this.nombre = nombre;
		this.estaBaneado = estaBaneado;
		this.poderModificado = poderModificado;
	}

	/**
	 * Obtiene el nombre del ataque.
	 *
	 * @return nombre del ataque.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del ataque.
	 *
	 * @param nombre nuevo nombre del ataque.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Indica si el ataque está baneado.
	 *
	 * @return {@code true} si está baneado, {@code false} en caso contrario.
	 */
	public boolean isEstaBaneado() {
		return estaBaneado;
	}

	/**
	 * Define el estado de baneo del ataque.
	 *
	 * @param estaBaneado nuevo estado de baneo.
	 */
	public void setEstaBaneado(boolean estaBaneado) {
		this.estaBaneado = estaBaneado;
	}

	/**
	 * Obtiene el poder modificado del ataque.
	 *
	 * @return poder modificado.
	 */
	public Integer getPoderModificado() {
		return poderModificado;
	}

	/**
	 * Establece el poder modificado del ataque.
	 *
	 * @param poderModificado nuevo poder modificado.
	 */
	public void setPoderModificado(Integer poderModificado) {
		this.poderModificado = poderModificado;
	}

	/**
	 * Genera el código hash del objeto.
	 *
	 * @return valor hash del ataque.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(estaBaneado, nombre, poderModificado);
	}

	/**
	 * Compara dos objetos Ataque.
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

		Ataque other = (Ataque) obj;

		return estaBaneado == other.estaBaneado
				&& Objects.equals(nombre, other.nombre)
				&& Objects.equals(poderModificado, other.poderModificado);
	}

	/**
	 * Retorna una representación en texto del ataque.
	 *
	 * @return cadena con la información del ataque.
	 */
	@Override
	public String toString() {
		return "Ataque [nombre=" + nombre
				+ ", estaBaneado=" + estaBaneado
				+ ", poderModificado=" + poderModificado
				+ "]";
	}

}