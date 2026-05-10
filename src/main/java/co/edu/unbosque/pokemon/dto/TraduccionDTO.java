package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

public class TraduccionDTO {

	@SerializedName("translated")
	private String textoTraducido;

	public TraduccionDTO() {
		
	}

	public TraduccionDTO(String textoTraducido) {
		super();
		this.textoTraducido = textoTraducido;
	}

	public String getTextoTraducido() {
		return textoTraducido;
	}

	public void setTextoTraducido(String textoTraducido) {
		this.textoTraducido = textoTraducido;
	}

	@Override
	public String toString() {
		return "TraduccionDTO [textoTraducido=" + textoTraducido + "]";
	}

}