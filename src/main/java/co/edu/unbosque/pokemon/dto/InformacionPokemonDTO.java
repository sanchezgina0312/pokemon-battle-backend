package co.edu.unbosque.pokemon.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class InformacionPokemonDTO {

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String nombre;

	@SerializedName("weight")
	private int peso;

	@SerializedName("stats")
	private List<EstadisticaPokemonDTO> listaEstadisticas;

	@SerializedName("moves")
	private List<AtaquePokemonDTO> listaAtaques;

	@SerializedName("sprites")
	private SpriteDTO imagenes;

	@SerializedName("types")
	private List<TipoPokemonDTO> listaTipos;
	
	@SerializedName("cries")
	private GritoPokemonDTO sonidos;

	public List<TipoPokemonDTO> getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(List<TipoPokemonDTO> listaTipos) {
		this.listaTipos = listaTipos;
	}

	public InformacionPokemonDTO() {

	}

	public InformacionPokemonDTO(int id, String nombre, int peso, List<EstadisticaPokemonDTO> listaEstadisticas,
			List<AtaquePokemonDTO> listaAtaques, SpriteDTO imagenes, List<TipoPokemonDTO> listaTipos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.peso = peso;
		this.listaEstadisticas = listaEstadisticas;
		this.listaAtaques = listaAtaques;
		this.imagenes = imagenes;
		this.listaTipos = listaTipos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public List<EstadisticaPokemonDTO> getListaEstadisticas() {
		return listaEstadisticas;
	}

	public void setListaEstadisticas(List<EstadisticaPokemonDTO> listaEstadisticas) {
		this.listaEstadisticas = listaEstadisticas;
	}

	public List<AtaquePokemonDTO> getListaAtaques() {
		return listaAtaques;
	}

	public void setListaAtaques(List<AtaquePokemonDTO> listaAtaques) {
		this.listaAtaques = listaAtaques;
	}

	public SpriteDTO getImagenes() {
		return imagenes;
	}

	public void setImagenes(SpriteDTO imagenes) {
		this.imagenes = imagenes;
	}
	
	public GritoPokemonDTO getSonidos() {
		return sonidos;
	}

	public void setSonidos(GritoPokemonDTO sonidos) {
		this.sonidos = sonidos;
	}

	@Override
	public String toString() {
		return "InformacionPokemonDTO [id=" + id + ", nombre=" + nombre + ", peso=" + peso + ", listaEstadisticas="
				+ listaEstadisticas + ", listaAtaques=" + listaAtaques + ", imagenes=" + imagenes + "]";
	}

}
