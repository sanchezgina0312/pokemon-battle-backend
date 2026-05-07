package co.edu.unbosque.pokemon.dto;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class InformacionPokemonDTO {
	
	@SerializedName("id")
    private int id;
    
    @SerializedName("name")
    private String nombre;
    
    @SerializedName("weight")
    private int peso; 

    @SerializedName("stats")
    private List<EstadisticaPokemonDTO> listaEstadisticas;

    @SerializedName("moves")
    private List<AtaquePokemonDTO> listaAtaques;

    @SerializedName("sprites")
    private SpriteDTO imagenes;

    // --- GETTERS Y SETTERS ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getPeso() { return peso; }
    public void setPeso(int peso) { this.peso = peso; }
    public List<EstadisticaPokemonDTO> getListaEstadisticas() { return listaEstadisticas; }
    public void setListaEstadisticas(List<EstadisticaPokemonDTO> listaEstadisticas) { this.listaEstadisticas = listaEstadisticas; }
    public List<AtaquePokemonDTO> getListaAtaques() { return listaAtaques; }
    public void setListaAtaques(List<AtaquePokemonDTO> listaAtaques) { this.listaAtaques = listaAtaques; }
    public SpriteDTO getImagenes() { return imagenes; }
    public void setImagenes(SpriteDTO imagenes) { this.imagenes = imagenes; }
}
