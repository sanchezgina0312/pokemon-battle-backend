package co.edu.unbosque.pokemon.dto;
import com.google.gson.annotations.SerializedName;

public class InformacionAtaqueDTO {
	
	@SerializedName("name")
    private String nombreAtaque;
    
    @SerializedName("url")
    private String urlAtaque; // Con esta url luego buscas el daño en español

    public String getNombreAtaque() { return nombreAtaque; }
    public void setNombreAtaque(String nombreAtaque) { this.nombreAtaque = nombreAtaque; }
    public String getUrlAtaque() { return urlAtaque; }
    public void setUrlAtaque(String urlAtaque) { this.urlAtaque = urlAtaque; }


}
