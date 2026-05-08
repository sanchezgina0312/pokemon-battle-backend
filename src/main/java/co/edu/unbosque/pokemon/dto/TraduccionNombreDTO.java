package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

public class TraduccionNombreDTO {

	@SerializedName("name")
	private String nombreTraducido;

	@SerializedName("language")
	private IdiomaDTO idioma;

	public TraduccionNombreDTO() {

	}

	public TraduccionNombreDTO(String nombreTraducido, IdiomaDTO idioma) {
		super();
		this.nombreTraducido = nombreTraducido;
		this.idioma = idioma;
	}

	public String getNombreTraducido() {
		return nombreTraducido;
	}

	public void setNombreTraducido(String nombreTraducido) {
		this.nombreTraducido = nombreTraducido;
	}

	public IdiomaDTO getIdioma() {
		return idioma;
	}

	public void setIdioma(IdiomaDTO idioma) {
		this.idioma = idioma;
	}

	@Override
	public String toString() {
		return "TraduccionNombreDTO [nombreTraducido=" + nombreTraducido + ", idioma=" + idioma + "]";
	}

}