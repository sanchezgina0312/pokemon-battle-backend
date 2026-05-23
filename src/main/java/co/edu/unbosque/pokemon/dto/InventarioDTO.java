package co.edu.unbosque.pokemon.dto;

import java.util.Objects;

/**
 * Objeto de Transferencia de Datos (DTO) que representa el inventario de ítems de un usuario.
 * <p>
 * Esta clase se encarga de transportar la información sobre la cantidad de recursos, objetos o 
 * herramientas (como Pokéballs, pociones, etc.) que posee un entrenador específico dentro del sistema, 
 * facilitando el flujo de datos entre la persistencia y la lógica de negocio.
 * </p>
 * 
 * @version 1.0
 */
public class InventarioDTO {

	/**
	 * Identificador único y auto-incremental del registro de inventario en la base de datos local.
	 */
	private long id;
	
	/**
	 * Identificador único del usuario (entrenador) dueño de este lote de ítems.
	 */
	private long idUsuario;
	
	/**
	 * Identificador único del ítem u objeto almacenado.
	 */
	private long idItem;
	
	/**
	 * Cantidad disponible del ítem en el inventario del usuario.
	 */
	private int cantidad;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code InventarioDTO}.
	 */
	public InventarioDTO() {

	}

	/**
	 * Constructor parametrizado de la clase (excluyendo el ID autogenerado).
	 * Permite instanciar un nuevo {@code InventarioDTO} con los datos iniciales listos para ser operados o persistidos.
	 *
	 * @param idUsuario El identificador único del usuario propietario.
	 * @param idItem    El identificador único del ítem.
	 * @param cantidad  La cantidad inicial disponible del objeto.
	 */
	public InventarioDTO(long idUsuario, long idItem, int cantidad) {
		super();
		this.idUsuario = idUsuario;
		this.idItem = idItem;
		this.cantidad = cantidad;
	}

	/**
	 * Obtiene el identificador único del registro de inventario.
	 *
	 * @return El ID del registro en la base de datos.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del registro de inventario.
	 *
	 * @param id El nuevo ID para asignar al registro.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el identificador del usuario dueño del inventario.
	 *
	 * @return El ID del usuario.
	 */
	public long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * Establece el identificador del usuario dueño del inventario.
	 *
	 * @param idUsuario El nuevo ID del usuario a asignar.
	 */
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Obtiene el identificador del ítem almacenado.
	 *
	 * @return El ID del ítem.
	 */
	public long getIdItem() {
		return idItem;
	}

	/**
	 * Establece el identificador del ítem almacenado.
	 *
	 * @param idItem El nuevo ID del ítem a asignar.
	 */
	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}

	/**
	 * Obtiene la cantidad disponible del ítem.
	 *
	 * @return Un entero con el número de unidades del objeto.
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Establece la cantidad disponible del ítem.
	 *
	 * @param cantidad La nueva cantidad de unidades a asignar.
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * Genera un código hash único para la instancia actual basado en sus campos.
	 *
	 * @return El código hash calculado para este objeto.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(cantidad, id, idItem, idUsuario);
	}

	/**
	 * Compara de forma estructural la igualdad de este objeto frente a otro.
	 * <p>
	 * Dos instancias se evalúan como iguales si y solo si coinciden plenamente en sus valores 
	 * de {@code id}, {@code idUsuario}, {@code idItem} y {@code cantidad}.
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
		InventarioDTO other = (InventarioDTO) obj;
		return cantidad == other.cantidad && id == other.id && idItem == other.idItem && idUsuario == other.idUsuario;
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva de los valores de la instancia.
	 */
	@Override
	public String toString() {
		return "InventarioDTO [id=" + id + ", idUsuario=" + idUsuario + ", idItem=" + idItem + ", cantidad=" + cantidad
				+ "]";
	}

}