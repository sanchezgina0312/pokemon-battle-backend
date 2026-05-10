package co.edu.unbosque.pokemon.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Ataque {

	private @Id String nombre;
	private boolean estaBaneado;
	private Integer poderModificado;

	public Ataque() {

	}

	public Ataque(String nombre, boolean estaBaneado, Integer poderModificado) {
		super();
		this.nombre = nombre;
		this.estaBaneado = estaBaneado;
		this.poderModificado = poderModificado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isEstaBaneado() {
		return estaBaneado;
	}

	public void setEstaBaneado(boolean estaBaneado) {
		this.estaBaneado = estaBaneado;
	}

	public Integer getPoderModificado() {
		return poderModificado;
	}

	public void setPoderModificado(Integer poderModificado) {
		this.poderModificado = poderModificado;
	}

	@Override
	public int hashCode() {
		return Objects.hash(estaBaneado, nombre, poderModificado);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ataque other = (Ataque) obj;
		return estaBaneado == other.estaBaneado && Objects.equals(nombre, other.nombre)
				&& Objects.equals(poderModificado, other.poderModificado);
	}

	@Override
	public String toString() {
		return "Ataque [nombre=" + nombre + ", estaBaneado=" + estaBaneado + ", poderModificado=" + poderModificado
				+ "]";
	}

}