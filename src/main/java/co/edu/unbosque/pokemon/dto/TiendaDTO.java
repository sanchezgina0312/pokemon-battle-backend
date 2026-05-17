package co.edu.unbosque.pokemon.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Objeto de Transferencia de Datos (DTO) que representa el registro de una transacción en la tienda.
 * <p>
 * Se utiliza para transportar la información correspondiente al historial de compras realizado por 
 * los usuarios dentro del sistema local, vinculando al comprador con el objeto adquirido y la marca 
 * de tiempo exacta en la que se efectuó la operación.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
public class TiendaDTO {

	/**
	 * Identificador único y auto-incremental del registro de la transacción en la base de datos local.
	 */
	private long id;
	
	/**
	 * Identificador único del usuario (entrenador) que realizó la compra en la tienda.
	 */
	private long idUsuario;
	
	/**
	 * Identificador único del ítem u objeto comercial que fue adquirido en la transacción.
	 */
	private long idItem;
	
	/**
	 * Fecha y hora exacta en la que se procesó y consolidó la compra del ítem.
	 */
	private LocalDateTime fechaCompra;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code TiendaDTO}.
	 */
	public TiendaDTO() {

	}

	/**
	 * Constructor parametrizado de la clase (excluyendo el ID local autogenerado).
	 * Permite instanciar un nuevo {@code TiendaDTO} con los datos de auditoría listos para su procesamiento.
	 *
	 * @param idUsuario   El identificador único del usuario comprador.
	 * @param idItem      El identificador único del ítem comprado.
	 * @param fechaCompra Objeto {@link LocalDateTime} con la marca de tiempo de la transacción.
	 */
	public TiendaDTO(long idUsuario, long idItem, LocalDateTime fechaCompra) {
		super();
		this.idUsuario = idUsuario;
		this.idItem = idItem;
		this.fechaCompra = fechaCompra;
	}

	/**
	 * Obtiene el identificador único del registro de la transacción.
	 *
	 * @return El ID del recibo o transacción en la base de datos.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del registro de la transacción.
	 *
	 * @param id El nuevo ID para asignar al registro.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del usuario que efectuó la compra.
	 *
	 * @return El ID del usuario comprador.
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el identificador del usuario que efectuó la compra.
	 *
	 * @param idUsuario El nuevo ID del usuario a asignar.
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el identificador del ítem adquirido.
	 *
	 * @return El ID del ítem comprado.
	 */
	public long getIdItem() {
		return idItem;
	}

	/**
	 * Establece el identificador del ítem adquirido.
	 *
	 * @param idItem El nuevo ID del ítem a asignar.
	 */
	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}

	/**
	 * Obtiene la fecha y hora en la que se realizó la compra.
	 *
	 * @return Un objeto {@link LocalDateTime} con el momento exacto de la transacción.
	 */
	public LocalDateTime getFechaCompra() {
		return fechaCompra;
	}

	/**
	 * Establece la fecha y hora en la que se realizó la compra.
	 *
	 * @param fechaCompra El nuevo objeto {@link LocalDateTime} con la marca de tiempo a asignar.
	 */
	public void setFechaCompra(LocalDateTime fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	/**
	 * Genera un código hash único para la instancia actual basado en sus campos.
	 *
	 * @return El código hash calculado para este objeto.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(fechaCompra, id, idItem, idUsuario);
	}

	/**
	 * Compara de forma estructural la igualdad de este objeto frente a otro.
	 * <p>
	 * Dos instancias se evalúan como iguales si y solo si coinciden plenamente en sus valores 
	 * de {@code id}, {@code idUsuario}, {@code idItem} y en la marca de tiempo {@code fechaCompra}.
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
		TiendaDTO other = (TiendaDTO) obj;
		return Objects.equals(fechaCompra, other.fechaCompra) && id == other.id && idItem == other.idItem
				&& idUsuario == other.idUsuario;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva de los valores de la instancia.
	 */
	@Override
	public String toString() {
		return "TiendaDTO [id=" + id + ", idUsuario=" + idUsuario + ", idItem=" + idItem + ", fechaCompra="
				+ fechaCompra + "]";
	}

}