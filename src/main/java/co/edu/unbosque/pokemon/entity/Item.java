package co.edu.unbosque.pokemon.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Item {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	private String nombre;
	private String descripcion;
	private int costo;
	private int efectoCurativo;

	public Item() {

	}

	public Item(String nombre, String descripcion, int costo, int efectoCurativo) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.costo = costo;
		this.efectoCurativo = efectoCurativo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public int getEfectoCurativo() {
		return efectoCurativo;
	}

	public void setEfectoCurativo(int efectoCurativo) {
		this.efectoCurativo = efectoCurativo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(costo, descripcion, efectoCurativo, id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return costo == other.costo && Objects.equals(descripcion, other.descripcion)
				&& efectoCurativo == other.efectoCurativo && id == other.id && Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", costo=" + costo
				+ ", efectoCurativo=" + efectoCurativo + "]";
	}

}
