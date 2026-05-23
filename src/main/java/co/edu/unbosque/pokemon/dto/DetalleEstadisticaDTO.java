package co.edu.unbosque.pokemon.dto;

/**
 * DTO que representa el detalle de una estadística.
 * 
 * Se utiliza para almacenar el nombre de una estadística
 * obtenida desde una fuente externa o transferida
 * entre capas de la aplicación.
 * 
 * @version 1.0
 */
public class DetalleEstadisticaDTO {

	/**
	 * Nombre de la estadística.
	 */
	private String name;

	/**
	 * Obtiene el nombre de la estadística.
	 * 
	 * @return nombre de la estadística.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el nombre de la estadística.
	 * 
	 * @param name nuevo nombre de la estadística.
	 */
	public void setName(String name) {
		this.name = name;
	}

}