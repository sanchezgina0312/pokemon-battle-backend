package co.edu.unbosque.pokemon.dto;

import java.util.Objects;

/**
 * Objeto de Transferencia de Datos (DTO) que representa un ítem u objeto consumible dentro del sistema local.
 * <p>
 * A diferencia del detalle crudo de la API, esta clase transporta la información depurada y procesada 
 * de la tienda local del juego, incluyendo atributos específicos del negocio como el costo en la moneda interna 
 * y su efectividad de restauración en los combates.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
public class ItemDTO {

	/**
	 * Identificador único y auto-incremental del ítem en la base de datos local.
	 */
	private long id;
	
	/**
	 * Nombre comercial del ítem traducido o adaptado para el usuario (ej. "Poción", "Superpoción").
	 */
	private String nombre;
	
	/**
	 * Descripción resumida de las funciones, propiedades o modo de uso del objeto.
	 */
	private String descripcion;
	
	/**
	 * Precio o valor de compra del ítem dentro de la tienda del juego.
	 */
	private int costo;
	
	/**
	 * Puntos de salud (HP) que restaura el ítem al ser aplicado sobre un Pokémon herido.
	 */
	private int efectoCurativo;

	/**
	 * Constructor por defecto de la clase.
	 * Crea una nueva instancia vacía de {@code ItemDTO}.
	 */
	public ItemDTO() {

	}

	/**
	 * Constructor parametrizado de la clase (excluyendo el ID autogenerado).
	 * Permite instanciar un nuevo {@code ItemDTO} con las propiedades fundamentales del objeto del juego.
	 *
	 * @param nombre         El nombre adaptado del ítem.
	 * @param descripcion    El texto explicativo de las propiedades del objeto.
	 * @param costo          El precio de venta en la tienda local.
	 * @param efectoCurativo Los puntos de restauración de salud asociados.
	 */
	public ItemDTO(String nombre, String descripcion, int costo, int efectoCurativo) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.costo = costo;
		this.efectoCurativo = efectoCurativo;
	}

	/**
	 * Obtiene el identificador único del ítem en el sistema local.
	 *
	 * @return El ID numérico del registro.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Establece el identificador único del ítem en el sistema local.
	 *
	 * @param id El nuevo ID a asignar al registro.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del ítem.
	 *
	 * @return Una cadena de caracteres con el nombre del objeto.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del ítem.
	 *
	 * @param nombre El nuevo nombre para asignar al objeto.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la descripción detallada de la utilidad del objeto.
	 *
	 * @return Una cadena de caracteres con la descripción del ítem.
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Establece la descripción detallada de la utilidad del objeto.
	 *
	 * @param descripcion La nueva descripción comercial a asignar.
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el precio o costo base del objeto en la tienda.
	 *
	 * @return Un entero con el valor económico del ítem.
	 */
	public int getCosto() {
		return costo;
	}

	/**
	 * Establece el precio o costo base del objeto en la tienda.
	 *
	 * @param costo El nuevo costo de adquisición a asignar.
	 */
	public void setCosto(int costo) {
		this.costo = costo;
	}

	/**
	 * Obtiene la capacidad o valor cuantitativo de restauración de salud del ítem.
	 *
	 * @return Un entero que representa los puntos de HP curados.
	 */
	public int getEfectoCurativo() {
		return efectoCurativo;
	}

	/**
	 * Establece la capacidad o valor cuantitativo de restauración de salud del ítem.
	 *
	 * @param efectoCurativo La cantidad de puntos de salud para asignar al efecto del ítem.
	 */
	public void setEfectoCurativo(int efectoCurativo) {
		this.efectoCurativo = efectoCurativo;
	}

	/**
	 * Genera un código hash único para la instancia actual basado en sus campos.
	 *
	 * @return El código hash calculado para este objeto.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(costo, descripcion, efectoCurativo, id, nombre);
	}

	/**
	 * Compara de forma estructural la igualdad de este objeto frente a otro.
	 * <p>
	 * Dos instancias se evalúan como iguales si y solo si coinciden plenamente en todos sus
	 * atributos de identidad, costos, textos y métricas curativas.
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
		ItemDTO other = (ItemDTO) obj;
		return costo == other.costo && Objects.equals(descripcion, other.descripcion)
				&& efectoCurativo == other.efectoCurativo && id == other.id && Objects.equals(nombre, other.nombre);
	}

	/**
	 * Construye una representación en cadena de texto legible con el estado actual de los atributos del DTO.
	 *
	 * @return Una cadena descriptiva de los valores de la instancia.
	 */
	@Override
	public String toString() {
		return "ItemDTO [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", costo=" + costo
				+ ", efectoCurativo=" + efectoCurativo + "]";
	}

}