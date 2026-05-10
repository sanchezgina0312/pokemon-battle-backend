package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

public class TipoPokemonDTO {

	@SerializedName("type")
	private InfoTipoDTO informacionTipo;

	public TipoPokemonDTO() {

	}

	public TipoPokemonDTO(InfoTipoDTO informacionTipo) {
		super();
		this.informacionTipo = informacionTipo;
	}

	public InfoTipoDTO getInformacionTipo() {
		return informacionTipo;
	}

	public void setInformacionTipo(InfoTipoDTO informacionTipo) {
		this.informacionTipo = informacionTipo;
	}
	
	@Override
	public String toString() {
		return "TipoPokemonDTO [informacionTipo=" + informacionTipo + "]";
	}

}