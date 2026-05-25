package co.edu.unbosque.pokemon.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad que representa un ítem dentro del sistema Pokémon.
 * <p>
 * Un ítem puede tener un nombre, descripción,
 * costo y un efecto curativo asociado.
 * </p>
 *
 * @version 1.0
 */
@Entity
public class Item {

	/**
	 * Identificador único del ítem.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

	/**
	 * Nombre del ítem.
	 */
	private String nombre;

	/**
	 * Descripción del ítem.
	 */
	private String descripcion;

	/**
	 * Costo del ítem dentro del juego.
	 */
	private int costo;

	/**
	 * Cantidad de salud que recupera el ítem.
	 */
	private int efectoCurativo;

	/**
	 * Constructor vacío requerido por JPA.
	 */
	public Item() {

	}

	/**
	 * Constructor con parámetros.
	 *
	 * @param nombre nombre del ítem.
	 * @param descripcion descripción del ítem.
	 * @param costo costo del ítem.
	 * @param efectoCurativo efecto curativo del ítem.
	 */
	public Item(String nombre, String descripcion,
			int costo, int efectoCurativo) {

		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.costo = costo;
		this.efectoCurativo = efectoCurativo;
	}

	/**
	 * Obtiene el identificador del ítem.
	 *
	 * @return identificador del ítem.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador del ítem.
	 *
	 * @param id nuevo identificador.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del ítem.
	 *
	 * @return nombre del ítem.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del ítem.
	 *
	 * @param nombre nuevo nombre del ítem.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la descripción del ítem.
	 *
	 * @return descripción del ítem.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción del ítem.
	 *
	 * @param descripcion nueva descripción del ítem.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el costo del ítem.
	 *
	 * @return costo del ítem.
	 */
	public int getCosto() {
		return costo;
	}

	/**
	 * Establece el costo del ítem.
	 *
	 * @param costo nuevo costo del ítem.
	 */
	public void setCosto(int costo) {
		this.costo = costo;
	}

	/**
	 * Obtiene el efecto curativo del ítem.
	 *
	 * @return cantidad de curación.
	 */
	public int getEfectoCurativo() {
		return efectoCurativo;
	}

	/**
	 * Establece el efecto curativo del ítem.
	 *
	 * @param efectoCurativo nuevo efecto curativo.
	 */
	public void setEfectoCurativo(int efectoCurativo) {
		this.efectoCurativo = efectoCurativo;
	}

	/**
	 * Genera el código hash del objeto.
	 *
	 * @return valor hash del ítem.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(costo, descripcion, efectoCurativo, id, nombre);
	}

	/**
	 * Compara dos objetos Item.
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

		Item other = (Item) obj;

		return costo == other.costo
				&& Objects.equals(descripcion, other.descripcion)
				&& efectoCurativo == other.efectoCurativo
				&& id == other.id
				&& Objects.equals(nombre, other.nombre);
	}

	/**
	 * Retorna una representación en texto del ítem.
	 *
	 * @return cadena con la información del ítem.
	 */
	@Override
	public String toString() {
		return "Item [id=" + id
				+ ", nombre=" + nombre
				+ ", descripcion=" + descripcion
				+ ", costo=" + costo
				+ ", efectoCurativo=" + efectoCurativo + "]";
	}

}