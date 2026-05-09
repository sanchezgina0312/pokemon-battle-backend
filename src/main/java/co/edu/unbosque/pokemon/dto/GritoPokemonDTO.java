package co.edu.unbosque.pokemon.dto;
import com.google.gson.annotations.SerializedName;

public class GritoPokemonDTO {
	
	@SerializedName("legacy")
    private String gritoPokemon;

    public GritoPokemonDTO() {
    }

	public String getGritoPokemon() {
		return gritoPokemon;
	}

	public void setGritoPokemon(String gritoPokemon) {
		this.gritoPokemon = gritoPokemon;
	}

	@Override
	public String toString() {
		return "GritoPokemonDTO [gritoPokemon=" + gritoPokemon + "]";
	}
	
}
