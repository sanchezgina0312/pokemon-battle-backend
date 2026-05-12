package co.edu.unbosque.pokemon.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Combate {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) long id;
	private long idUsuarioJugador;
	private int idPokeApiAliado;
	private int idPokeApiRival;
	private int saludFinalAliado;
	private int saludFinalRival;
	private String resultado;
	private String modo;
	private LocalDateTime fechaCombate;

	public Combate() {
		this.fechaCombate = LocalDateTime.now();
	}

	public Combate(long idUsuarioJugador, int idPokeApiAliado, int idPokeApiRival, int saludFinalAliado,
			int saludFinalRival, String resultado, String modo, LocalDateTime fechaCombate) {
		super();
		this.idUsuarioJugador = idUsuarioJugador;
		this.idPokeApiAliado = idPokeApiAliado;
		this.idPokeApiRival = idPokeApiRival;
		this.saludFinalAliado = saludFinalAliado;
		this.saludFinalRival = saludFinalRival;
		this.resultado = resultado;
		this.modo = modo;
		this.fechaCombate = fechaCombate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdUsuarioJugador() {
		return idUsuarioJugador;
	}

	public void setIdUsuarioJugador(long idUsuarioJugador) {
		this.idUsuarioJugador = idUsuarioJugador;
	}

	public int getIdPokeApiAliado() {
		return idPokeApiAliado;
	}

	public void setIdPokeApiAliado(int idPokeApiAliado) {
		this.idPokeApiAliado = idPokeApiAliado;
	}

	public int getIdPokeApiRival() {
		return idPokeApiRival;
	}

	public void setIdPokeApiRival(int idPokeApiRival) {
		this.idPokeApiRival = idPokeApiRival;
	}

	public int getSaludFinalAliado() {
		return saludFinalAliado;
	}

	public void setSaludFinalAliado(int saludFinalAliado) {
		this.saludFinalAliado = saludFinalAliado;
	}

	public int getSaludFinalRival() {
		return saludFinalRival;
	}

	public void setSaludFinalRival(int saludFinalRival) {
		this.saludFinalRival = saludFinalRival;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getModo() {
		return modo;
	}

	public void setModo(String modo) {
		this.modo = modo;
	}

	public LocalDateTime getFechaCombate() {
		return fechaCombate;
	}

	public void setFechaCombate(LocalDateTime fechaCombate) {
		this.fechaCombate = fechaCombate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaCombate, id, idPokeApiAliado, idPokeApiRival, idUsuarioJugador, modo, resultado,
				saludFinalAliado, saludFinalRival);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Combate other = (Combate) obj;
		return Objects.equals(fechaCombate, other.fechaCombate) && id == other.id
				&& idPokeApiAliado == other.idPokeApiAliado && idPokeApiRival == other.idPokeApiRival
				&& idUsuarioJugador == other.idUsuarioJugador && Objects.equals(modo, other.modo)
				&& Objects.equals(resultado, other.resultado) && saludFinalAliado == other.saludFinalAliado
				&& saludFinalRival == other.saludFinalRival;
	}

	@Override
	public String toString() {
		return "Combate [id=" + id + ", idUsuarioJugador=" + idUsuarioJugador + ", idPokeApiAliado=" + idPokeApiAliado
				+ ", idPokeApiRival=" + idPokeApiRival + ", saludFinalAliado=" + saludFinalAliado + ", saludFinalRival="
				+ saludFinalRival + ", resultado=" + resultado + ", modo=" + modo + ", fechaCombate=" + fechaCombate
				+ "]";
	}

}