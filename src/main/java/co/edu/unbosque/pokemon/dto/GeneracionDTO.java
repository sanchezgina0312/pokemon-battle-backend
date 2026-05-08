package co.edu.unbosque.pokemon.dto;
import com.google.gson.annotations.SerializedName;

public class GeneracionDTO {
	
	@SerializedName("firered-leafgreen")
    private RojoFuegoDTO rojoFuego;
    public RojoFuegoDTO getRojoFuego() { return rojoFuego; }

}
