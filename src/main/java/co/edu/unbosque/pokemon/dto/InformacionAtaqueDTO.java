package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

public class InformacionAtaqueDTO {

	@SerializedName("name")
	private String nombreAtaque;

	@SerializedName("url")
	private String urlAtaque;

	public InformacionAtaqueDTO() {

	}

	public InformacionAtaqueDTO(String nombreAtaque, String urlAtaque) {
		super();
		this.nombreAtaque = nombreAtaque;
		this.urlAtaque = urlAtaque;
	}

	public String getNombreAtaque() {
		return nombreAtaque;
	}

	public void setNombreAtaque(String nombreAtaque) {
		this.nombreAtaque = nombreAtaque;
	}

	public String getUrlAtaque() {
		return urlAtaque;
	}

	public void setUrlAtaque(String urlAtaque) {
		this.urlAtaque = urlAtaque;
	}

	@Override
	public String toString() {
		return "InformacionAtaqueDTO [nombreAtaque=" + nombreAtaque + ", urlAtaque=" + urlAtaque + "]";
	}

}
