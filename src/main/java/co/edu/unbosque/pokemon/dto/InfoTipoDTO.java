package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

public class InfoTipoDTO {

	@SerializedName("name")
	private String nombreTipo;

	public InfoTipoDTO() {

	}

	public InfoTipoDTO(String nombreTipo) {
		super();
		this.nombreTipo = nombreTipo;
	}

	public String getNombreTipo() {
		return nombreTipo;
	}

	public void setNombreTipo(String nombreTipo) {
		this.nombreTipo = nombreTipo;
	}

	@Override
	public String toString() {
		return "InfoTipoDTO [nombreTipo=" + nombreTipo + "]";
	}

}