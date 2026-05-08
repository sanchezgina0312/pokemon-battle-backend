package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

public class SpriteItemDTO {

	@SerializedName("default")
	private String imagenDefault;

	public SpriteItemDTO() {

	}

	public SpriteItemDTO(String imagenDefault) {
		super();
		this.imagenDefault = imagenDefault;
	}

	public String getImagenDefault() {
		return imagenDefault;
	}

	public void setImagenDefault(String imagenDefault) {
		this.imagenDefault = imagenDefault;
	}

	@Override
	public String toString() {
		return "SpriteItemDTO [imagenDefault=" + imagenDefault + "]";
	}

}