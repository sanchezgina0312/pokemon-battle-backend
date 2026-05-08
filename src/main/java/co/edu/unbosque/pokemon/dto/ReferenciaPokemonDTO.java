package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

public class ReferenciaPokemonDTO {

	@SerializedName("name")
	private String nombre;

	@SerializedName("url")
	private String url;

	public ReferenciaPokemonDTO() {

	}

	public ReferenciaPokemonDTO(String nombre, String url) {
		super();
		this.nombre = nombre;
		this.url = url;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ReferenciaPokemonDTO [nombre=" + nombre + ", url=" + url + "]";
	}

}
