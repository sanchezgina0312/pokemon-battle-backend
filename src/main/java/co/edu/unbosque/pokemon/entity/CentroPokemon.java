package co.edu.unbosque.pokemon.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CentroPokemon {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	private long idUsuario;
	private long idPokemonCurado;
	private LocalDateTime fechaVisita;

	public CentroPokemon() {
		
	}

	public CentroPokemon(long idUsuario, long idPokemonCurado, LocalDateTime fechaVisita) {
		super();
		this.idUsuario = idUsuario;
		this.idPokemonCurado = idPokemonCurado;
		this.fechaVisita = fechaVisita;
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

	public long getIdPokemonCurado() {
		return idPokemonCurado;
	}

	public void setIdPokemonCurado(long idPokemonCurado) {
		this.idPokemonCurado = idPokemonCurado;
	}

	public LocalDateTime getFechaVisita() {
		return fechaVisita;
	}

	public void setFechaVisita(LocalDateTime fechaVisita) {
		this.fechaVisita = fechaVisita;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaVisita, id, idPokemonCurado, idUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CentroPokemon other = (CentroPokemon) obj;
		return Objects.equals(fechaVisita, other.fechaVisita) && id == other.id
				&& idPokemonCurado == other.idPokemonCurado && idUsuario == other.idUsuario;
	}

	@Override
	public String toString() {
		return "CentroPokemon [id=" + id + ", idUsuario=" + idUsuario + ", idPokemonCurado=" + idPokemonCurado
				+ ", fechaVisita=" + fechaVisita + "]";
	}

}
