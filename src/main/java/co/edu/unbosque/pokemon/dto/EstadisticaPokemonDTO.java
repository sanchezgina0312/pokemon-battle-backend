package co.edu.unbosque.pokemon.dto;
import com.google.gson.annotations.SerializedName;

public class EstadisticaPokemonDTO {

	@SerializedName("base_stat")
    private int valorDeLaEstadistica; 

    public int getValorDeLaEstadistica() { return valorDeLaEstadistica; }
    public void setValorDeLaEstadistica(int valorDeLaEstadistica) { this.valorDeLaEstadistica = valorDeLaEstadistica; }
}
