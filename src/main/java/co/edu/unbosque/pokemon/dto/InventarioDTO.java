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

    private long id;
    private long idUsuario;
    private long idItem;
    private int cantidad;
    private String nombre;

    /**
     * Constructor por defecto.
     */
    public InventarioDTO() {
    }

    /**
     * Constructor parametrizado.
     * @param idUsuario ID del dueño.
     * @param idItem    ID del objeto (ej. extraído de la PokeAPI).
     * @param cantidad  Cantidad disponible.
     * @param nombre    Nombre del objeto.
     */
    public InventarioDTO(long idUsuario, long idItem, int cantidad, String nombre) {
        super();
        this.idUsuario = idUsuario;
        this.idItem = idItem;
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    // --- Getters y Setters ---

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public long getIdItem() {
        return idItem;
    }

    public void setIdItem(long idItem) {
        this.idItem = idItem;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // --- Métodos de utilidad ---

    @Override
    public int hashCode() {
        return Objects.hash(cantidad, id, idItem, idUsuario, nombre);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        InventarioDTO other = (InventarioDTO) obj;
        return cantidad == other.cantidad && id == other.id && idItem == other.idItem
                && idUsuario == other.idUsuario && Objects.equals(nombre, other.nombre);
    }

    @Override
    public String toString() {
        return "InventarioDTO [id=" + id + ", idUsuario=" + idUsuario + ", idItem=" + idItem
                + ", cantidad=" + cantidad + ", nombre=" + nombre + "]";
    }
}