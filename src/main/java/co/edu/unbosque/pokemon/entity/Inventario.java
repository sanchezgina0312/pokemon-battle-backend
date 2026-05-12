package co.edu.unbosque.pokemon.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Inventario {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	private long idUsuario;
	private long idItem;
	private int cantidad;

	public Inventario() {

	}

	public Inventario(long idUsuario, long idItem, int cantidad) {
		super();
		this.idUsuario = idUsuario;
		this.idItem = idItem;
		this.cantidad = cantidad;
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

	@Override
	public int hashCode() {
		return Objects.hash(cantidad, id, idItem, idUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventario other = (Inventario) obj;
		return cantidad == other.cantidad && id == other.id && idItem == other.idItem && idUsuario == other.idUsuario;
	}

	@Override
	public String toString() {
		return "Inventario [id=" + id + ", idUsuario=" + idUsuario + ", idItem=" + idItem + ", cantidad=" + cantidad
				+ "]";
	}

}