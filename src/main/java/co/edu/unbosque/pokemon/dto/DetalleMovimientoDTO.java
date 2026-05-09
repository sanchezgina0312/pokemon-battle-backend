package co.edu.unbosque.pokemon.dto;
import com.google.gson.annotations.SerializedName;

public class DetalleMovimientoDTO {
	
	@SerializedName("power")
    private int poder;

    @SerializedName("accuracy")
    private int precision;

    @SerializedName("pp")
    private int pp;

    @SerializedName("damage_class")
    private InfoTipoDTO claseDanio; 
    
    @SerializedName("type")
    private InfoTipoDTO tipoAtaque; 

    public DetalleMovimientoDTO() {}

	public int getPoder() {
		return poder;
	}

	public void setPoder(int poder) {
		this.poder = poder;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getPp() {
		return pp;
	}

	public void setPp(int pp) {
		this.pp = pp;
	}

	public InfoTipoDTO getClaseDanio() {
		return claseDanio;
	}

	public void setClaseDanio(InfoTipoDTO claseDanio) {
		this.claseDanio = claseDanio;
	}

	public InfoTipoDTO getTipoAtaque() {
		return tipoAtaque;
	}

	public void setTipoAtaque(InfoTipoDTO tipoAtaque) {
		this.tipoAtaque = tipoAtaque;
	}

	@Override
	public String toString() {
		return "DetalleMovimientoDTO [poder=" + poder + ", precision=" + precision + ", pp=" + pp + ", claseDanio="
				+ claseDanio + ", tipoAtaque=" + tipoAtaque + "]";
	}
    
}
