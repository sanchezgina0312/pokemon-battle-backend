package co.edu.unbosque.pokemon.dto;

import java.util.Objects;

public class PokemonDTO {

	private long id;
	private Integer pokeApiId;
	private String apodo;
	private int nivel;
	private int experienciaAcumulada;
	private int saludActual;
	private int saludMaxima;
	private String nombreAtaque1;
	private String nombreAtaque2;
	private String nombreAtaque3;
	private String nombreAtaque4;
	private Long idUsuarioPropietario;

	public PokemonDTO() {

	}

	public PokemonDTO(Integer pokeApiId, String apodo, int nivel, int experienciaAcumulada, int saludActual,
			int saludMaxima, String nombreAtaque1, String nombreAtaque2, String nombreAtaque3, String nombreAtaque4,
			Long idUsuarioPropietario) {
		super();
		this.pokeApiId = pokeApiId;
		this.apodo = apodo;
		this.nivel = nivel;
		this.experienciaAcumulada = experienciaAcumulada;
		this.saludActual = saludActual;
		this.saludMaxima = saludMaxima;
		this.nombreAtaque1 = nombreAtaque1;
		this.nombreAtaque2 = nombreAtaque2;
		this.nombreAtaque3 = nombreAtaque3;
		this.nombreAtaque4 = nombreAtaque4;
		this.idUsuarioPropietario = idUsuarioPropietario;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Integer getPokeApiId() {
		return pokeApiId;
	}

	public void setPokeApiId(Integer pokeApiId) {
		this.pokeApiId = pokeApiId;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public int getExperienciaAcumulada() {
		return experienciaAcumulada;
	}

	public void setExperienciaAcumulada(int experienciaAcumulada) {
		this.experienciaAcumulada = experienciaAcumulada;
	}

	public int getSaludActual() {
		return saludActual;
	}

	public void setSaludActual(int saludActual) {
		this.saludActual = saludActual;
	}

	public int getSaludMaxima() {
		return saludMaxima;
	}

	public void setSaludMaxima(int saludMaxima) {
		this.saludMaxima = saludMaxima;
	}

	public String getNombreAtaque1() {
		return nombreAtaque1;
	}

	public void setNombreAtaque1(String nombreAtaque1) {
		this.nombreAtaque1 = nombreAtaque1;
	}

	public String getNombreAtaque2() {
		return nombreAtaque2;
	}

	public void setNombreAtaque2(String nombreAtaque2) {
		this.nombreAtaque2 = nombreAtaque2;
	}

	public String getNombreAtaque3() {
		return nombreAtaque3;
	}

	public void setNombreAtaque3(String nombreAtaque3) {
		this.nombreAtaque3 = nombreAtaque3;
	}

	public String getNombreAtaque4() {
		return nombreAtaque4;
	}

	public void setNombreAtaque4(String nombreAtaque4) {
		this.nombreAtaque4 = nombreAtaque4;
	}

	public Long getIdUsuarioPropietario() {
		return idUsuarioPropietario;
	}

	public void setIdUsuarioPropietario(Long idUsuarioPropietario) {
		this.idUsuarioPropietario = idUsuarioPropietario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(apodo, experienciaAcumulada, id, idUsuarioPropietario, nivel, nombreAtaque1, nombreAtaque2,
				nombreAtaque3, nombreAtaque4, pokeApiId, saludActual, saludMaxima);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PokemonDTO other = (PokemonDTO) obj;
		return Objects.equals(apodo, other.apodo) && experienciaAcumulada == other.experienciaAcumulada
				&& id == other.id && Objects.equals(idUsuarioPropietario, other.idUsuarioPropietario)
				&& nivel == other.nivel && Objects.equals(nombreAtaque1, other.nombreAtaque1)
				&& Objects.equals(nombreAtaque2, other.nombreAtaque2)
				&& Objects.equals(nombreAtaque3, other.nombreAtaque3)
				&& Objects.equals(nombreAtaque4, other.nombreAtaque4) && Objects.equals(pokeApiId, other.pokeApiId)
				&& saludActual == other.saludActual && saludMaxima == other.saludMaxima;
	}

	@Override
	public String toString() {
		return "PokemonDTO [id=" + id + ", pokeApiId=" + pokeApiId + ", apodo=" + apodo + ", nivel=" + nivel
				+ ", experienciaAcumulada=" + experienciaAcumulada + ", saludActual=" + saludActual + ", saludMaxima="
				+ saludMaxima + ", nombreAtaque1=" + nombreAtaque1 + ", nombreAtaque2=" + nombreAtaque2
				+ ", nombreAtaque3=" + nombreAtaque3 + ", nombreAtaque4=" + nombreAtaque4 + ", idUsuarioPropietario="
				+ idUsuarioPropietario + "]";
	}

}
