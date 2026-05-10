package co.edu.unbosque.pokemon.dto;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class InformacionPokemonDTO {

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String nombre;

	@SerializedName("weight")
	private int peso;

	@SerializedName("stats")
	private ArrayList<EstadisticaPokemonDTO> listaEstadisticas;

	@SerializedName("moves")
	private ArrayList<AtaquePokemonDTO> listaAtaques;

	@SerializedName("sprites")
	private SpriteDTO imagenes;

	@SerializedName("types")
	private ArrayList<TipoPokemonDTO> listaTipos;

	@SerializedName("cries")
	private GritoPokemonDTO sonidos;

	public InformacionPokemonDTO() {

	}

	public InformacionPokemonDTO(int id, String nombre, int peso, ArrayList<EstadisticaPokemonDTO> listaEstadisticas,
			ArrayList<AtaquePokemonDTO> listaAtaques, SpriteDTO imagenes, ArrayList<TipoPokemonDTO> listaTipos,
			GritoPokemonDTO sonidos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.peso = peso;
		this.listaEstadisticas = listaEstadisticas;
		this.listaAtaques = listaAtaques;
		this.imagenes = imagenes;
		this.listaTipos = listaTipos;
		this.sonidos = sonidos;
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

	public ArrayList<EstadisticaPokemonDTO> getListaEstadisticas() {
		return listaEstadisticas;
	}

	public void setListaEstadisticas(ArrayList<EstadisticaPokemonDTO> listaEstadisticas) {
		this.listaEstadisticas = listaEstadisticas;
	}

	public ArrayList<AtaquePokemonDTO> getListaAtaques() {
		return listaAtaques;
	}

	public void setListaAtaques(ArrayList<AtaquePokemonDTO> listaAtaques) {
		this.listaAtaques = listaAtaques;
	}

	public SpriteDTO getImagenes() {
		return imagenes;
	}

	public void setImagenes(SpriteDTO imagenes) {
		this.imagenes = imagenes;
	}

	public ArrayList<TipoPokemonDTO> getListaTipos() {
		return listaTipos;
	}

	public void setListaTipos(ArrayList<TipoPokemonDTO> listaTipos) {
		this.listaTipos = listaTipos;
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
				+ listaEstadisticas + ", listaAtaques=" + listaAtaques + ", imagenes=" + imagenes + ", listaTipos="
				+ listaTipos + ", sonidos=" + sonidos + "]";
	}

}
