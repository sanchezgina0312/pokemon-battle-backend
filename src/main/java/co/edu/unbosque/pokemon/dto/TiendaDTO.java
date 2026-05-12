package co.edu.unbosque.pokemon.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class TiendaDTO {

	private long id;
	private long idUsuario;
	private long idItem;
	private LocalDateTime fechaCompra;

	public TiendaDTO() {

	}

	public TiendaDTO(long idUsuario, long idItem, LocalDateTime fechaCompra) {
		super();
		this.idUsuario = idUsuario;
		this.idItem = idItem;
		this.fechaCompra = fechaCompra;
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

	public LocalDateTime getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(LocalDateTime fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaCompra, id, idItem, idUsuario);
	}

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

	@Override
	public String toString() {
		return "TiendaDTO [id=" + id + ", idUsuario=" + idUsuario + ", idItem=" + idItem + ", fechaCompra="
				+ fechaCompra + "]";
	}

}
