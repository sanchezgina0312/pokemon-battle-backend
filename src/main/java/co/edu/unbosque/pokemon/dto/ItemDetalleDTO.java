package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ItemDetalleDTO {

	@SerializedName("id")
	private int id;

	@SerializedName("name")
	private String nombreIngles;

	@SerializedName("cost")
	private int costo;

	@SerializedName("sprites")
	private SpriteItemDTO imagenes;

	@SerializedName("names")
	private List<TraduccionNombreDTO> listaNombresTraducidos;

	@SerializedName("flavor_text_entries")
	private List<DescripcionDTO> listaDescripciones;

	public ItemDetalleDTO() {

	}

	public ItemDetalleDTO(int id, String nombreIngles, int costo, SpriteItemDTO imagenes,
			List<TraduccionNombreDTO> listaNombresTraducidos, List<DescripcionDTO> listaDescripciones) {
		super();
		this.id = id;
		this.nombreIngles = nombreIngles;
		this.costo = costo;
		this.imagenes = imagenes;
		this.listaNombresTraducidos = listaNombresTraducidos;
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

	public List<TraduccionNombreDTO> getListaNombresTraducidos() {
		return listaNombresTraducidos;
	}

	public void setListaNombresTraducidos(List<TraduccionNombreDTO> listaNombresTraducidos) {
		this.listaNombresTraducidos = listaNombresTraducidos;
	}

	public List<DescripcionDTO> getListaDescripciones() {
		return listaDescripciones;
	}

	public void setListaDescripciones(List<DescripcionDTO> listaDescripciones) {
		this.listaDescripciones = listaDescripciones;
	}

}