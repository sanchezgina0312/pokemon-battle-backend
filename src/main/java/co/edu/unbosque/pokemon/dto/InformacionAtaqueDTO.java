package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

public class InformacionAtaqueDTO {

	@SerializedName("name")
	private String nombre;

	@SerializedName("url")
	private String urlAtaque;

	public InformacionAtaqueDTO() {

	}

	public InformacionAtaqueDTO(String nombre, String urlAtaque) {
		super();
		this.nombre = nombre;
		this.urlAtaque = urlAtaque;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUrlAtaque() {
		return urlAtaque;
	}

	public void setUrlAtaque(String urlAtaque) {
		this.urlAtaque = urlAtaque;
	}

	@Override
	public String toString() {
		return "InformacionAtaqueDTO [nombre=" + nombre + ", urlAtaque=" + urlAtaque + "]";
	}

}
