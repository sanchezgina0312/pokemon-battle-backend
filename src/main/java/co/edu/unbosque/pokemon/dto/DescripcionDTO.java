package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

public class DescripcionDTO {

	@SerializedName("flavor_text")
	private String textoDescripcion;

	@SerializedName("language")
	private IdiomaDTO idioma;

	public DescripcionDTO() {

	}

	public DescripcionDTO(String textoDescripcion, IdiomaDTO idioma) {
		super();
		this.textoDescripcion = textoDescripcion;
		this.idioma = idioma;
	}

	public String getTextoDescripcion() {
		return textoDescripcion;
	}

	public void setTextoDescripcion(String textoDescripcion) {
		this.textoDescripcion = textoDescripcion;
	}

	public IdiomaDTO getIdioma() {
		return idioma;
	}

	public void setIdioma(IdiomaDTO idioma) {
		this.idioma = idioma;
	}

	@Override
	public String toString() {
		return "DescripcionDTO [textoDescripcion=" + textoDescripcion + ", idioma=" + idioma + "]";
	}

}