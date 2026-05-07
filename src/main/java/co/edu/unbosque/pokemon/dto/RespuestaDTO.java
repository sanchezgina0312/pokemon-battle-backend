package co.edu.unbosque.pokemon.dto;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class RespuestaDTO {
	
	@SerializedName("results")
    private List<ReferenciaPokemonDTO> listaResultados;

    public List<ReferenciaPokemonDTO> getListaResultados() { return listaResultados; }
    public void setListaResultados(List<ReferenciaPokemonDTO> listaResultados) { this.listaResultados = listaResultados; }

    
}
