package co.edu.unbosque.pokemon.dto;
import com.google.gson.annotations.SerializedName;

public class VersionDTO {
	@SerializedName("generation-iii")
    private GeneracionDTO generacion3;
    public GeneracionDTO getGeneracion3() { return generacion3; }

}
