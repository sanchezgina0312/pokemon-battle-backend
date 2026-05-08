package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

public class RojoFuegoDTO {

	@SerializedName("front_default")
	private String imagenFrente;

	@SerializedName("back_default")
	private String imagenEspalda;

	public RojoFuegoDTO() {

	}

	public RojoFuegoDTO(String imagenFrente, String imagenEspalda) {
		super();
		this.imagenFrente = imagenFrente;
		this.imagenEspalda = imagenEspalda;
	}

	public String getImagenFrente() {
		return imagenFrente;
	}

	public void setImagenFrente(String imagenFrente) {
		this.imagenFrente = imagenFrente;
	}

	public String getImagenEspalda() {
		return imagenEspalda;
	}

	public void setImagenEspalda(String imagenEspalda) {
		this.imagenEspalda = imagenEspalda;
	}

	@Override
	public String toString() {
		return "RojoFuegoDTO [imagenFrente=" + imagenFrente + ", imagenEspalda=" + imagenEspalda + "]";
	}

}
