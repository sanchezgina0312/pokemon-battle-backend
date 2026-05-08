package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

public class GeneracionDTO {

	@SerializedName("firered-leafgreen")
	private RojoFuegoDTO rojoFuego;

	public GeneracionDTO() {

	}

	public GeneracionDTO(RojoFuegoDTO rojoFuego) {
		super();
		this.rojoFuego = rojoFuego;
	}

	public RojoFuegoDTO getRojoFuego() {
		return rojoFuego;
	}

	public void setRojoFuego(RojoFuegoDTO rojoFuego) {
		this.rojoFuego = rojoFuego;
	}

	@Override
	public String toString() {
		return "GeneracionDTO [rojoFuego=" + rojoFuego + "]";
	}

}
