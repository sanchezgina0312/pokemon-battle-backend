package co.edu.unbosque.pokemon.dto;
import com.google.gson.annotations.SerializedName;

public class AtaquePokemonDTO {
	
	@SerializedName("move")
    private InformacionAtaqueDTO informacionAtaque;

    public InformacionAtaqueDTO getInformacionAtaque() { return informacionAtaque; }
    public void setInformacionAtaque(InformacionAtaqueDTO informacionAtaque) { this.informacionAtaque = informacionAtaque; }

    
}
