package co.edu.unbosque.pokemon.dto;
import com.google.gson.annotations.SerializedName;

public class RojoFuegoDTO {
	
	    @SerializedName("front_default")
	    private String imagenFrente; // La imagen que ve el rival
	    
	    @SerializedName("back_default")
	    private String imagenEspalda; // La imagen que ves tú (tu Pokémon)

	    public String getImagenFrente() { return imagenFrente; }
	    public String getImagenEspalda() { return imagenEspalda; }

}
