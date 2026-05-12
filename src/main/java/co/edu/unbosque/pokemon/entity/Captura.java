package co.edu.unbosque.pokemon.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Captura {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	private long idUsuario;
	private int pokeApiId;
	private String nombrePokemon;
	private LocalDateTime fechaCaptura;

	public Captura() {

	}

	public Captura(long idUsuario, int pokeApiId, String nombrePokemon, LocalDateTime fechaCaptura) {
		super();
		this.idUsuario = idUsuario;
		this.pokeApiId = pokeApiId;
		this.nombrePokemon = nombrePokemon;
		this.fechaCaptura = fechaCaptura;
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

	public int getPokeApiId() {
		return pokeApiId;
	}

	public void setPokeApiId(int pokeApiId) {
		this.pokeApiId = pokeApiId;
	}

	public String getNombrePokemon() {
		return nombrePokemon;
	}

	public void setNombrePokemon(String nombrePokemon) {
		this.nombrePokemon = nombrePokemon;
	}

	public LocalDateTime getFechaCaptura() {
		return fechaCaptura;
	}

	public void setFechaCaptura(LocalDateTime fechaCaptura) {
		this.fechaCaptura = fechaCaptura;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaCaptura, id, idUsuario, nombrePokemon, pokeApiId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Captura other = (Captura) obj;
		return Objects.equals(fechaCaptura, other.fechaCaptura) && id == other.id && idUsuario == other.idUsuario
				&& Objects.equals(nombrePokemon, other.nombrePokemon) && pokeApiId == other.pokeApiId;
	}

	@Override
	public String toString() {
		return "Captura [id=" + id + ", idUsuario=" + idUsuario + ", pokeApiId=" + pokeApiId + ", nombrePokemon="
				+ nombrePokemon + ", fechaCaptura=" + fechaCaptura + "]";
	}

}
