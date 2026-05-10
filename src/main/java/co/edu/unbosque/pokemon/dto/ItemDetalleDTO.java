package co.edu.unbosque.pokemon.dto;

import java.util.ArrayList; // Importante para que funcione
import com.google.gson.annotations.SerializedName;

public class ItemDetalleDTO {

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String nombreIngles;

	@SerializedName("cost")
	private int costo;

	@SerializedName("sprites")
	private SpriteItemDTO imagenes;

	@SerializedName("flavor_text_entries")
	private ArrayList<DescripcionDTO> listaDescripciones;

	public ItemDetalleDTO() {

	}

	public ItemDetalleDTO(int id, String nombreIngles, int costo, SpriteItemDTO imagenes,
			ArrayList<DescripcionDTO> listaDescripciones) {
		super();
		this.id = id;
		this.nombreIngles = nombreIngles;
		this.costo = costo;
		this.imagenes = imagenes;
		this.listaDescripciones = listaDescripciones;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreIngles() {
		return nombreIngles;
	}

	public void setNombreIngles(String nombreIngles) {
		this.nombreIngles = nombreIngles;
	}

	public int getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}

	public SpriteItemDTO getImagenes() {
		return imagenes;
	}

	public void setImagenes(SpriteItemDTO imagenes) {
		this.imagenes = imagenes;
	}

	public ArrayList<DescripcionDTO> getListaDescripciones() {
		return listaDescripciones;
	}

	public void setListaDescripciones(ArrayList<DescripcionDTO> listaDescripciones) {
		this.listaDescripciones = listaDescripciones;
	}

	@Override
	public String toString() {
		return "ItemDetalleDTO [id=" + id + ", nombreIngles=" + nombreIngles + ", costo=" + costo + ", imagenes="
				+ imagenes + ", listaDescripciones=" + listaDescripciones + "]";
	}

}