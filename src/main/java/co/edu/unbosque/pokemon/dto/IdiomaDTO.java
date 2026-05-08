package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

public class IdiomaDTO {

	@SerializedName("name")
	private String nombreIdioma; // "es" o "en"

	public IdiomaDTO() {

	}

	public IdiomaDTO(String nombreIdioma) {
		super();
		this.nombreIdioma = nombreIdioma;
	}

	public String getNombreIdioma() {
		return nombreIdioma;
	}

	public void setNombreIdioma(String nombreIdioma) {
		this.nombreIdioma = nombreIdioma;
	}

	@Override
	public String toString() {
		return "IdiomaDTO [nombreIdioma=" + nombreIdioma + "]";
	}

}