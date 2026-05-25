package co.edu.unbosque.pokemon.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad que representa una compra realizada en la tienda del sistema.
 * 
 * Almacena información relacionada con el usuario comprador,
 * el ítem adquirido y la fecha en la que se realizó la compra.
 * 
 * Esta entidad es gestionada mediante JPA y persistida
 * en la base de datos.
 * 
 * @version 1.0
 */
@Entity
public class Tienda {

	/**
	 * Identificador único de la compra.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;

	/**
	 * Identificador del usuario que realizó la compra.
	 */
	private long idUsuario;

	/**
	 * Identificador del ítem comprado.
	 */
	private long idItem;

	/**
	 * Fecha y hora en la que se realizó la compra.
	 */
	private LocalDateTime fechaCompra;

	/**
	 * Constructor vacío requerido por JPA.
	 */
	public Tienda() {

	}

	/**
	 * Constructor con parámetros para inicializar una compra.
	 * 
	 * @param idUsuario identificador del usuario comprador.
	 * @param idItem identificador del ítem adquirido.
	 * @param fechaCompra fecha y hora de la compra.
	 */
	public Tienda(long idUsuario, long idItem, LocalDateTime fechaCompra) {
		super();
		this.idUsuario = idUsuario;
		this.idItem = idItem;
		this.fechaCompra = fechaCompra;
	}

	/**
	 * Obtiene el identificador único de la compra.
	 * 
	 * @return identificador de la compra.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador único de la compra.
	 * 
	 * @param id nuevo identificador.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del usuario comprador.
	 * 
	 * @return identificador del usuario.
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el identificador del usuario comprador.
	 * 
	 * @param idUsuario nuevo identificador del usuario.
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el identificador del ítem comprado.
	 * 
	 * @return identificador del ítem.
	 */
	public long getIdItem() {
		return idItem;
	}

	/**
	 * Establece el identificador del ítem comprado.
	 * 
	 * @param idItem nuevo identificador del ítem.
	 */
	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}

	/**
	 * Obtiene la fecha y hora de la compra.
	 * 
	 * @return fecha y hora de compra.
	 */
	public LocalDateTime getFechaCompra() {
		return fechaCompra;
	}

	/**
	 * Establece la fecha y hora de la compra.
	 * 
	 * @param fechaCompra nueva fecha y hora de compra.
	 */
	public void setFechaCompra(LocalDateTime fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	/**
	 * Genera el código hash del objeto Tienda.
	 * 
	 * @return valor hash generado.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(fechaCompra, id, idItem, idUsuario);
	}

	/**
	 * Compara este objeto con otro objeto Tienda.
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
		Tienda other = (Tienda) obj;
		return Objects.equals(fechaCompra, other.fechaCompra) && id == other.id && idItem == other.idItem
				&& idUsuario == other.idUsuario;
	}

	/**
	 * Retorna una representación en texto del objeto Tienda.
	 * 
	 * @return información de la compra en formato String.
	 */
	@Override
	public String toString() {
		return "Tienda [id=" + id + ", idUsuario=" + idUsuario + ", idItem=" + idItem + ", fechaCompra=" + fechaCompra
				+ "]";
	}

}