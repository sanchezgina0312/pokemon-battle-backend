package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;

public class VersionDTO {

	@SerializedName("generation-iii")
	private GeneracionDTO generacion3;

	public VersionDTO() {

	}

	public VersionDTO(GeneracionDTO generacion3) {
		super();
		this.generacion3 = generacion3;
	}

	public GeneracionDTO getGeneracion3() {
		return generacion3;
	}

	public void setGeneracion3(GeneracionDTO generacion3) {
		this.generacion3 = generacion3;
	}

	@Override
	public String toString() {
		return "VersionDTO [generacion3=" + generacion3 + "]";
	}

}
