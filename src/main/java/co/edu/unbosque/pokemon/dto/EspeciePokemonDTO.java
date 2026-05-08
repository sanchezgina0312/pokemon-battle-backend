package co.edu.unbosque.pokemon.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class EspeciePokemonDTO {

	@SerializedName("flavor_text_entries")
	private List<DescripcionDTO> listaDescripciones;

	public EspeciePokemonDTO() {

	}

	public EspeciePokemonDTO(List<DescripcionDTO> listaDescripciones) {
		super();
		this.listaDescripciones = listaDescripciones;
	}

	public List<DescripcionDTO> getListaDescripciones() {
		return listaDescripciones;
	}

	public void setListaDescripciones(List<DescripcionDTO> listaDescripciones) {
		this.listaDescripciones = listaDescripciones;
	}

	@Override
	public String toString() {
		return "EspeciePokemonDTO [listaDescripciones=" + listaDescripciones + "]";
	}

}