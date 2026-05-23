package co.edu.unbosque.pokemon.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad que representa el inventario de un usuario.
 * <p>
 * Almacena la relación entre un usuario y los ítems
 * que posee, incluyendo la cantidad disponible
 * de cada uno.
 * </p>
 *
 * @version 1.0
 */
@Entity
public class Inventario {

	/**
	 * Identificador único del registro de inventario.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

	/**
	 * Identificador del usuario propietario del inventario.
	 */
	private long idUsuario;

	/**
	 * Identificador del ítem almacenado.
	 */
	private long idItem;

	/**
	 * Cantidad disponible del ítem.
	 */
	private int cantidad;

	/**
	 * Constructor vacío requerido por JPA.
	 */
	public Inventario() {

	}

	/**
	 * Constructor con parámetros.
	 *
	 * @param idUsuario identificador del usuario.
	 * @param idItem identificador del ítem.
	 * @param cantidad cantidad disponible del ítem.
	 */
	public Inventario(long idUsuario, long idItem, int cantidad) {
		super();
		this.idUsuario = idUsuario;
		this.idItem = idItem;
		this.cantidad = cantidad;
	}

	/**
	 * Obtiene el identificador del inventario.
	 *
	 * @return identificador del registro.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador del inventario.
	 *
	 * @param id nuevo identificador.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del usuario.
	 *
	 * @return identificador del usuario.
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el identificador del usuario.
	 *
	 * @param idUsuario nuevo identificador del usuario.
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el identificador del ítem.
	 *
	 * @return identificador del ítem.
	 */
	public long getIdItem() {
		return idItem;
	}

	/**
	 * Establece el identificador del ítem.
	 *
	 * @param idItem nuevo identificador del ítem.
	 */
	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}

	/**
	 * Obtiene la cantidad disponible del ítem.
	 *
	 * @return cantidad del ítem.
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Establece la cantidad disponible del ítem.
	 *
	 * @param cantidad nueva cantidad del ítem.
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Genera el código hash del objeto.
	 *
	 * @return valor hash del inventario.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(cantidad, id, idItem, idUsuario);
	}

	/**
	 * Compara dos objetos Inventario.
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

		Inventario other = (Inventario) obj;

		return cantidad == other.cantidad
				&& id == other.id
				&& idItem == other.idItem
				&& idUsuario == other.idUsuario;
	}

	/**
	 * Retorna una representación en texto del inventario.
	 *
	 * @return cadena con la información del inventario.
	 */
	@Override
	public String toString() {
		return "Inventario [id=" + id
				+ ", idUsuario=" + idUsuario
				+ ", idItem=" + idItem
				+ ", cantidad=" + cantidad + "]";
	}

}