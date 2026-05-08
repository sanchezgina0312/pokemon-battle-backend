package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

public class SpriteDTO {

	@SerializedName("front_default")
	private String frente;

	@SerializedName("back_default")
	private String espalda;

	public SpriteDTO() {

	}

	public SpriteDTO(String frente, String espalda) {
		super();
		this.frente = frente;
		this.espalda = espalda;
	}

	public String getFrente() {
		return frente;
	}

	public void setFrente(String frente) {
		this.frente = frente;
	}

	public String getEspalda() {
		return espalda;
	}

	public void setEspalda(String espalda) {
		this.espalda = espalda;
	}

	@Override
	public String toString() {
		return "SpriteDTO [frente=" + frente + ", espalda=" + espalda + "]";
	}

}
