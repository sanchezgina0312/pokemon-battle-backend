package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Objeto de Transferencia de Datos (DTO) que representa el valor numérico de una estadística base de un Pokémon.
 * <p>
 * Se utiliza en la capa de integración con servicios externos para mapear y deserializar los componentes
 * individuales del arreglo de estadísticas (como puntos de salud, ataque, defensa, entre otros) provenientes de la PokeAPI.
 * </p>
 * 
 * @author Integrantes del Proyecto
 * @version 1.0
 */
public class EstadisticaPokemonDTO {

	@SerializedName("base_stat")
    private int valorDeLaEstadistica;

    @SerializedName("stat")
    private DetalleEstadisticaDTO  stat;

    public EstadisticaPokemonDTO() {}

    public int getValorDeLaEstadistica() {
        return valorDeLaEstadistica;
    }

    public void setValorDeLaEstadistica(int valorDeLaEstadistica) {
        this.valorDeLaEstadistica = valorDeLaEstadistica;
    }

    public DetalleEstadisticaDTO getStat() {
        return stat;
    }

    public void setStat(DetalleEstadisticaDTO stat) {
        this.stat = stat;
    }

    @Override
    public String toString() {
        return "EstadisticaPokemonDTO [valor=" + valorDeLaEstadistica + ", name=" + (stat != null ? stat.getName() : "null") + "]";
    }
}